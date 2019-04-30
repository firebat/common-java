package common.rpc.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Joiner;
import common.api.json.Json;
import common.json.JsonMapper;
import common.json.MapperBuilder;
import okhttp3.MediaType;

import java.util.Map;

public interface RestShared {

    JsonMapper mapper = MapperBuilder.getDefaultMapper();

    Joiner comma = Joiner.on(',');

    MediaType MEDIA_OF_JSON = MediaType.parse("application/json");

    TypeReference<Json<Object>> JSON_TYPE_OBJECT = new TypeReference<Json<Object>>() {
    };
    TypeReference<Json<String>> JSON_TYPE_STRING = new TypeReference<Json<String>>() {
    };
    TypeReference<Json<Boolean>> JSON_TYPE_BOOLEAN = new TypeReference<Json<Boolean>>() {
    };
    TypeReference<Json<Integer>> JSON_TYPE_INTEGER = new TypeReference<Json<Integer>>() {
    };
    TypeReference<Json<Long>> JSON_TYPE_LONG = new TypeReference<Json<Long>>() {
    };
    TypeReference<Json<Map>> JSON_TYPE_MAP = new TypeReference<Json<Map>>() {
    };
    TypeReference<Map> TYPE_MAP = new TypeReference<Map>() {
    };
}
