package common.rpc.http;

import common.config.ClientConfig;
import common.config.ProxyConfig;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 连接点配置
 */
public class Endpoint extends ClientConfig {

    @NestedConfigurationProperty
    private ProxyConfig proxy = new ProxyConfig();

    private String url;

    // 访问认证(可选)
    private String key;
    private String secret;
    private int timeout = 10;
    private boolean duplicate = false;

    public ProxyConfig getProxy() {
        return proxy;
    }

    public void setProxy(ProxyConfig proxy) {
        this.proxy = proxy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }
}
