package common.rpc.client;

import com.fasterxml.jackson.core.type.TypeReference;
import common.api.json.Json;
import common.config.ProxyConfig;
import common.json.JsonMapper;
import common.json.MapperBuilder;
import common.rpc.autoconfigure.RestProperties;
import common.rpc.http.HttpEndpoint;
import common.rpc.http.HttpRpc;
import okhttp3.Dns;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class RestClient {

    protected static final JsonMapper mapper = MapperBuilder.getDefaultMapper();

    public static final MediaType MEDIA_OF_JSON = MediaType.parse("application/json");
    public static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<Map<String, Object>>() {
    };

    public static final TypeReference<Json<Object>> OBJECT_JSON = new TypeReference<Json<Object>>() {
    };
    public static final TypeReference<Json<Integer>> INT_JSON = new TypeReference<Json<Integer>>() {
    };

    @Resource
    private RestProperties properties;
    private HttpRpc service;
    private final String name;

    public RestClient(String name) {
        this.name = name;
    }

    @PostConstruct
    public void init() {
        HttpEndpoint point = properties.getService().get(name);
        OkHttpClient.Builder builder = createClient(point);
        this.service = new HttpRpc(point, builder.build());
    }

    protected OkHttpClient.Builder createClient(HttpEndpoint point) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (!properties.getDns().isEmpty()) {
            setDns(builder, properties.getDns());
        }

        ProxyConfig proxy = point.getProxy();
        if (proxy.getType() != Proxy.Type.DIRECT) {
            builder.proxy(new Proxy(proxy.getType(), new InetSocketAddress(proxy.getHost(), proxy.getPort())));
        }

        if (point.getUrl().startsWith("https://")) {
            // TODO load cert
            final X509TrustManager trustManager = createTrustManager();
            builder.sslSocketFactory(createSSLSocketFactory(trustManager), trustManager)
                    .hostnameVerifier((s, session) -> true);
        }

        return builder.readTimeout(point.getReadTimeout(), TimeUnit.SECONDS)
                .connectTimeout(point.getConnectTimeout(), TimeUnit.SECONDS)
                .writeTimeout(point.getWriteTimeout(), TimeUnit.SECONDS);
    }

    protected X509TrustManager createTrustManager() {
        return new X509TrustManager() {
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
    }

    protected SSLSocketFactory createSSLSocketFactory(TrustManager trustManager) {
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            return sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void setDns(OkHttpClient.Builder builder, Map<String, List<String>> dns) {

        final Map<String, List<InetAddress>> mapping = dns.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().stream().map(RestClient::inetAddressOf).collect(Collectors.toList())));

        builder.dns((hostname) -> {
            List<InetAddress> addresses = mapping.get(hostname);
            if (addresses != null) {
                return addresses;
            }

            return Dns.SYSTEM.lookup(hostname);
        });
    }

    private static InetAddress inetAddressOf(String host) {
        try {
            return InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpRpc getService() {
        return service;
    }

    public RestProperties getProperties() {
        return properties;
    }
}
