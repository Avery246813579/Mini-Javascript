package com.avery246813579.minijs.components;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.avery246813579.minijs.util.Logger;

public class MiniExporter {
	private List<String> segments = new ArrayList<String>();
	private boolean success = false;

	public MiniExporter(String file, List<String> segments) {
		this.segments = segments;

		File eFile = checkFile(file);

		if (eFile != null) {
			export(eFile);
		}
	}

	private File checkFile(String name) {
		File file = new File(name);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				Logger.log(Logger.ERROR, "Could not create export file");
				return null;
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
			return;
		}

		try {
			for (String segment : segments) {
				writer.write(segment);
				writer.flush();
			}
		} catch (Exception ex) {
			Logger.log(Logger.ERROR, "Could not write to export file");
			
			try {
				writer.close();
			} catch (IOException e) {
			}

			return;
		}

		try {
			writer.close();
			
			success = true;
		} catch (Exception ex) {
			Logger.log(Logger.ERROR, "Could not write to export file");
		}
	}

	public boolean isSuccess(){
		return success;
	}
}
