package com.avery246813579.minijs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {
	public static void copyFile(File copy, File location) {
		BufferedReader reader = null;
		
		System.out.println(copy.getAbsolutePath());
		if(!copy.exists()){
			Logger.error("Copy file doesn't exist");
		}
		
		if(!location.exists()){
			try {
				location.createNewFile();
			} catch (IOException e) {
				Logger.error("Couldn't create configuration template");
			}
		}

		try {
			reader = new BufferedReader(new FileReader(copy));
		} catch (Exception ex) {
			Logger.error("Couldn't read configuration template");
		}

		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new FileWriter(location));
		} catch (Exception ex) {
			Logger.error("Couldn't write configuration template");
		}

		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				writer.write(line);
				writer.newLine();
			}

			reader.close();
			writer.close();
		} catch (Exception ex) {
			Logger.error("Couldn't write configuration template");
		}
	}
	
	public static void copyResource(String resource, File location){
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new InputStreamReader(FileUtil.class.getResourceAsStream(resource)));
		} catch (Exception ex) {
			Logger.error("Couldn't read configuration template");
		}
		
		if(!location.exists()){
			try {
				location.createNewFile();
			} catch (IOException e) {
				Logger.error("Couldn't create configuration template");
			}
		}

		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new FileWriter(location));
		} catch (Exception ex) {
			Logger.error("Couldn't write configuration template");
		}

		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				writer.write(line);
				writer.newLine();
			}

			reader.close();
			writer.close();
		} catch (Exception ex) {
			Logger.error("Couldn't write configuration template");
		}
	}
}
