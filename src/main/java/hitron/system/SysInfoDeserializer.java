package hitron.system;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class SysInfoDeserializer extends JsonDeserializer<SysInfo> {

	@Override
	public SysInfo deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		ObjectCodec oc = parser.getCodec();
		JsonNode node = oc.readTree(parser);
		SysInfo result = new SysInfo();
		if (node.has("hwVersion"))
			result.setHwVersion(node.get("hwVersion").textValue());
		if (node.has("swVersion"))
			result.setSwVersion(node.get("swVersion").textValue());
		if (node.has("serialNumber"))
			result.setSerialNumber(node.get("serialNumber").textValue());
		if (node.has("rfMac"))
			result.setRfMac(node.get("rfMac").textValue());
		if (node.has("wanIp"))
			result.setWanIp(node.get("wanIp").textValue());
		if (node.has("wanDlgtPrefix"))
			result.setWanDlgtPrefix(node.get("wanDlgtPrefix").textValue());
		if (node.has("WanDNSInfo"))
			result.setWanDNSInfo(node.get("WanDNSInfo").textValue());
		if (node.has("systemLanUptime"))
			result.setSystemLanUptime(node.get("systemLanUptime").textValue());
		if (node.has("systemWanUptime"))
			result.setSystemWanUptime(node.get("systemWanUptime").textValue());
		if (node.has("systemTime"))
			result.setSystemTime(node.get("systemTime").textValue());
		if (node.has("timezone"))
			result.setTimezone(node.get("timezone").textValue());
		if (node.has("WRecPkt"))
			result.setWRecPkt(node.get("WRecPkt").textValue());
		if (node.has("WSendPkt"))
			result.setWSendPkt(node.get("WSendPkt").textValue());
		if (node.has("lanIp"))
			result.setLanIp(node.get("lanIp").textValue());
		if (node.has("LRecPkt"))
			result.setLRecPkt(node.get("LRecPkt").textValue());
		if (node.has("LSendPkt"))
			result.setLSendPkt(node.get("LSendPkt").textValue());
		if (node.has("gatewayOnOff"))
			result.setGatewayOnOff(node.get("gatewayOnOff").textValue().equalsIgnoreCase("Enabled"));
		return result;
	}

}