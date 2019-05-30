package hitron.wireless;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WirelesSsidTest {
	private static final String json = "{\"id\":0,\"band\":\"2.4G\",\"ssidName\":\"ssidName\",\"enable\":\"ON\",\"visible\":\"ON\",\"wmm\":\"ON\",\"authMode\":4,\"encryptType\":4,\"securityType\":15,\"passPhrase\":\"658476611523\",\"hiddenWepAtkip\":\"Display\",\"wlsEnable\":\"ON\",\"wepKey1\":\"89383\",\"wepKey2\":\"30886\",\"wepKey3\":\"92777\",\"wepKey4\":\"36915\",\"txKey\":1,\"secuMode\":2,\"isPriSsid\":1,\"defaultKey\":\"658476611523\",\"ssidSplit\":4,\"authServer\":\"authServer\",\"authPort\":0,\"authSecret\":\"authSecret\",\"maxInactiveTime\":300,\"wepShownOpt\":\"Mixed\",\"phyMode\":\"5\"}";
	private static final WirelesSsid rule = new WirelesSsid(0, "2.4G", "ssidName", true, true, true, 4, 4, 15, "658476611523", "Display", true,
			"89383", "30886", "92777", "36915", 1, 2, 1, "658476611523", 4, "authServer", 0, "authSecret", 300, "Mixed", "5");

	private static ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void whenToStringThenAllCorrect() throws JsonProcessingException {
		assertThat(objectMapper.writeValueAsString(rule), is(json));
	}

	@Test
	public void whenFromStringThenEquals() throws IOException {
		WirelesSsid actual = objectMapper.readValue(json, WirelesSsid.class);
		assertThat(actual, is(rule));
	}
}
