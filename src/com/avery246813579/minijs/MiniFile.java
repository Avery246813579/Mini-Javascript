package com.avery246813579.minijs;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
		List<String> segments = new ArrayList<String>();

		boolean hitReg = false;
		List<File> files = new ArrayList<File>();
		for(String file : compressing){
			if(file.endsWith(".min.js") && Boolean.parseBoolean((String) config.getProperties().get("ignoreMin"))){
				if(hitReg){
					MiniSegment segment = new MiniSegment(files);
					segment.fetch();
					segments.add(segment.getSegment());
					files.clear();
				}
				
				segments.add(new MiniSegment(new File(file)).getText());
				hitReg = false;
				continue;
			}
			
			hitReg = true;
			files.add(new File(file));
		}
		
		if(hitReg){
			MiniSegment segment = new MiniSegment(files);
			segment.fetch();
			segments.add(segment.getSegment());
		}
		
		new MiniExporter((String) config.getProperties().get("export"), segments);
	}
}
