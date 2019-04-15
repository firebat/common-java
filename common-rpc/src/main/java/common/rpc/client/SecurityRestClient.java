package common.rpc.client;

import common.rpc.http.Endpoint;
import okhttp3.OkHttpClient;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;

public abstract class SecurityRestClient extends RestClient {

    private static final X509TrustManager EMPTY_TRUST_MANAGER = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };

    public SecurityRestClient(String name) {
        super(name);
    }

    @Override
    protected OkHttpClient.Builder createClient(Endpoint point) {

        OkHttpClient.Builder builder = super.createClient(point);

        if (!point.getUrl().startsWith("https://")) {
            return builder;
        }

        InputStream stream = loadCertificate();
        if (stream == null) {
            X509TrustManager trustManager = EMPTY_TRUST_MANAGER;
            return builder.sslSocketFactory(createSSLSocketFactory(trustManager, new SecureRandom()), trustManager)
                    .hostnameVerifier((s, sesssion) -> true);
        }

        try {
            X509TrustManager trustManager = createTrustManager(stream);
            return builder.sslSocketFactory(createSSLSocketFactory(trustManager, null), trustManager);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    protected X509TrustManager createTrustManager(InputStream stream) throws GeneralSecurityException {

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(stream);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        char[] password = "password".toCharArray();
        KeyStore keyStore = createKeyStore(null, password);
        int idx = 0;
        for (Certificate certificate : certificates) {
            keyStore.setCertificateEntry(String.valueOf(idx++), certificate);
        }

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }

        return (X509TrustManager) trustManagers[0];
    }

    protected KeyStore createKeyStore(InputStream stream, char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(stream, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    protected SSLSocketFactory createSSLSocketFactory(TrustManager trustManager, SecureRandom random) {
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{trustManager}, random);
            return sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected InputStream loadCertificate() {
        return null;
    }
}
