package ui;

import org.apache.log4j.Logger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import email.Email;
import email.Email.Message;
import hitron.forwarding.ForwardingRule;
import hitron.forwarding.ForwardingRules;
import hitron.forwarding.ForwardingStatus;
import hitron.web.Api;

import static util.Utils.*;

public class EmailManager {

	private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private Logger log = Logger.getLogger(EmailManager.class.getName());
	private final Pattern IP_ADDRESS = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3}");

	public void run() {
		LocalDateTime lastCheckDate = LocalDateTime.now();
		while (true) {
			try {
				lastCheckDate = LocalDateTime.parse(getPref("lastCheckDate"), dateFormat);
			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
				lastCheckDate = lastCheckDate.minusDays(2);
			}

			log.info("lastCheckDate = " + dateFormat.format(lastCheckDate));

			log.info("Receiving email messages");
			Email email = new Email(getProp("smtp"), getProp("imap"), getProp("login"), getProp("password"));
			lastCheckDate = lastCheckDate.plusSeconds(1); // To avoid fetching same message again
			Message message = email.getLastMessage(getProp("lookForFromList"), getProp("lookForSubject"), lastCheckDate);
			lastCheckDate = lastCheckDate.minusSeconds(1);

			if (message != null) {
				lastCheckDate = message.getSentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				System.out.println(message);
				String report = dispatch(message.getBody());
				System.out.println(report);
				email.send("Re: " + getProp("lookForSubject") + " - " + (report.endsWith("true") ? "Success" : "Fail"), report, getProp("login"));
			}

			setPref("lastCheckDate", dateFormat.format(lastCheckDate));

			try {
				TimeUnit.SECONDS.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private String dispatch(String message) {
		Matcher ipMatcher = IP_ADDRESS.matcher(message);
		if (ipMatcher.find()) {
			String remoteIp = ipMatcher.group(0);
			return applyFirewallRule(remoteIp, true);
		} else if (message.equalsIgnoreCase("off")) {
			return applyFirewallRule(null, false);
		}
		return null;
	}

	private String applyFirewallRule(String remoteIp, boolean enable) {
		StringJoiner report = new StringJoiner(System.lineSeparator());
		Api api = new Api("http://" + getProp("hitron.host"));
		ForwardingRules rules = null;

		String ruleName = getProp("hitron.rulr");
		int port = 3396;// random.nextInt(65534) + 1;
		String init = String.format(
				"{\"ruleIndex\":1,\"appName\":\"%s\",\"pubStart\":\"%2$d\",\"pubEnd\":\"%2$d\",\"priStart\":\"3389\",\"priEnd\":\"3389\",\"protocal\":\"TCP\",\"localIpAddr\":\"192.168.0.6\",\"remoteIpStar\":\"%3$s\",\"remoteIpEnd\":\"%3$s\",\"remoteOnOff\":\"Specific\",\"ruleOnOff\":\"ON\"}",
				ruleName, port, remoteIp);
		try {
			api.login(getProp("hitron.user"), getProp("hitron.password"));
			// Enable Firewall
			ForwardingStatus status = api.getForwardingStatus();
			report.add("Forwarding status (before): " + (status.getEnabled() ? "Enabled" : "Disabled"));
			status.setEnabled(true);
			api.setForwardingStatus(status);

			// Add forwarding
			rules = api.getForwardingRules();
			int index = rules.findByName("RDP");

			report.add("RDP forwarding (before): " + (index >= 0 ? "Assigned" : "None"));

			if (index >= 0) {
				rules.remove(index);
			}

			ForwardingRule rule = new ForwardingRule(init);
			rule.setRuleOnOff(enable);
			rules.add(rule);
			api.setForwardingRules(rules);

			report.add("Forwarding status (after): " + (api.getForwardingStatus().getEnabled() ? "Enabled" : "Disabled"));
			report.add("RDP forwarding set: " + api.getForwardingRules().contains(rule));
		} finally {
			api.close();
		}

		return report.toString();
	}

}
