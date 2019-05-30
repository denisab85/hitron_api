package hitron.system;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SysInfoSerializer extends JsonSerializer<SysInfo> {

	@Override
	public void serialize(SysInfo value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartObject();
		jgen.writeStringField("hwVersion", value.getHwVersion());
		jgen.writeStringField("swVersion", value.getSwVersion());
		jgen.writeStringField("serialNumber", value.getSerialNumber());
		jgen.writeStringField("rfMac", value.getRfMac());
		jgen.writeStringField("wanIp", value.getWanIp());
		jgen.writeStringField("wanDlgtPrefix", value.getWanDlgtPrefix());
		jgen.writeStringField("WanDNSInfo", value.getWanDNSInfo());
		jgen.writeStringField("systemLanUptime", value.getSystemLanUptime());
		jgen.writeStringField("systemWanUptime", value.getSystemWanUptime());
		jgen.writeStringField("systemTime", value.getSystemTime());
		jgen.writeStringField("timezone", value.getTimezone());
		jgen.writeStringField("WRecPkt", value.getWRecPkt());
		jgen.writeStringField("WSendPkt", value.getWSendPkt());
		jgen.writeStringField("lanIp", value.getLanIp());
		jgen.writeStringField("LRecPkt", value.getLRecPkt());
		jgen.writeStringField("LSendPkt", value.getLSendPkt());
		jgen.writeStringField("gatewayOnOff", value.isGatewayOnOff() ? "Enabled" : "Disabled");
		jgen.writeEndObject();
	}
}