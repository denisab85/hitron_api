package email;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;
import java.util.StringJoiner;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import lombok.Getter;

public class Email {

	private final String user;

	private final String password;

	private final String imapHost;

	private final Properties props = System.getProperties();

	@Getter
	public class Message {

		Date sentDate;
		String from;
		String subject;
		String body;

		Message(Date sentDate, String from, String subject, String body) {
			super();
			this.sentDate = sentDate;
			this.from = from;
			this.subject = subject;
			this.body = body;
		}

		@Override
		public String toString() {
			StringJoiner result = new StringJoiner(System.lineSeparator());
			result.add(String.format("%-10s%s", "Date:", sentDate));
			result.add(String.format("%-10s%s", "From:", from));
			result.add(String.format("%-10s%s", "Subject:", subject));
			result.add("=========== BEGIN ===========");
			result.add(body);
			result.add("============ END ============");
			return result.toString();
		}
	}

	public Email(String smtpHost, String imapHost, String user, String password) {
		this.imapHost = imapHost;
		this.password = password;
		this.user = user;
		props.setProperty("mail.store.protocol", "imaps");
		props.setProperty("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true"); // TLS
	}

	private static String getTextFromMessage(javax.mail.Message message) {
		String result = null;
		try {
			if (message.isMimeType("text/plain")) {
				result = message.getContent().toString();
			} else if (message.isMimeType("multipart/*")) {
				MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
				result = getTextFromMimeMultipart(mimeMultipart);
			}
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		StringJoiner result = new StringJoiner(System.lineSeparator());
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result.add(bodyPart.getContent().toString());
				break; // without break same text appears twice in my tests
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result.add(org.jsoup.Jsoup.parse(html).text());
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result.add(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
			}
		}
		return result.toString();
	}

	public Message getLastMessage(String from, String subject, LocalDateTime since) {
		return getLastMessage(from, subject, java.util.Date.from(since.atZone(ZoneId.systemDefault()).toInstant()));
	}

	public Message getLastMessage(String from, String subject, Date since) {
		Message result = null;
		Store store = null;
		try {
			Session session = Session.getDefaultInstance(props, null);
			store = session.getStore("imaps");
			store.connect(imapHost, user, password);
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			javax.mail.Message[] messages = inbox.getMessages();
			int i = messages.length - 1;
			boolean exit = false;
			while (!exit) {
				javax.mail.Message message = messages[i];
				String sbj = message.getSubject();
				String frm = message.getFrom()[0].toString();
				Date sentDate = message.getSentDate();
				i--;

				if (sentDate.before(since)) {
					exit = true;
				} else if (sbj.equalsIgnoreCase(subject) && frm.contains(from)) {
					exit = true;
					result = new Message(message.getSentDate(), frm, sbj, getTextFromMessage(message));
				}
				exit |= i < 0;
			}
			inbox.close(false);
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
			try {
				if (store != null) {
					store.close();
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public void send(String subject, String body, String to) {
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}
