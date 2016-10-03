package com.avery246813579.minijs.components;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avery246813579.minijs.templates.TemplateHandler;
import com.avery246813579.minijs.util.Logger;

public class MiniConfig {
	private Map<String, Object> properties = new HashMap<String, Object>();
	private List<String> values = new ArrayList<String>();
	private String property = null;
	private String name;
	private File file;

	public MiniConfig(String name) {
		file = new File(name);
		this.name = name;

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		init();
		validify();
	}

	public void init() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String line = null;
			while ((line = reader.readLine()) != null) {
				line = line.trim();

				if (line.isEmpty()) {
					continue;
				}

				if (line.startsWith("#")) {
					continue;
				}

				doLine(line);
			}

			if (property != null) {
				properties.put(property, values);

				property = null;
				values = new ArrayList<String>();
			}

			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void validify() {
		List<String> lines = new ArrayList<String>(Arrays.asList(""));

		for (String template : TemplateHandler.TEMPLATES) {
			if (!properties.containsKey(template)) {
				Logger.log(Logger.WARNING, "\"" + template + "\" configuration not found in config. Adding in the default.");
				lines.add("");
				lines.addAll(TemplateHandler.getTemplate(template).getLines());
			}
		}

		if (lines.size() > 1) {
			try (FileWriter fw = new FileWriter(name, true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter out = new PrintWriter(bw)) {
				for (String line : lines) {
					out.println(line);
				}
			} catch (IOException e) {
				Logger.error("Couldn't update configuration file with missing sections");
			}

			Logger.log("Configuration updating due missing information");
			properties.clear();
			init();
		}
	}

	public void doLine(String line) {
		if (!line.contains(":") && !line.contains("-")) {
			Logger.log(Logger.ERROR, "Config is broken, please fix! 1");
		}

		if (line.startsWith("-")) {
			if (property == null) {
				Logger.log(Logger.ERROR, "Config is broken, please fix! 2" + line);
			}

			values.add(line.substring(line.indexOf('-') + 1).trim());
			return;
		}

		String[] args = line.split(":");

		if (property != null) {
			properties.put(property, values);

			property = null;
			values = new ArrayList<String>();
			doLine(line);
			return;
		}

		if (args.length == 1) {
			property = args[0].trim();
			return;
		}

		properties.put(args[0].trim(), line.substring(line.indexOf(':') + 1).trim());
	}

	public Map<String, Object> getProperties() {
		return properties;
	}
}
