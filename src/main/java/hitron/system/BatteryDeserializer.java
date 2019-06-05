package hitron.system;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class BatteryDeserializer extends JsonDeserializer<Battery> {

	@Override
	public Battery deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);
		Battery result = new Battery();
		if (node.has("battRemain"))
			result.setBattRemain(node.get("battRemain").intValue());
		if (node.has("battCharge"))
			result.setBattCharge(getBoolean(node.get("battCharge").textValue()));
		if (node.has("battPsm"))
			result.setBattPsm(getBoolean(node.get("battPsm").textValue()));
		if (node.has("battInsert"))
			result.setBattInsert(getBoolean(node.get("battInsert").textValue()));
		return result;
	}

	private Boolean getBoolean(String value) {
		if (value.equalsIgnoreCase("ON")) {
			return true;
		} else if (value.equalsIgnoreCase("OFF")) {
			return false;
		} else {
			return null;
		}
	}

}