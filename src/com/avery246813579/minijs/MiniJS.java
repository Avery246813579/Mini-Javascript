package com.avery246813579.minijs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MiniJS {
	public static final String VERSION = "1.0.0"; 

	public static void main(String[] args){
		new MiniJS();
	}
	
	public MiniJS(){
		Logger.log("Loading v" + VERSION + " of MiniJS");

		File mainFolder = new File(System.getProperty("user.dir"));
		for(File file : mainFolder.listFiles()){
			if(file.isDirectory()){
				
			}
		}
		
		List<File> files = fetchConfigs();
		for(File file : files){
			System.out.println(file.getAbsolutePath());
		}
		
		new MiniFile("minijs.conf");
	}
	
	public List<File> fetchConfigs(){
		return downFolder(new File(System.getProperty("user.dir")));
	}
	
	private List<File> downFolder(File file){
		List<File> configs = new ArrayList<File>();
		
		for(File element : file.listFiles()){
			if(element.isDirectory()){
				configs.addAll(downFolder(element));
				continue;
			}
			
			if(element.getName().equals("minijs.conf")){
				configs.add(element);
			}
		}
		
		return configs;
	}
}
