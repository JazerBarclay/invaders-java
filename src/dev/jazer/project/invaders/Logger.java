package dev.jazer.project.invaders;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple synchronised logging for info, warning and error terminal messages
 * @author Jazer Barclay
 *
 */
public class Logger {
	
	/**
	 * Prints to the console the current time, parent class, message and exception<br><br>
	 * If an exception is defined, the output will be set in red with syserr
	 * 
	 * @param type
	 * @param parent
	 * @param message
	 * @param e
	 */
	private static void print(String type, Object parent, String message, Exception e) {
		String text = new SimpleDateFormat("HH:mm:ss (SSS)").format(new Date()) + " : " + type + " : " + parent.getClass().getSimpleName() + " : " + message;
		if (e == null) {
			System.out.println(text);
		} else {
			System.err.println(text);
			e.printStackTrace();
		}
	}
	
	public static synchronized void info(Object parent, String message) {
		print("INFO", parent, message, null);
	}
	
	public static synchronized void warn(Object parent, String message) {
		print("WARN", parent, message, null);
	}
	
	public static synchronized void error(Object parent, String message, Exception e) {
		print("ERROR", parent, message, e);
	}
	
}
