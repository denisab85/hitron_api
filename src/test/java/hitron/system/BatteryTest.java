package hitron.system;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BatteryTest {

	private static final String json = "{\"battRemain\":\"0\",\"battCharge\":\"N/A\",\"battPsm\":\"OFF\",\"battInsert\":\"N/A\"}";
	private static final Battery rule = new Battery(0, null, false, null);

	private static ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void whenSerializeThenAllCorrect() throws JsonProcessingException {
		assertThat(objectMapper.writeValueAsString(rule), is(json));
	}

	@Test
	public void whenDeserializeThenEquals() throws IOException {
		Battery actual = objectMapper.readValue(json, Battery.class);
		assertThat(actual, is(rule));
	}

}
