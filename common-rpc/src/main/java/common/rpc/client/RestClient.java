package common.rpc.client;

import com.google.common.base.Strings;
import common.config.ProxyConfig;
import common.rpc.autoconfigure.RestProperties;
import common.rpc.http.HttpEndpoint;
import common.rpc.http.HttpRpc;
import okhttp3.*;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class RestClient {

    private RestProperties properties;
    private final String name;
    private HttpRpc service;

    public RestClient(String name) {
        this.name = name;
    }

    @PostConstruct
    public void init() {
        HttpEndpoint point = properties.getService().get(name);
        this.service = new HttpRpc(point, createClient(point).build());
    }

    protected OkHttpClient.Builder createClient(HttpEndpoint point) {

        final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(point.getReadTimeout(), TimeUnit.SECONDS)
                .connectTimeout(point.getConnectTimeout(), TimeUnit.SECONDS)
                .writeTimeout(point.getWriteTimeout(), TimeUnit.SECONDS);

        if (!properties.getDns().isEmpty()) {
            setDns(builder, properties.getDns());
        }

        ProxyConfig proxy = point.getProxy();
        if (proxy.getType() != Proxy.Type.DIRECT) {
            builder.proxy(new Proxy(proxy.getType(), new InetSocketAddress(proxy.getHost(), proxy.getPort())));

            if (!Strings.isNullOrEmpty(proxy.getUsername())) {
                builder.proxyAuthenticator(new Authenticator() {
                    @Nullable
                    @Override
                    public Request authenticate(@Nullable Route route, Response response) throws IOException {
                        String credential = Credentials.basic(proxy.getUsername(), proxy.getPassword());
                        return response.request().newBuilder().header("Proxy-Authorization", credential).build();
                    }
                });
            }
        }

        return builder;
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

    @Resource
    public void setProperties(RestProperties properties) {
        this.properties = properties;
    }
}
