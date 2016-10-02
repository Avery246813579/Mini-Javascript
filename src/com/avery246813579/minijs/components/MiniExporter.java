package com.avery246813579.minijs.components;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.avery246813579.minijs.util.Logger;

public class MiniExporter {
	List<String> segments = new ArrayList<String>();

	public MiniExporter(String file, List<String> segments) {
		this.segments = segments;

		export(checkFile(file));
	}

	private File checkFile(String name) {
		File file = new File(name);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				Logger.log(Logger.ERROR, "Could not create export file");
			}
		}

		return file;
	}

	private void export(File file) {
		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new FileWriter(file));
		} catch (Exception ex) {
			Logger.log(Logger.ERROR, "Can't write to file");
		}

		try {
			for (String segment : segments) {
				writer.write(segment);
				writer.flush();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.log(Logger.ERROR, "Could not write to export file");
		}
		
		try {
			writer.close();
		} catch (Exception ex) {
			Logger.log(Logger.ERROR, "Could not write to export file");
		}
	}
}
