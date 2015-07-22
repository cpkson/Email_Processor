package test.uk.co.parknet;

import static org.junit.Assert.fail;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONException;
import org.junit.Test;

import uk.co.parknet.email.EmailJsonSender;

/**
 * Unit testing of the EmailJsonSender Class
 * 
 * @author craigp
 * @version 0.1
 * @since 11/03/2015
 */
public class EmailJsonSenderTest 
{
	/**
	 * Unit test to check for JSONException
	 */
	@Test(expected = JSONException.class)
	public void testHandlingOfInvalidJSON()
	{
		EmailJsonSender sender = new EmailJsonSender();
		sender.setServer("tp-exch-01.tp.local");
		try 
		{
			//by setting invalid JSON we cause a JSONException error
			sender.createEmail("hello");
		}
		catch (AddressException e) 
		{
			fail("Test failed to run");
		}
		catch (MessagingException e)
		{
			fail("Test failed to run");
		}
	}
	
	/**
	 * Unit test to check for AddressException
	 * 
	 * @throws MessagingException
	 */
	@Test(expected = AddressException.class)
	public void testHandlingOfIncorrectAddresses() throws MessagingException
	{
		EmailJsonSender sender = new EmailJsonSender();
		sender.setServer("tp-exch-01.tp.local");
		
		try
		{
			//by setting an email address without an @ symbol we cause an AdresssException
			sender.createEmail("{\"to\":[\"testing\"],\"cc\":[],\"bcc\":[],\"from\":[],\"subject\":\"\",\"body\":\"\",\"attachments\":[]}");
		}
		catch(JSONException e)
		{
			fail("Test failed to run");
		}
	}

	/**
	 * Unit test to check for NullPointerException
	 */
	@Test(expected = NullPointerException.class)
	public void testSendingOfNullEmail()
	{
		EmailJsonSender sender = new EmailJsonSender();
		sender.setServer("tp-exch-01.tp.local");
		
		try 
		{
			sender.sendEmail();
		}
		catch (MessagingException e)
		{
			fail("Test failed to run");
		}
	}
	
	/**
	 * Unit test to check for MessagingException
	 * 
	 * @throws MessagingException
	 */
	@Test(expected = MessagingException.class)
	public void testSendingOfEmail() throws MessagingException
	{
		EmailJsonSender sender = new EmailJsonSender();
		sender.setServer("tp-exch-01.tp.local");
	
		try
		{
			//by setting an address that doesn't exist on the exchange server we cause an MessagingException
			sender.createEmail("{\"to\":[\"testing@testing.co.uk\"],\"cc\":[],\"bcc\":[],\"from\":[],\"subject\":\"\",\"body\":\"\",\"attachments\":[]}");
		}
		catch(JSONException e)
		{
			fail("Test failed to run");
		}
		catch(MessagingException e)
		{
			fail("Test failed to run");
		}
		sender.sendEmail();
	}
}