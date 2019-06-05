package hitron.system;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = BatterySerializer.class)
@JsonDeserialize(using = BatteryDeserializer.class)
public class Battery {
	private int battRemain;
	private Boolean battCharge;
	private Boolean battPsm;
	private Boolean battInsert;
}
