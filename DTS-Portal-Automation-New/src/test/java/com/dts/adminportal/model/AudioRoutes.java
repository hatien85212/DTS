package com.dts.adminportal.model;


public class AudioRoutes {
	public static final String HEADER  = "//*[@id='router-audio-brand-info']/div/span";
	public static final String DESCRIPTION  = "//*[@id='router-audio-brand-info']/b[contain(text(),'There are the ultimate fallback']";
	public static final String STANDARD_ACCESSORIES_LABEL  = "//*[@id='router-audio-brand-info']/h5[1]";
	public static final String INTERNAL_SPEAKER_LABEL  = "//*[@id='router-audio-brand-info']/h5[2]";
	public static final String OTHER_AUDIO_ROUTES_LABEL  = "//*[@id='router-audio-brand-info']/h5[3]";
	public static final String HEADER_STRING = "Audio Routes";
	public static final String DESCRIPTION_STRING = "There are the ultimate fallback tunings for all audio routes supported by Headphone:X Partner Portal";
	public static final String STANDARD_ACCESSORIES_STRING = "Standard Accessories: Line-out0 and Bluetooth0";
	public static final String INTERNAL_SPEAKER_STRING = "Internal speaker: Attached0 (Portrait) and Attached1 (Landscape)";
	public static final String OTHER_AUDIO_ROUTES_STRING  = "Other Audio Routes";
	public static final String LINK_OTHER_AUDIO = "//*[@id='other_router']/a";
	public static final String LINK_STANDARD_AUDIO = "//*[@id='standard_router']/a";
	public static final String LINK_STEREO_ROOM = "//*[@id='stereo_room']/a";
	public static final String LINK_MCROOM = "//*[@id='multi_channel_room']/a";
	public static final String MULTI_CHANNEL_CONTAINER = "//*[@id='multi_channel_room']";
	public static final String STANDARD_ROUTE_CONTAINER = "//*[@id='standard_router']";
	public static final String OTHER_ROUTE_CONTAINER = "//*[@id='other_router']";
	public static final String STEREO_ROOM_MODELS_CONTAINER = "//*[@id='stereo_room']";
	public static final String MCROOM0 = "//*[@id='multi_channel_room']/a[1]";
	public static final String MCROOM1 = "//*[@id='multi_channel_room']/a[2]";
	public static final String MCROOM2 = "//*[@id='multi_channel_room']/a[3]";
	public static final String Attached6 = "//*[@id='other_router']/a[7]";
	public static final String Attached7 = "//*[@id='other_router']/a[8]";
	public static final String Attached8 = "//*[@id='other_router']/a[9]";
	
	
	
	//Room models
	public static final String STEREO_ROOM0  = ".//*[@id='13968']";
	public static final String ROOMMODEL_UUID  = "//*[@id='entity-uuid']";
	public static final String STEREO_ROOM_ID  = "//*[@id='router-routeid']";
	
	public static final String SIZE_LIST[] = {
		"160X160",									
		"290X290", 
		"664X664",
	};
	
	public static final String getLang_selectxpath(int numoflang){
		return ".//*[@id='lang-div-container']/div["+numoflang+"]/select";
	}
	
	
	public static final String LANGUAGE_LIST[] = {
		"- Select -", 
		"Chinese (Simplified)", 
		"Chinese (Traditional)", 
		"French (France)",
		"German", 
		"Italian", 
		"Japanese",
		"Korean", 
		"Russian", 
		"Spanish (Spain)"
	};
	
	public static final String NAME_LIST[] = {
		"",
		"å¤–éƒ¨æ‰¬å£°å™¨",									
		"å¤–éƒ¨æ�šè�²å™¨", 
		"Haut-parleur externe",
		"externe Lautsprecher",									
		"altoparlante esterno", 
		"å¤–éƒ¨ã‚¹ãƒ�?ãƒ¼ã‚«ãƒ¼",
		"오버 �?�어 헤드�?�",									
		"�?’�?½�?µÑˆ�?½�?¸�?¹ �?´�?¸�?½�?°�?¼�?¸�?º", 
		"altavoz externo",
	};
	
	public enum Standard_Routes {
		Over_Ear_Headphones("Over-Ear Headphones"),	//0								
		Earbuds("Earbuds"), 
		Ear_piece("Ear-piece"), //2
		External_Speakers("External Speakers"),									
		Car_Audio("Car-Audio"); //4
		
		private final String name;

		Standard_Routes(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public static String[] getStandard() {
			Standard_Routes[] names = values();
			String[] result = new String[names.length];
			for (int i = 0; i < names.length; i++) {
				result[i] = names[i].getName();
			}
			return result;
		}
	};
	
	public enum Other_Routes {
		Attached0_Internal_Speakers("Attached0 - Internal Speakers (Default)"),
		Attached1_Internal_Speakers("Attached1 - Internal Speakers (Off)"),
		Attached2_Internal_Speakers("Attached2 - Internal Speakers (mode 1)"),
		Attached3_Internal_Speakers("Attached3 - Internal Speakers (mode 2)"),
		Attached4_Internal_Speakers("Attached4 - Internal Speakers (mode 3)"),
		Attached5_Internal_Speakers("Attached5 - Internal Speakers (mode 4)"),
		Attached6_Internal_Speakers("Attached6 - Internal Speakers (mode 5)"),
		Attached7_Internal_Speakers("Attached7 - Internal Speakers (mode 6)"),
		Attached8_Internal_Speakers("Attached8 - Internal Speakers (mode 7)"),
		Combo0_Dual_Audio("Combo0 - Dual Audio"),
		Default_External_Audio("Default - External Audio");
		
		private final String name;

		Other_Routes(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public static String[] getOther() {
			Other_Routes[] other_routes = values();
			String[] result = new String[other_routes.length];
			for (int i = 0; i < other_routes.length; i++) {
				result[i] = other_routes[i].getName();
			}
			return result;
		}
	};
	

	public static enum Stereo_Room_Models {
		Wide("StereoRoom0 - Wide"),									
		In_front("StereoRoom1 - In-front");
		
		private final String name;

		Stereo_Room_Models (String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
		
		public static String[] getNames() {
			Stereo_Room_Models[] models = values();
			String[] result = new String[models.length];
			for (int i = 0; i < models.length; i++) {
				result[i] = models[i].getName();
			}
			return result;
		}
	};
	
	public static final String MC_ROOM[] = {
		"McRoom0 -",
		"McRoom1 -",
		"McRoom2 -"
	};
}
