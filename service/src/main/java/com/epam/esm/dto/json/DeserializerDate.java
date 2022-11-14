package com.epam.esm.dto.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class DeserializerDate extends StdDeserializer<LocalDate> {

    protected DeserializerDate() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {
        return LocalDate.parse(parser.readValueAs(String.class));
    }
}
