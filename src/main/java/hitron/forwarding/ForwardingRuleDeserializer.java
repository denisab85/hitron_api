package hitron.forwarding;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ForwardingRuleDeserializer extends JsonDeserializer<ForwardingRule> {

	@Override
	public ForwardingRule deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);
		ForwardingRule result = new ForwardingRule();
		result.setRuleIndex(node.get("ruleIndex").asInt());
		result.setAppName(node.get("appName").textValue());
		result.setPubStart(node.get("pubStart").asInt());
		result.setPubEnd(node.get("pubEnd").asInt());
		result.setPriStart(node.get("priStart").asInt());
		result.setPriEnd(node.get("priEnd").asInt());
		result.setProtocal(node.get("protocal").textValue());
		result.setLocalIpAddr(node.get("localIpAddr").textValue());
		result.setRemoteIpStar(node.get("remoteIpStar").textValue());
		result.setRemoteIpEnd(node.get("remoteIpEnd").textValue());
		result.setRemoteOnOff(node.get("remoteOnOff").textValue().equalsIgnoreCase("Specific"));
		result.setRuleOnOff(node.get("ruleOnOff").textValue().equalsIgnoreCase("ON"));
		return result;
	}

}