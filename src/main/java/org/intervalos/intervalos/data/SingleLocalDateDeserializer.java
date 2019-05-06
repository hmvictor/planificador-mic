package org.intervalos.intervalos.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Víctor
 */
public class SingleLocalDateDeserializer extends KeyDeserializer {

    @Override
    public Object deserializeKey(String string, DeserializationContext dc) throws IOException, JsonProcessingException {
        return LocalDate.parse(string, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
    
}
