package CSCI5308.GroupFormationTool.AccessControl;

import CSCI5308.GroupFormationTool.Credential.EmailCredential;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

public class SendNotification implements IUserNotifications {

	private Logger logger = Logger.getLogger(this.getClass());

	public void sendUserLoginCredentials(IUser user, String rawPassword) {
		if (user != null && rawPassword != null && !rawPassword.isEmpty()) {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(EmailCredential.userId, EmailCredential.password);
				}
			});
			Message msg = new MimeMessage(session);
			try {
				msg.setFrom(new InternetAddress("Frommail@gmail.com", false));
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
				msg.setSubject("Your new password");
				msg.setContent(rawPassword, "text/html");
				msg.setSentDate(new Date());

				MimeBodyPart messageBodyPart = new MimeBodyPart();
				String s = rawPassword;

				messageBodyPart.setContent(s, "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				Transport.send(msg);

			} catch (AddressException ae) {
				logger.error(String.format("The email address is invalid: ", ae.getMessage()));
			} catch (MessagingException me) {
				logger.error(String.format("Error occurred while sending email message: ", me.getMessage()));
			} catch (Exception genericException) {
				logger.error(genericException);
			}
		}
	}

}
