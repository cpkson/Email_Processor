package uk.co.parknet.email;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.json.JSONException;

import uk.co.parknet.SettingsManager;

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
	private static final SettingsManager settings = SettingsManager.getInstance();
	
	public void process(Exchange arg0) throws Exception 
	{
		String jsonMessage = arg0.getIn().getBody(String.class);
		logger.info(jsonMessage);
		
		/*
		 * We try and retrieve the email server address from the header
		 * if it doesn't exist use the value in the CouchDB database
		 * if it does use the value from the header
		 */
		Object headerSetting = arg0.getIn().getHeader("emailServerAddress");
		
		String server = "";
		if(headerSetting == null)
		{
			server = settings.getProperty("emailServerAddress");
		}
		else
		{
			if(headerSetting instanceof String)
			{
				server = (String) headerSetting;
			}
		}
		
		setServer(server);
		logger.info(jsonMessage);
		logger.info(server);
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