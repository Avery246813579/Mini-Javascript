package com.avery246813579.minijs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MiniSegment {
	private List<File> files = new ArrayList<File>();
	private String segment;

	public MiniSegment(List<File> files) {
		this.files = files;
		
		segment = request(encode(fetchText()));
	}

	private String encode(String text) {
		try {
			return URLEncoder.encode(text, StandardCharsets.UTF_8.name());
		} catch (Exception ex) {
			Logger.log(Logger.ERROR, "Could not encode text");
		}

		return null;
	}

	private String fetchText() {
		BufferedReader reader = null;
		String fetch = "";

		for (File file : files) {
			try {
				reader = new BufferedReader(new FileReader(file));

				String line = null;
				while ((line = reader.readLine()) != null) {
					fetch += line + "\n";
				}
			} catch (Exception ex) {
				Logger.log(Logger.ERROR, "Could not read file " + file.getName());
			}
		}

		return fetch;
	}

	private String request(String encoding) {
		return Request.sendRequest("http://closure-compiler.appspot.com/compile", RequestMethod.POST, null,
				"compilation_level=" + Config.getProperties().get("compilationLevel") + "&output_format=text&output_info=compiled_code&js_code=" + encoding);

	}
	
	public String getSegment(){
		return segment;
	}
}
