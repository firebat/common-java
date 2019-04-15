package common.rpc.client;

import com.fasterxml.jackson.core.type.TypeReference;
import common.rpc.autoconfigure.RpcProperties;
import common.rpc.http.Endpoint;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.InputStream;
import java.util.Map;

public class SecurityRestClientTest extends TestCase {

    class OpenWeather extends SecurityRestClient {

        public OpenWeather() {
            super("open-weather");
        }

        @Override
        protected InputStream loadCertificate() {
            return null;
        }

        public Map<String, Object> getCityWeather(String city) {
            // ?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22
            return getService().getObject("data/2.5/weather", new TypeReference<Map<String, Object>>() {
                    },
                    "appid", "b6907d289e10d714a6e88b30761fae22",
                    "q", city);
        }
    }

    @Test
    public void testAll() {

        Endpoint endpoint = new Endpoint();
        endpoint.setUrl("https://samples.openweathermap.org");

        RpcProperties properties = new RpcProperties();
        properties.getService().put("open-weather", endpoint);

        OpenWeather weather = new OpenWeather();
        weather.setProperties(properties);
        weather.init();

        Map london = weather.getCityWeather("London,uk");
        assertEquals("London", london.get("name"));
        System.out.println(london);
    }
}
