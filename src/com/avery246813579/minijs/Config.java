package com.avery246813579.minijs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
	private Map<String, Object> properties = new HashMap<String, Object>();
	private File file;

	public Config(String name) {
		file = new File(name);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		init();
	}

	String property = null;
	List<String> values = new ArrayList<String>();

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

			Logger.log(Logger.INFO, "Configs: " + properties.toString());

			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
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

		// Lets stop the last property
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

	public void reset() {

	}
	
	public Map<String, Object> getProperties(){
		return properties;
	}
}
