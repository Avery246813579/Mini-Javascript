package com.avery246813579.minijs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.avery246813579.minijs.components.MiniFile;
import com.avery246813579.minijs.templates.TemplateHandler;
import com.avery246813579.minijs.util.FileUtil;
import com.avery246813579.minijs.util.Logger;

public class MiniJS {
	public static final String VERSION = "1.0.0";

	public static void main(String[] args) {
		new MiniJS();
	}

	public MiniJS() {
		Logger.log("Loading v" + VERSION + " of MiniJS");
		new TemplateHandler();
		
		List<File> files = fetchConfigs();
		if (files.size() < 1) {
			FileUtil.copyResource("/Templates/minijs.conf.temp", new File(System.getProperty("user.dir") + "/minijs.conf"));
			Logger.log("Configuration file created, edit it and run the jar again!");
			return;
		}

		for (File file : files) {
			Logger.log("Mini-ing: " + file.getName());
			new MiniFile(file.getAbsolutePath());
		}
	}

	public List<File> fetchConfigs() {
		return downFolder(new File(System.getProperty("user.dir")));
	}

	private List<File> downFolder(File file) {
		List<File> configs = new ArrayList<File>();

		for (File element : file.listFiles()) {
			if (element.isDirectory()) {
				configs.addAll(downFolder(element));
				continue;
			}

			if (element.getName().equals("minijs.conf")) {
				configs.add(element);
			}
		}

		return configs;
	}
}