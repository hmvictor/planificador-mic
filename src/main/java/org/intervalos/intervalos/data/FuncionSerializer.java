package org.intervalos.intervalos.data;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import org.intervalos.intervalos.Funcion;
import org.intervalos.intervalos.Funcion;

/**
 *
 * @author Víctor
 */
public class FuncionSerializer extends JsonDeserializer {

    @Override
    public Object deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node=jp.readValueAsTree();
        return new Funcion().inicia(node.get("horas").asInt(), node.get("minutos").asInt());
    }
    
}
