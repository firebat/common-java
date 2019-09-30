package common.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CodeMessageSerializer extends StdSerializer<CodeMessage> {

    private String code;
    private String data;
    private String message;

    public CodeMessageSerializer(String code, String data, String message) {
        super(CodeMessage.class);

        this.code = code;
        this.data = data;
        this.message = message;
    }

    @Override
    public void serialize(CodeMessage object, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {

        generator.writeStartObject();
        generator.writeNumberField(code, object.getCode());
        generator.writeObjectField(data, object.getData());
        generator.writeStringField(message, object.getMessage());
        generator.writeEndObject();
    }
}
