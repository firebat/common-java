package common.rpc.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import common.api.json.CodeMessage;
import common.api.json.Json;
import common.exception.ServiceException;
import common.json.JsonMapper;
import common.json.MapperBuilder;
import common.rpc.url.AuthUrl;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * HTTP请求调用封装
 */
public class HttpRpc {

    private final Logger logger = LoggerFactory.getLogger(HttpRpc.class);

    private final HttpEndpoint config;
    private final OkHttpClient client;
    private final JsonMapper mapper;
    private final Function<HttpUrl, Request.Builder> requestFunc;

    public HttpRpc(HttpEndpoint config, OkHttpClient client) {
        this(config, client, MapperBuilder.getDefaultMapper());
    }

    public HttpRpc(HttpEndpoint config, OkHttpClient client, JsonMapper mapper) {
        this.config = config;
        this.client = client;
        this.mapper = mapper;

        final String key = config.getKey();
        final String secret = config.getSecret();
        final int timeout = config.getTimeout();

        if (Strings.isNullOrEmpty(config.getKey()) || Strings.isNullOrEmpty(config.getSecret())) {
            this.requestFunc = (url) -> new Request.Builder().url(url);
        } else {
            this.requestFunc = (url) -> new Request.Builder().url(new AuthUrl(url.toString(), key, secret).timeout(timeout).duplicate(config.isDuplicate()).build());
        }
    }

    public Request.Builder createRequest(String host, String uri, String... args) {

        HttpUrl.Builder urlBuilder = HttpUrl.parse(host).newBuilder().addPathSegments(uri);
        Preconditions.checkArgument(args.length % 2 == 0, "参数数量错误");
        for (int i = 0; i < args.length; i += 2) {
            urlBuilder.addQueryParameter(args[i], args[i + 1]);
        }

        return requestFunc.apply(urlBuilder.build());
    }

    public Response execute(Request request) {

        logger.debug("request={}", request);

        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T parse(Response response, TypeReference<T> type) {

        try {
            if (!response.isSuccessful()) {
                String body = response.body().string();
                logger.error("status={}, body={}", response.code(), body);
                throw new ServiceException(response.code(), body);
            }

            return mapper.readValue(response.body().charStream(), type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T returnOrException(Json<T> json) {
        if (json.code == CodeMessage.OK) {
            return json.data;
        }

        throw new ServiceException(json.code, json.message);
    }

    public <T> T getObject(String uri, TypeReference<T> type, String... args) {
        return parse(execute(createRequest(config.getUrl(), uri, args).get().build()), type);
    }

    public <T> T postObject(String uri, TypeReference<T> type, RequestBody requestBody) {
        return parse(execute(createRequest(config.getUrl(), uri).post(requestBody).build()), type);
    }

    public <T> T get(String uri, TypeReference<Json<T>> type, String... args) {
        return returnOrException(getObject(uri, type, args));
    }

    public <T> T post(String uri, TypeReference<Json<T>> type, RequestBody requestBody) {
        return returnOrException(postObject(uri, type, requestBody));
    }
}
