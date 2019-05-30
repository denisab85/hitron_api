package hitron.system;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = SystemModelSerializer.class)
@JsonDeserialize(using = SystemModelDeserializer.class)
public class SystemModel {

	private String modelName;
	private int skipWizard;
	private String theme;
}
