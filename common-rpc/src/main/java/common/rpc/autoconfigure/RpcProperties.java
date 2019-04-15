package common.rpc.autoconfigure;

import com.google.common.collect.Maps;
import common.rpc.http.Endpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;
import java.util.Map;

@ConfigurationProperties("rpc")
public class RpcProperties {

    @NestedConfigurationProperty
    private Map<String, List<String>> dns = Maps.newHashMap();

    @NestedConfigurationProperty
    private Map<String, Endpoint> service = Maps.newHashMap();

    public Map<String, List<String>> getDns() {
        return dns;
    }

    public void setDns(Map<String, List<String>> dns) {
        this.dns = dns;
    }

    public Map<String, Endpoint> getService() {
        return service;
    }

    public void setService(Map<String, Endpoint> service) {
        this.service = service;
    }
}
