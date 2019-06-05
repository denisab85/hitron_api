package hitron.system;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class SessionStatusDeserializer extends JsonDeserializer<SessionStatus> {

	@Override
	public SessionStatus deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);
		SessionStatus result = new SessionStatus();
		if (node.has("session_status"))
			result.setSessionStatus(node.get("session_status").asText().equalsIgnoreCase("Alive"));
		return result;
	}

}