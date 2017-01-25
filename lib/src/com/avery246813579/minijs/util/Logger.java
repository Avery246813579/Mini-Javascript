package com.avery246813579.minijs.util;

public class Logger {
	public static final String WARNING = "WARNING", ERROR = "ERROR", INFO = "INFO";
	
	public static void log(String message){
		log(INFO, message);
	}
	
	public static void log(String prefix, String message){
		System.out.println("MiniJS " + prefix + " >> " + message);
	}
	
	public static void error(String message){
		log(ERROR, message);
	}
}
