package common.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class JsonMapper {

    private final ObjectMapper mapper;

    public JsonMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String writeValueAsString(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("jackson format error: ", e);
        }
    }

    public void writeValue(Writer writer, Object value) throws IOException {

        Preconditions.checkNotNull(writer);

        try {
            mapper.writeValue(writer, value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("jackson format error: ", e);
        }
    }

    public <T> T readValue(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("jackson parse error :" + json.substring(0, Math.min(100, json.length())), e);
        }
    }

    public <T> T readValue(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException("jackson parse error :" + json.substring(0, Math.min(100, json.length())), e);
        }
    }

    public <T> T readValue(Reader reader, TypeReference<T> type) throws IOException {

        Preconditions.checkNotNull(reader);

        try {
            return mapper.readValue(reader, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("jackson parse error.", e);
        }
    }
}
