package uk.co.parknet;

import org.apache.camel.spring.Main;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * Initialise the message consumer process
 * @author craigp
 * @version 0.1
 * @since 21/05/2015
 */
public class BootLoader
{
	private static Logger logger = LogManager.getLogger("uk.co.parknet");
	
	/**
	 * main
	 * @param args program arguments
	 */
	public static void main(String[] args)
	{
		BootLoader loader = new BootLoader();
		loader.boot();
	}

	/** 
	 * 
	 */
	private void boot()
	{
		/*
		CamelContext c = new DefaultCamelContext();
		ConnectionFactory factory = new ActiveMQConnectionFactory("consumer", "Gr33n21F1sh12H1llman", "tcp://192.168.0.110:61616");
		c.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(factory));
		
		try {
			c.addRoutes
			(
					new RouteBuilder()
					{
						public void configure()
						{
							from("jms:queue:emailQueue").process(new EmailSender());
						}
					}
				);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			c.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			c.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		Main main = new Main();
		main.setApplicationContextUri("/applicationContext.xml");
		
		try
		{
			//pass the program arguments to run()
			main.run();
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}
	}
}