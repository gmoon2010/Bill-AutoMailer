import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email 
{
	private final String HOST = "localhost";
	private MimeMessage message;
	private Properties properties;
	private Session session;
	private Contact[] recipients;
	private String fromEmailAddress, messageText, subject;
	
	public Email(Template temp)
	{
		
	}
	
	public Email(Contact[] recipients, String fromEmailAddress, 
			String messageText, String subject)
	{
		this.subject = subject;
		this.fromEmailAddress = fromEmailAddress;
		this.recipients = recipients;
		this.messageText = messageText;
		
		properties = System.getProperties();
		properties.setProperty("mail.smtp.host", HOST);
		
		session = Session.getDefaultInstance(properties);		
		message = new MimeMessage(session);
	}
	
	public void send() throws MessagingException
	{
		createEmail();
		Transport.send(message);
	}
	
	private void createEmail() throws AddressException, MessagingException
	{
		setToAddresses();
		message.setFrom(new InternetAddress(fromEmailAddress));
		message.setSubject(subject);
		message.setText(messageText);
	}
	
	public Contact[] getRecipients()
	{
		return recipients;
	}

	private void setToAddresses() throws AddressException, MessagingException
	{
		for(int i = 0; i < recipients.length; i++)
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(recipients[i].getEmail()));
	}
}
