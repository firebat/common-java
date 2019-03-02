package common.rpc.autoconfigure;

import com.google.common.collect.Maps;
import common.rpc.http.HttpEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;
import java.util.Map;

@ConfigurationProperties("rest")
public class RestProperties {

    @NestedConfigurationProperty
    private Map<String, List<String>> dns = Maps.newHashMap();

    @NestedConfigurationProperty
    private Map<String, HttpEndpoint> service = Maps.newHashMap();

    public Map<String, List<String>> getDns() {
        return dns;
    }

    public void setDns(Map<String, List<String>> dns) {
        this.dns = dns;
    }

    public Map<String, HttpEndpoint> getService() {
        return service;
    }

    public void setService(Map<String, HttpEndpoint> service) {
        this.service = service;
    }
}
