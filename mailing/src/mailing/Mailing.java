
package mailing;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * A utility class for sending e-mail message with attachment.
 * @author www.codejava.net
 *
 */
public class Mailing {
	
	/**
	 * Sends an e-mail message from a SMTP host with a list of attached files. 
	 * 
	 */
	public static void main(String args[])
					throws AddressException, MessagingException {
		// sets SMTP server properties
                String host="smtp.gmail.com";
                String port="587";
                String userName="";
                String password="";
                String to[]={""};
                String subject="hello last test";
                String message="This is content";
                for(int i=0;i<to.length;i++){
                    try{
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", userName);
		properties.put("mail.password", password);

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};
		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(userName));
		InternetAddress[] toAddresses = { new InternetAddress(to[i]) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// adds attachments
		messageBodyPart = new MimeBodyPart();
                String filename[] = {"D:/java/choser.java","D:/java/mytbl.mdb"};
                for(int j=0;j<filename.length;j++){
                DataSource source = new FileDataSource(filename[j]);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(filename[j]);
                multipart.addBodyPart(messageBodyPart);
                }
		// sets the multi-part as e-mail's content
		msg.setContent(multipart);

		// sends the e-mail
		Transport.send(msg);
                System.out.println("Mail successfully send to : "+to[i]);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
	}
}
