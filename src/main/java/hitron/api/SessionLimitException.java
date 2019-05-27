package hitron.api;

public class SessionLimitException extends RuntimeException {

	private static final long serialVersionUID = -8908336717612777787L;

	public SessionLimitException(String message) {
		super(message);
	}
}
