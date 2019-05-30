package hitron.system;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SystemModelTest {

	private static final String json = "{\"modelName\":\"CGNM-2250-SHW\",\"skipWizard\":\"1\",\"theme\":\"Default\"}";
	private static final SystemModel rule = new SystemModel("CGNM-2250-SHW", 1, "Default");
	//
	private static ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void whenToStringThenAllCorrect() throws JsonProcessingException {
		assertThat(objectMapper.writeValueAsString(rule), is(json));
	}

	@Test
	public void whenFromStringThenEquals() throws IOException {
		SystemModel actual = objectMapper.readValue(json, SystemModel.class);
		assertThat(actual, is(rule));
	}

}
