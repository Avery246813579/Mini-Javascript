package com.avery246813579.minijs.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.avery246813579.minijs.requests.Request;
import com.avery246813579.minijs.requests.RequestMethod;
import com.avery246813579.minijs.util.Logger;

public class MiniSegment {
	private List<File> files = new ArrayList<File>();
	private MiniFile miniFile;
	private String segment;
	private File file;

	public MiniSegment(MiniFile miniFile, List<File> files) {
		this.files = files;
		this.miniFile = miniFile;
	}

	public MiniSegment(MiniFile miniFile, File file) {
		this.file = file;
		this.miniFile = miniFile;
	}

	public MiniSegment(MiniFile miniFile) {
		this.miniFile = miniFile;
	}

	public void addFile(File file) {
		files.add(file);
	}

	public void fetch() {
		String fetchText = fetchText();
		if(fetchText == null){
			return;
		}
		
		segment = request(encode(fetchText));
	}

	private String encode(String text) {
		try {
			return URLEncoder.encode(text, StandardCharsets.UTF_8.name());
		} catch (Exception ex) {
			Logger.log(Logger.ERROR, "Could not encode text");
		}

		return null;
	}

	public String getText() {
		BufferedReader reader = null;
		String fetch = "";

		try {
			reader = new BufferedReader(new FileReader(file));

			String line = null;
			while ((line = reader.readLine()) != null) {
				fetch += line + "\n";
			}
		} catch (Exception ex) {
			Logger.log(Logger.ERROR, "Could not read file " + file.getName());
			return null;
		}

		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fetch;
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
				return null;
			}
		}
		
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fetch;
	}

	private String request(String encoding) {
		return Request.sendRequest("http://closure-compiler.appspot.com/compile", RequestMethod.POST, null,
				"compilation_level=" + miniFile.getConfig().getProperties().get("compilationLevel") + "&output_format=text&output_info=compiled_code&js_code=" + encoding);

	}

	public String getSegment() {
		return segment;
	}
}
