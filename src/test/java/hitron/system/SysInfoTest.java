package hitron.system;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SysInfoTest {

	private static final String json = "{\"hwVersion\":\"1A\",\"swVersion\":\"4.5.11.9.1\",\"serialNumber\":\"658476611523\",\"rfMac\":\"e5:3f:5a:7b:d4:46\",\"wanIp\":\"120.93.35.205/22\",\"wanDlgtPrefix\":\"none\",\"WanDNSInfo\":\"120.93.176.13, 120.93.177.226\",\"systemLanUptime\":\"003 days 01h:40m:02s\",\"systemWanUptime\":\"003 days 01h:37m:40s\",\"systemTime\":\"Wed, 29 May 2019 20:24:52 -050\",\"timezone\":\"--\",\"WRecPkt\":\"1.32G Bytes\",\"WSendPkt\":\"8.17G Bytes\",\"lanIp\":\"192.168.0.1/24\",\"LRecPkt\":\"185.42M Bytes\",\"LSendPkt\":\"8.59G Bytes\",\"gatewayOnOff\":\"Enabled\"}";
	private static final SysInfo rule = new SysInfo("1A", "4.5.11.9.1", "658476611523", "e5:3f:5a:7b:d4:46", "120.93.35.205/22", "none",
			"120.93.176.13, 120.93.177.226", "003 days 01h:40m:02s", "003 days 01h:37m:40s", "Wed, 29 May 2019 20:24:52 -050", "--", "1.32G Bytes",
			"8.17G Bytes", "192.168.0.1/24", "185.42M Bytes", "8.59G Bytes", true);

	private static ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void whenToStringThenAllCorrect() throws JsonProcessingException {
		assertThat(objectMapper.writeValueAsString(rule), is(json));
	}

	@Test
	public void whenFromStringThenEquals() throws IOException {
		SysInfo actual = objectMapper.readValue(json, SysInfo.class);
		assertThat(actual, is(rule));
	}

}
