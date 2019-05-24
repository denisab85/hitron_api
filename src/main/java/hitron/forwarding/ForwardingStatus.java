package hitron.forwarding;

import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
public class ForwardingStatus {

	private boolean rulesOnOff = true;
	private String privateLan = "192.168.0.1";
	private String subMask = "255.255.255.0";
	private int httpPort = 8080;
	private int httpsPort = 8181;
	private int telnetPort = 2323;
	private int sshPort = 22;
	private int forwardingRuleStatus = 1;

	private static final String IMPORT_STRING = "^\\[?\\s*\\{\\s*[\\w\\\":\\.,\\s*]+\\}\\s*\\]?$";
	private static final String IMPORT_RECORD = "\\\"(\\w+)\\\":\\\"?([-%\\w\\.]+)\\\"?";
	private static final String FORMAT_STRING = "\"%s\":\"%s\"";
	private static final String FORMAT_INT = "\"%s\":\"%d\"";

	public ForwardingStatus(String string) {
		if (!string.trim().matches(IMPORT_STRING)) {
			throw new RuntimeException(String.format("Init string '%s' doesn't match pattern: '%s'", string, IMPORT_STRING));
		}
		Pattern record = Pattern.compile(IMPORT_RECORD);
		Matcher matcher = record.matcher(string);
		while (matcher.find()) {
			String name = matcher.group(1);
			String value = matcher.group(2);
			switch (name) {
			case "rulesOnOff": {
				this.rulesOnOff = value.equalsIgnoreCase("Enabled");
				break;
			}
			case "privateLan": {
				this.privateLan = value;
				break;
			}
			case "subMask": {
				this.subMask = value;
				break;
			}
			case "HttpPort": {
				this.httpPort = Integer.parseInt(value);
				break;
			}
			case "HttpsPort": {
				this.httpsPort = Integer.parseInt(value);
				break;
			}
			case "TelnetPort": {
				this.telnetPort = Integer.parseInt(value);
				break;
			}
			case "SshPort": {
				this.sshPort = Integer.parseInt(value);
				break;
			}
			}
		}
	}

	public void setEnabled(boolean status) {
		rulesOnOff = status;
	}

	public boolean getEnabled() {
		return rulesOnOff;
	}

	@Override
	public String toString() {
		StringJoiner result = new StringJoiner(", ", "{ ", " }");
		result.add(getRulesOnOff());
		result.add(getPrivateLan());
		result.add(getSubMask());
		result.add(getHttpPort());
		result.add(getHttpsPort());
		result.add(getTelnetPort());
		result.add(getSshPort());
		result.add(getForwardingRuleStatus());
		return result.toString();
	}

	private String getRulesOnOff() {
		return String.format(FORMAT_STRING, "rulesOnOff", rulesOnOff ? "Enabled" : "Disabled");
	}

	private String getPrivateLan() {
		return String.format(FORMAT_STRING, "privateLan", privateLan);
	}

	private String getSubMask() {
		return String.format(FORMAT_STRING, "subMask", subMask);
	}

	private String getHttpPort() {
		return String.format(FORMAT_INT, "HttpPort", httpPort);
	}

	private String getHttpsPort() {
		return String.format(FORMAT_INT, "HttpsPort", httpsPort);
	}

	private String getTelnetPort() {
		return String.format(FORMAT_INT, "TelnetPort", telnetPort);
	}

	private String getSshPort() {
		return String.format(FORMAT_INT, "SshPort", sshPort);
	}

	private String getForwardingRuleStatus() {
		return String.format(FORMAT_INT, "forwardingRuleStatus", forwardingRuleStatus);
	}

}
