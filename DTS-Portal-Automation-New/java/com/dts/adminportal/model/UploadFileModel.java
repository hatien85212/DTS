package com.dts.adminportal.model;

public class UploadFileModel {

	public static enum FileName {
		IMG250_JPG("250x250.jpg"), IMG500_JPG("500x500.jpg"), IMG1000_JPG("1000x1000.jpg"), 
		IMG250_PNG("250x250.png"), IG500_PNG("500x500.png"), IMG1000_PNG("1000x1000.png"),
		CHRYSAN_JPG("Chrysanthemum.jpg"), DESERT_JPG("Desert.jpg"), HYDRA_JPG("Hydrangeas.jpg");

		private final String type;

		FileName(String type) {
			this.type = type;
		}

		public String getName() {
			return this.type;
		}

		public static String[] getNames() {
			FileName[] types = values();
			String[] result = new String[types.length];
			for (int i = 0; i < types.length; i++) {
				result[i] = types[i].getName();
			}
			return result;
		}
	}
	
	
}
