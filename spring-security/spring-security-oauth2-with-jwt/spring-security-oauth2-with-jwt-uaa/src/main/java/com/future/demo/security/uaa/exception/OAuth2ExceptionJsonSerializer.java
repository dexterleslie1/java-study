package com.future.demo.security.uaa.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class OAuth2ExceptionJsonSerializer extends StdSerializer<OAuth2ExceptionWithCustomizeJson> {
    protected OAuth2ExceptionJsonSerializer() {
        super(OAuth2ExceptionWithCustomizeJson.class);
    }

    @Override
    public void serialize(OAuth2ExceptionWithCustomizeJson value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("errorCode", 600);
        gen.writeStringField("errorMessage", value.getMessage());
        gen.writeEndObject();
    }
}
