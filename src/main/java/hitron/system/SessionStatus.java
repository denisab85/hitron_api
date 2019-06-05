package hitron.system;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = SessionStatusSerializer.class)
@JsonDeserialize(using = SessionStatusDeserializer.class)
public class SessionStatus {
	private Boolean sessionStatus;
}
