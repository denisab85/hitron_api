package hitron.system;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = SysInfoSerializer.class)
@JsonDeserialize(using = SysInfoDeserializer.class)
public class SysInfo {

	private String hwVersion;
	private String swVersion;
	private String serialNumber;
	private String rfMac;
	private String wanIp;
	private String wanDlgtPrefix;
	private String WanDNSInfo;
	private String systemLanUptime;
	private String systemWanUptime;
	private String systemTime;
	private String timezone;
	private String WRecPkt;
	private String WSendPkt;
	private String lanIp;
	private String LRecPkt;
	private String LSendPkt;
	private boolean gatewayOnOff;

}
