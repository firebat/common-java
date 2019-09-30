package common.api.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import junit.framework.TestCase;
import org.junit.Test;

public class CodeMessageSerializerTest extends TestCase {

    @Test
    public void testDefault() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(new Json<>(1, "hello", "world"));
        assertEquals("{\"code\":1,\"message\":\"hello\",\"data\":\"world\"}", value);
    }

    @Test
    public void testCustomized() throws Exception {

        SimpleModule module = new SimpleModule();
        module.addSerializer(new CodeMessageSerializer("status", "data", "msg"));
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);

        String value = mapper.writeValueAsString(new Json<>(1, "hello", "world"));
        assertEquals("{\"status\":1,\"data\":\"world\",\"msg\":\"hello\"}", value);
    }
}
