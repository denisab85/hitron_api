package hitron.forwarding;

import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Setter;

@Setter
public class ForwardingRule implements Comparable<ForwardingRule> {

	int ruleIndex;
	String appName;
	int pubStart;
	int pubEnd;
	int priStart;
	int priEnd;
	String protocol;
	String localIpAddr;
	String remoteOnOff;
	String remoteIpStar;
	String remoteIpEnd;
	boolean ruleOnOff;

	private static final String IMPORT_STRING = "^\\{.+?\\}$";
	private static final String IMPORT_RECORD = "\\\"(\\w+)\\\":\\\"?([\\w\\.%-]+)\\\"?";
	private static final String FORMAT_STRING = "\"%s\":\"%s\"";
	private static final String FORMAT_INT = "\"%s\":\"%d\"";

	public ForwardingRule(int ruleIndex, boolean ruleOnOff, String appName, int pubStart, int pubEnd, int priStart, int priEnd, String protocol,
			String remoteOnOff, String localIpAddr, String remoteIpStar, String remoteIpEnd) {
		super();
		this.ruleIndex = ruleIndex;
		this.appName = appName;
		this.pubStart = pubStart;
		this.pubEnd = pubEnd;
		this.priStart = priStart;
		this.priEnd = priEnd;
		this.protocol = protocol;
		this.localIpAddr = localIpAddr;
		this.remoteOnOff = remoteOnOff;
		this.remoteIpStar = remoteIpStar;
		this.remoteIpEnd = remoteIpEnd;
		this.ruleOnOff = ruleOnOff;
	}

	public ForwardingRule(String string) {
		if (!string.trim().matches(IMPORT_STRING)) {
			throw new RuntimeException(String.format("Init string doesn't match pattern: '%s'", IMPORT_STRING));
		}
		Pattern record = Pattern.compile(IMPORT_RECORD);
		Matcher matcher = record.matcher(string);
		while (matcher.find()) {
			String name = matcher.group(1);
			String value = matcher.group(2);
			switch (name) {
			case "ruleIndex": {
				this.ruleIndex = Integer.parseInt(value);
				break;
			}
			case "appName": {
				this.appName = value;
				break;
			}
			case "pubStart": {
				this.pubStart = Integer.parseInt(value);
				break;
			}
			case "pubEnd": {
				this.pubEnd = Integer.parseInt(value);
				break;
			}
			case "priStart": {
				this.priStart = Integer.parseInt(value);
				break;
			}
			case "priEnd": {
				this.priEnd = Integer.parseInt(value);
				break;
			}
			// The typo is deliberate in 'protocAl' - originates in Hitron firmware
			case "protocal": {
				this.protocol = value;
				break;
			}
			case "localIpAddr": {
				this.localIpAddr = value;
				break;
			}
			case "remoteOnOff": {
				this.remoteOnOff = value;
				break;
			}
			case "remoteIpStar": {
				this.remoteIpStar = value;
				break;
			}
			case "remoteIpEnd": {
				this.remoteIpEnd = value;
				break;
			}
			case "ruleOnOff": {
				this.ruleOnOff = value.equalsIgnoreCase("ON");
				break;
			}
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appName == null) ? 0 : appName.hashCode());
		result = prime * result + ((localIpAddr == null) ? 0 : localIpAddr.hashCode());
		result = prime * result + priEnd;
		result = prime * result + priStart;
		result = prime * result + ((protocol == null) ? 0 : protocol.hashCode());
		result = prime * result + pubEnd;
		result = prime * result + pubStart;
		result = prime * result + ((remoteIpEnd == null) ? 0 : remoteIpEnd.hashCode());
		result = prime * result + ((remoteIpStar == null) ? 0 : remoteIpStar.hashCode());
		result = prime * result + ((remoteOnOff == null) ? 0 : remoteOnOff.hashCode());
		result = prime * result + (ruleOnOff ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForwardingRule other = (ForwardingRule) obj;
		if (appName == null) {
			if (other.appName != null)
				return false;
		} else if (!appName.equals(other.appName))
			return false;
		if (localIpAddr == null) {
			if (other.localIpAddr != null)
				return false;
		} else if (!localIpAddr.equals(other.localIpAddr))
			return false;
		if (priEnd != other.priEnd)
			return false;
		if (priStart != other.priStart)
			return false;
		if (protocol == null) {
			if (other.protocol != null)
				return false;
		} else if (!protocol.equals(other.protocol))
			return false;
		if (pubEnd != other.pubEnd)
			return false;
		if (pubStart != other.pubStart)
			return false;
		if (remoteIpEnd == null) {
			if (other.remoteIpEnd != null)
				return false;
		} else if (!remoteIpEnd.equals(other.remoteIpEnd))
			return false;
		if (remoteIpStar == null) {
			if (other.remoteIpStar != null)
				return false;
		} else if (!remoteIpStar.equals(other.remoteIpStar))
			return false;
		if (remoteOnOff == null) {
			if (other.remoteOnOff != null)
				return false;
		} else if (!remoteOnOff.equals(other.remoteOnOff))
			return false;
		if (ruleOnOff != other.ruleOnOff)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringJoiner result = new StringJoiner(",", "{", "}");

		result.add(getRuleIndex());
		result.add(getAppName());
		result.add(getPubStart());
		result.add(getPubEnd());
		result.add(getPriStart());
		result.add(getPriEnd());
		result.add(getProtocol());
		result.add(getLocalIpAddr());
		result.add(getRemoteIpStar());
		result.add(getRemoteIpEnd());
		result.add(getRemoteOnOff());
		result.add(getRuleOnOff());

		return result.toString();
	}

	private String getRuleIndex() {
		return "\"ruleIndex\":" + ruleIndex;
	}

	private String getAppName() {
		return String.format(FORMAT_STRING, "appName", appName);
	}

	private String getPubStart() {
		return String.format(FORMAT_INT, "pubStart", pubStart);
	}

	private String getPubEnd() {
		return String.format(FORMAT_INT, "pubEnd", pubEnd);
	}

	private String getPriStart() {
		return String.format(FORMAT_INT, "priStart", priStart);
	}

	private String getPriEnd() {
		return String.format(FORMAT_INT, "priEnd", priEnd);
	}

	private String getProtocol() {
		// The typo is deliberate in 'protocAl' - originates in Hitron firmware
		return String.format(FORMAT_STRING, "protocal", protocol);
	}

	private String getLocalIpAddr() {
		return String.format(FORMAT_STRING, "localIpAddr", localIpAddr);
	}

	private String getRemoteOnOff() {
		return String.format(FORMAT_STRING, "remoteOnOff", remoteOnOff);
	}

	private String getRemoteIpStar() {
		return String.format(FORMAT_STRING, "remoteIpStar", remoteIpStar);
	}

	private String getRemoteIpEnd() {
		return String.format(FORMAT_STRING, "remoteIpEnd", remoteIpEnd);
	}

	private String getRuleOnOff() {
		return String.format(FORMAT_STRING, "ruleOnOff", ruleOnOff ? "ON" : "OFF");
	}

	@Override
	public int compareTo(ForwardingRule o) {
		if (this == o) {
			return 0;
		}
		if (o == null) {
			return -1;
		}
		return this.ruleIndex - o.ruleIndex;
	}
}
