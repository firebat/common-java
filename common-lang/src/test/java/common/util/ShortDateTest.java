package common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import common.json.JsonMapper;
import common.json.MapperBuilder;
import junit.framework.TestCase;
import org.junit.Test;

public class ShortDateTest extends TestCase {

    @Test
    public void testDate() {
        ShortDate date = new ShortDate(20191010);
        assertEquals("2019-10-10", date.toString());

        JsonMapper mapper = MapperBuilder.getDefaultMapper();
        assertEquals("20191010", mapper.writeValueAsString(date));

        ShortDate v = mapper.readValue("20191011", new TypeReference<ShortDate>() {
        });
        assertEquals(20191011, v.getValue());
    }
}
