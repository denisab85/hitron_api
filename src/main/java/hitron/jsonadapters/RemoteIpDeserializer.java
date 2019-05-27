package hitron.jsonadapters;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class RemoteIpDeserializer extends JsonDeserializer<Boolean> {
	@Override
	public Boolean deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return "Specific".equals(parser.getText());
	}
}
