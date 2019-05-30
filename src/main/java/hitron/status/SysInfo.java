package hitron.status;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;

@Data
public class SysInfo {

	@Getter
	private static ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	}

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
	private String gatewayOnOff;

}
