package com.avery246813579.minijs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MiniFile {
	public static final String VERSION = "1.0.0"; 
	
	public static void main(String[] args){
		new MiniFile();
	}
	
	public MiniFile() {
		Logger.log("Loading v" + VERSION + " of MiniJS");
		
		Config config = new Config();
		
		List<String> compressing = (List<String>) config.getProperties().get("compressing");
		List<File> files = new ArrayList<File>();
		for(String file : compressing){
			files.add(new File(file));
		}
		
		MiniSegment segment = new MiniSegment(files);
		System.out.println(segment.getSegment());
		
		List<String> segments = new ArrayList<String>();
		segments.add(segment.getSegment());
		
		new MiniExporter((String) config.getProperties().get("export"), segments);
	}
}
