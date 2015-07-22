package uk.co.parknet;

import org.apache.camel.spring.Main;

/**
 * 
 * Initialise the message consumer process
 * @author craigp
 * @version 0.1
 * @since 21/05/2015
 */
public class BootLoader
{
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
		Main main = new Main();
		main.setApplicationContextUri("/applicationContext.xml");
		
		try
		{
			//pass the program arguments to run()
			main.run();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}