package uk.co.travelplaces.email;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

/**
 * 
 * @author craigp
 * @version 0.1
 * @since 21/01/2015
 * @description Send an email received from queue / drop folder
 */
public class EmailSender extends EmailJsonSender implements Processor 
{
	static Logger logger = Logger.getLogger("uk.co.travelplaces");

	public void process(Exchange arg0) throws Exception 
	{
		String jsonMessage = arg0.getIn().getBody(String.class);
		setServer((String) arg0.getProperty("emailServerAddress"));
		createEmail(jsonMessage);
		sendEmail();
	}
}
