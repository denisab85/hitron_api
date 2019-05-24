package util;

import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import org.apache.log4j.Logger;

public class Utils {

	private static final String PROPERTIES_FILE_NAME = "efm";

	private static Logger log = Logger.getLogger(Utils.class.getName());
	private static ResourceBundle prop = ResourceBundle.getBundle(PROPERTIES_FILE_NAME);
	private static Preferences pref = Preferences.userRoot().node(Utils.class.getName());

	public static String getProp(String property) {
		log.debug(String.format("Reading '%s' from property file", property));
		return prop.getString(property);
	}

	public static String getPref(String preference) {
		log.debug(String.format("Reading '%s' from preference file", preference));
		return pref.get(preference, null);
	}

	public static void setPref(String key, String value) {
		log.debug(String.format("Writing '%s = %s' to preference file", key, value));
		pref.put(key, value);
	}

}
