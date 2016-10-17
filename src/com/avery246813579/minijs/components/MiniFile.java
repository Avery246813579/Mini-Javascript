package com.avery246813579.minijs.components;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.avery246813579.minijs.util.Logger;

public class MiniFile {
	private MiniConfig config;

	public MiniFile(String configLocation) {
		config = new MiniConfig(configLocation);
		File configFile = new File(configLocation);

		if (!Boolean.parseBoolean((String) config.getProperties().get("enabled"))) {
			Logger.log(Logger.INFO, "Mini-ing ceased due to config being disabled");
			return;
		}

		@SuppressWarnings("unchecked")
		List<String> compressing = (List<String>) config.getProperties().get("compressing");
		List<String> segments = new ArrayList<String>();

		boolean hitReg = false;
		List<File> files = new ArrayList<File>();
		for (String file : compressing) {
			if (file.endsWith(".min.js") && Boolean.parseBoolean((String) config.getProperties().get("ignoreMin"))) {
				if (hitReg) {
					MiniSegment segment = new MiniSegment(this, files);
					segment.fetch();

					String segText = segment.getSegment();

					if (segText == null) {
						Logger.error("Could not load following config: " + configLocation);
						return;
					} else {
						segments.add(segText);
					}

					files.clear();
				}

				String segText = new MiniSegment(this, new File(configFile.getParentFile().getAbsolutePath() + "/" + file)).getText();

				if (segText == null) {
					Logger.error("Could not load following config: " + configLocation);
					return;
				} else {
					segments.add(segText);
				}

				hitReg = false;
				continue;
			}

			hitReg = true;
			files.add(new File(configFile.getParentFile().getAbsolutePath() + "/" + file));
		}

		if (hitReg) {
			MiniSegment segment = new MiniSegment(this, files);
			segment.fetch();

			String segText = segment.getSegment();

			if (segText == null) {
				Logger.error("Could not load following config: " + configLocation);
				return;
			} else {
				segments.add(segText);
			}
		}

		MiniExporter exporter = new MiniExporter(configFile.getParentFile().getAbsolutePath() + "/" + (String) config.getProperties().get("export"), segments);

		if (exporter.isSuccess()) {
			Logger.log(Logger.INFO, "File mini-ed successfully!");
		}
	}

	public MiniConfig getConfig() {
		return config;
	}
}
