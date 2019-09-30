package common.web.spring.handler;

import com.fasterxml.jackson.databind.module.SimpleModule;
import common.api.json.CodeMessageSerializer;
import common.web.annotation.EnableJsonBody;
import common.web.configuration.JsonBodyConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

public class JsonBodyImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableJsonBody.class.getName(), false));

        String code = attributes.getString("code");
        String data = attributes.getString("data");
        String message = attributes.getString("message");

        SimpleModule module = new SimpleModule();
        module.addSerializer(new CodeMessageSerializer(code, data, message));

        JsonSerializer.getMapper().registerModule(module);

        return new String[]{JsonBodyConfiguration.class.getName()};
    }
}
