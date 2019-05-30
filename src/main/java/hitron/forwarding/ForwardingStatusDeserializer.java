package hitron.forwarding;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ForwardingStatusDeserializer extends JsonDeserializer<ForwardingStatus> {

	@Override
	public ForwardingStatus deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);
		ForwardingStatus result = new ForwardingStatus();
		result.setRulesOnOff(node.get("rulesOnOff").textValue().equalsIgnoreCase("Enabled"));
		result.setPrivateLan(node.get("privateLan").textValue());
		result.setSubMask(node.get("subMask").textValue());
		result.setHttpPort(node.get("HttpPort").asInt());
		result.setHttpsPort(node.get("HttpsPort").asInt());
		result.setTelnetPort(node.get("TelnetPort").asInt());
		result.setSshPort(node.get("SshPort").asInt());
		if (node.has("forwardingRuleStatus")) {
			result.setForwardingRuleStatus(node.get("forwardingRuleStatus").asInt());
		}
		return result;
	}

}