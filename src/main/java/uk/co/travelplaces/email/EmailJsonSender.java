package uk.co.travelplaces.email;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author craigp
 * @version 0.1
 * @since 26/2/2015
 */
public class EmailJsonSender
{
	private MimeMessage message;
	private String server;
	
	public EmailJsonSender()
	{
		message = null;
		server = null;
	}
	
	/**
	 * Set the server we are connecting too
	 * 
	 * @param server
	 */
	public void setServer(String server)
	{
		this.server = server;
	}
	
	/**
	 * Create a email from a structured jsonMessage
	 * 
	 * @param jsonMessage
	 * @throws MessagingException 
	 * @throws JSONException 
	 * @throws AddressException 
	 */
	public void createEmail(String jsonMessage) throws AddressException, JSONException, MessagingException
	{
		//get the json object from the exchanged message
		JSONObject obj = new JSONObject(jsonMessage);
		
		//get the system properties, i.e where the mail server is
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", server);
		Session session = Session.getDefaultInstance(props);
		
		//create a new message
		message = new MimeMessage(session);
		//construct who to send the message from
		message.addFrom(createAddresses(obj.getJSONArray("from")));
		//construct who to send the mesage to
		message.addRecipients(Message.RecipientType.TO, createAddresses(obj.getJSONArray("to")));
		//construct who to bcc into the message
		message.addRecipients(Message.RecipientType.BCC, createAddresses(obj.getJSONArray("bcc")));
		//construct who to cc into the message
		message.addRecipients(Message.RecipientType.CC, createAddresses(obj.getJSONArray("cc")));
		//set the subject of the message
		message.setSubject(obj.getString("subject"));
		//build the message body including attachments
		message.setContent(buildBody(obj.getJSONArray("attachments"), obj.getString("body")));
	}
	
	/**
	 * Send the message to the exchange server
	 * 
	 * @throws MessagingException
	 */
	public void sendEmail() throws MessagingException
	{
		Transport.send(message);
	}
	
	/**
	 * This parameterised version of sendEmail
	 * is for unit testing purposes only, it 
	 * sends the message to the exchange server
	 * 
	 * @param message
	 * @throws MessagingException
	 */
	public void sendEmail(MimeMessage message) throws MessagingException
	{
		Transport.send(message);
	}
	
	/**
	 * Construct the internet addresses from JSONArray
	 * 
	 * Create an internet address array containing the email
	 * address to send an email to, this is created from a json array
	 * which contains the email addresses as strings
	 * 
	 * @since 21/05/2015
	 * @param a JSONArray containing email addresses as strings
	 * @return InternetAddress[] an internet adresses array containing email addresses
	 * @throws AddressException
	 * @throws JSONException
	 */
	public InternetAddress[] createAddresses(JSONArray a) throws AddressException, JSONException
	{
		InternetAddress[] b = new InternetAddress[a.length()];
		
		//iterate over the json array and store the value in InternetAddess[]
		for(int i = 0; i < a.length(); i++)
		{
			b[i] = new InternetAddress(a.getString(i),true);
		}
		return b;
	}
	
	/**
	 * Construct an email body
	 * 
	 * Construct an email body from multiple parts, multiple attachments stored
	 * in a JSONArray and the body string.
	 * 
	 * @since 21/05/2015 
	 * @param attachments JSONArray containing objects {"filename": " ", "path": " "}
	 * @param body the text element of the email body
	 * @return Multipart the constructed email body
	 * @throws MessagingException
	 */
	public Multipart buildBody(JSONArray attachments, String body) throws MessagingException
	{
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(body);
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		
		//add each attachment to the email by iterating over the objects
		if(attachments.length()>0)
		{
			for(int i = 0; i < attachments.length(); i++)
			{
				messageBodyPart = new MimeBodyPart();
				//get the files
				DataSource source = new FileDataSource(attachments.getJSONObject(i).getString("path"));
				messageBodyPart.setDataHandler(new DataHandler(source));
				//set the attachment name
				messageBodyPart.setFileName(attachments.getJSONObject(i).getString("filename"));
				//add the attachment to the message
				multipart.addBodyPart(messageBodyPart);
			}
		}
		return multipart;
	}
}