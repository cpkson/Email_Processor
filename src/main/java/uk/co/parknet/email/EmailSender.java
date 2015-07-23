package uk.co.parknet.email;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.json.JSONException;

/**
 * 
 * @author craigp
 * @version 0.1
 * @since 21/01/2015
 * @description Send an email received from queue / drop folder
 */
public class EmailSender extends EmailJsonSender implements Processor 
{
	static Logger logger = Logger.getLogger("uk.co.parknet");

	public void process(Exchange arg0) throws Exception 
	{
		String jsonMessage = arg0.getIn().getBody(String.class);
		setServer((String) arg0.getIn().getHeader("emailServerAddress"));
		
		try
		{
			createEmail(jsonMessage);
			sendEmail();
		}
		catch(AddressException e)
		{
			logger.error(e.getMessage());
		}
		catch(MessagingException e)
		{
			logger.error(e.getMessage());
		}
		catch(JSONException e)
		{
			logger.error(e.getMessage());
		}
	}
}
