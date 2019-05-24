package hitron.forwarding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;

public class ForwardingRules {

	@Getter
	private List<ForwardingRule> rules = new ArrayList<>();

	private static final String IMPORT_STRING = "^\\[(?:(\\{.+?\\})(,\\{.+?\\})*)?\\]$";
	private static final String IMPORT_RECORD = "(\\{[\\\"\\w\\.%-,:-]+?\\})";

	public ForwardingRules() {
	}

	public ForwardingRules(String string) {
		if (!string.trim().matches(IMPORT_STRING)) {
			throw new RuntimeException(String.format("Init string doesn't match pattern '%s': %s", IMPORT_STRING, string));
		}
		Pattern record = Pattern.compile(IMPORT_RECORD);
		Matcher matcher = record.matcher(string);
		while (matcher.find()) {
			ForwardingRule rule = new ForwardingRule(matcher.group(1));
			rules.add(rule);
		}
		Collections.sort(rules);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rules == null) ? 0 : rules.hashCode());
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
		ForwardingRules other = (ForwardingRules) obj;
		if (rules == null) {
			if (other.rules != null)
				return false;
		} else if (!rules.equals(other.rules))
			return false;
		return true;
	}

	public void add(ForwardingRule rule) {
		this.rules.add(rule);
		enumerateRules();
	}

	public void add(ForwardingRule rule, int index) {
		this.rules.add(index, rule);
		enumerateRules();
	}

	@Override
	public String toString() {
		return rules.toString();
	}

	public ForwardingRule get(int index) {
		return rules.get(index);
	}

	public int findByName(String name) {
		int result = -1;
		int index = 0;
		for (ForwardingRule rule : rules) {
			if (rule.appName.equals(name)) {
				result = index;
				break;
			}
			index++;
		}
		return result;
	}

	public void remove(int index) {
		rules.remove(index);
		enumerateRules();
	}

	private void enumerateRules() {
		for (int i = 0; i < rules.size(); i++) {
			rules.get(i).setRuleIndex(i + 1);
		}
	}

	public boolean contains(ForwardingRule rule) {
		return this.rules.contains(rule);
	}

}
