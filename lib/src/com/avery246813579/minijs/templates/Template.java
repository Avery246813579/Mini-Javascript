package com.avery246813579.minijs.templates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.avery246813579.minijs.util.Logger;

public class Template {
	private List<String> lines = new ArrayList<String>();
	private boolean loaded;
	private String name;

	public Template(String name) {
		this.name = name;
	}

	public void load() {
		if (loaded) {
			return;
		}

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(TemplateHandler.TEMPLATE_LOCATOIN + name + ".temp")));
		} catch (Exception ex) {
			Logger.error("Couldn't load template: " + name);
		}

		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}

			reader.close();
		} catch (IOException e) {
			Logger.error("Couldn't load template: " + name);
		}
	}
	
	public String toString(){
		return lines.toString();
	}

	public List<String> getLines() {
		return lines;
	}

	public boolean isLoaded() {
		return loaded;
	}
}
