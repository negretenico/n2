package com.n2.core.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.InputStream;
import java.util.Set;

public class DocumentValidator {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Set<ValidationMessage> validate(InputStream json, InputStream schemaStream) throws Exception {
        JsonNode jsonNode = mapper.readTree(json);
        JsonSchema schema = JsonSchemaFactory
                .getInstance(SpecVersion.VersionFlag.V7)
                .getSchema(schemaStream);
        return schema.validate(jsonNode);
    }
}
