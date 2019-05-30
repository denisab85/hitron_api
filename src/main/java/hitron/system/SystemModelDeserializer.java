package hitron.system;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class SystemModelDeserializer extends JsonDeserializer<SystemModel> {

	@Override
	public SystemModel deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);
		SystemModel result = new SystemModel();
		if (node.has("modelName"))
			result.setModelName(node.get("modelName").textValue());
		if (node.has("skipWizard"))
			result.setSkipWizard(node.get("skipWizard").asInt());
		if (node.has("theme"))
			result.setTheme(node.get("theme").textValue());
		return result;
	}

}