package hitron.wireless;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = WirelesSsidSerializer.class)
@JsonDeserialize(using = WirelesSsidDeserializer.class)
public class WirelesSsid {

	private int id;
	private String band;
	private String ssidName;
	private boolean enable;
	private boolean visible;
	private boolean wmm;
	private int authMode;
	private int encryptType;
	private int securityType;
	private String passPhrase;
	private String hiddenWepAtkip;
	private boolean wlsEnable;
	private String wepKey1;
	private String wepKey2;
	private String wepKey3;
	private String wepKey4;
	private int txKey;
	private int secuMode;
	private int isPriSsid;
	private String defaultKey;
	private int ssidSplit;
	private String authServer;
	private int authPort;
	private String authSecret;
	private int maxInactiveTime;
	private String wepShownOpt;
	private String phyMode;

}
