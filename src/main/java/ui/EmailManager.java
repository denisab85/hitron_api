package ui;

import org.apache.log4j.Logger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import email.Email;
import email.Email.Message;
import hitron.api.Api;
import hitron.forwarding.ForwardingRule;
import hitron.forwarding.ForwardingStatus;

import static util.Utils.*;

public class EmailManager {

	private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private Logger log = Logger.getLogger(EmailManager.class.getName());
	private final Pattern IP_ADDRESS_RX = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3}");

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
		Matcher ipMatcher = IP_ADDRESS_RX.matcher(message);
		if (ipMatcher.find()) {
			String remoteIp = ipMatcher.group(0);
			return applyFirewallRule(remoteIp, true);
		} else if (message.equalsIgnoreCase("off")) {
			return applyFirewallRule(null, false);
		}
		return null;
	}

	private static boolean removeRuleIfNameExists(List<ForwardingRule> rules, String ruleName) {
		for (ForwardingRule rule : rules) {
			if (rule.getAppName().equalsIgnoreCase(ruleName)) {
				rules.remove(rule);
				return true;
			}
		}
		return false;
	}

	private String applyFirewallRule(String remoteIp, boolean enable) {
		StringJoiner report = new StringJoiner(System.lineSeparator());
		Api api = new Api("http://" + getProp("hitron.host"), getProp("hitron.user"), getProp("hitron.password"));
		List<ForwardingRule> rules = null;

		String ruleName = getProp("hitron.rule");
		String port = "3396";
		ForwardingRule rule = new ForwardingRule(1, ruleName, port, port, "3389", "3389", "TCP", "192.168.0.6", remoteIp, remoteIp, "Specific",
				enable ? "ON" : "OFF");

		try {
			// Enable Firewall
			ForwardingStatus status = api.getForwardingStatus();
			report.add("Forwarding status (before): " + (status.getEnabled() ? "Enabled" : "Disabled"));
			status.setEnabled(true);
			api.setForwardingStatus(status);

			// Add forwarding
			rules = api.getForwardingRules();
			boolean existed = removeRuleIfNameExists(rules, "RDP");

			report.add(ruleName + " rule existed: " + existed);

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
