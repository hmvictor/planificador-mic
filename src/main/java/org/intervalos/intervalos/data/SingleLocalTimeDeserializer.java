package org.intervalos.intervalos.data;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalTime;

/**
 *
 * @author Víctor
 */
public class SingleLocalTimeDeserializer extends JsonDeserializer<LocalTime> {

    @Override
    public LocalTime deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        return LocalTime.parse(jp.getValueAsString());
    }
    
}
