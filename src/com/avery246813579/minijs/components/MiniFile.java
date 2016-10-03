package com.avery246813579.minijs.components;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MiniFile {
	private MiniConfig config;
	
	public MiniFile(String configLocation) {
		config = new MiniConfig(configLocation);
		File configFile = new File(configLocation);
		
		@SuppressWarnings("unchecked")
		List<String> compressing = (List<String>) config.getProperties().get("compressing");
		List<String> segments = new ArrayList<String>();
		
		boolean hitReg = false;
		List<File> files = new ArrayList<File>();
		for(String file : compressing){
			if(file.endsWith(".min.js") && Boolean.parseBoolean((String) config.getProperties().get("ignoreMin"))){
				if(hitReg){
					MiniSegment segment = new MiniSegment(this, files);
					segment.fetch();
					segments.add(segment.getSegment());
					files.clear();
				}
				
				segments.add(new MiniSegment(this, new File(configFile.getParentFile().getAbsolutePath() + "/" + file)).getText());
				hitReg = false;
				continue;
			}
			
			hitReg = true;
			files.add(new File(configFile.getParentFile().getAbsolutePath() + "/" + file));
		}
		
		if(hitReg){
			MiniSegment segment = new MiniSegment(this, files);
			segment.fetch();
			segments.add(segment.getSegment());
		}
		
		new MiniExporter(configFile.getParentFile().getAbsolutePath() + "/" + (String) config.getProperties().get("export"), segments);
	}
	
	public MiniConfig getConfig(){
		return config;
	}
}
