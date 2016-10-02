package com.avery246813579.minijs;

public class Logger {
	public static final String WARNING = "WARNING", ERROR = "ERROR", INFO = "INFO";
	
	public static void log(String message){
		log(INFO, message);
	}
	
	public static void log(String prefix, String message){
		System.out.println("MiniJS " + prefix + " >> " + message);
		
		if(prefix == ERROR){
			System.exit(0);
		}
	}
	
	public static void error(String message){
		log(ERROR, message);
	}
}
