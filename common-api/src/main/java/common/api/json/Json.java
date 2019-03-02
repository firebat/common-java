package common.api.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Json<T> {

    public final int code;
    public final String message;
    public final T data;

    @JsonCreator
    public Json(@JsonProperty("code") int code,
                @JsonProperty("message") String message,
                @JsonProperty("data") T data) {

        this.code = code;
        this.message = message;
        this.data = data;
    }
}
