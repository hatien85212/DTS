package com.dts.adminportal.model;

import java.util.ArrayList;
import java.util.Hashtable;


public class DeviceEdit {
	
	// Product
	public static final String SALESFORCE_ID = "//*[@id='salesforceId']";
	public static final String COMPANY = "//*[@id='companies']";
	public static final String BRAND = "//*[@id='brand']";
	public static final String NAME = "//*[@id='name']";
	public static final String TYPE = "//*[@id='type']";
	public static final String EAGLE_VERSION = "//*[@id='eagleVersion']";
	public static final String PRODUCT_TYPE = "//*[@id='deviceProductType']";
	public static final String OS = "//*[@id='platform-list']/parent::div/div";
	public static final String OS_license = "//*[@id='device-form']/fieldset/div[9]/div";
	public static final String DTS_TRACKING_ID = "//*[@id='user-panel']/div/form/fieldset/div[4]/span";
	
	// Headphone:X Licensing Info
	public static final String DCC_SECRET = "//*[@id='device-form']/fieldset/fieldset/div/div[2]/div/span";
	public static final String INIT_KEY_NAME = "//*[@id='licensing-keyname']";
	public static final String INIT_KEY_VALUE = "//*[@id='licensing-keyvalue']";
	
	// Audio Routes
	public static final String ADD_DEFAULT_AUDIO_ROUTE = "//*[@id='add-tuning-material-btn']/span";
	public static final String RETRY_DEFAULT_AUDIO_ROUTE = ".//*[@id='tuning-fileupload--1']/table/tbody/tr[1]/td[2]/a";
	public static final String ADD_AUDIO_ROUTE_CONTAINER = "//*[@id='defaulttuning-material-div']";
	public static final String UPLOADED_DEFAULT_AUDIO_ROUTE = "//*[@id='uploaded-defaulttuning-material']";
	public static final String DELETE_AUDIO_ROUTE_ICON = "//img[contains(@onclick,'confirmDeleteTuningFile')]";
	public static final String ANOTHER_ROUTE = "//*[@id='another_route']";
	public static final String ADD_CUSTOM_TUNING = "//*[@id='add-custom-tuning-material-btn']/span";
	public static final String RETRY_CUSTOM_TUNING = "//*[@id='tuning-fileupload--2']/table/tbody/tr[1]/td[2]/a";
	public static final String AUDIO_ROUTE_TYPE = "//*[@id='audioroutetype']";
	public static final String AUDIO_ROUTE = "//*[@id='audioroute']";
	public static final String UPLOADED_DEFAULT_AUDIO_NAME = "//*[@id='uploaded-defaulttuning-material']/div/div[2]/a";
	public static final String UPLOADED_CUSTOM_TUNNING_NAME = "//*[@id='another_route']/div[1]/a[@name='custom-tuning-media']";
	public static final String DELETE_CUSTOM_TUNNING_ICON = "//*[@id='another_route']/div[1]/a[@class='btn btn-link btn-delete-tuning-link']";
	public static final String AUDIO_ROUTE_INVALID_MESSAGE = "//*[contains(@id,'tuning-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String SUB_DROPDOWN_AUDIO_ROUTE = "//*[@id='sub-audio-route']";
	public static final String UPLOADED_CUSTOM_TUNNING_NAME_2 = "//*[@id='another_route']/div[2]/a[@name='custom-tuning-media']";
	public static final String UPLOADED_CUSTOM_TUNNING_NAME_3 = "//*[@id='another_route']/div[3]/a[@name='custom-tuning-media']";
	public static final String UPLOADED_CUSTOM_TUNNING_NAME_4 = "//*[@id='another_route']/div[4]/a[@name='custom-tuning-media']";
	public static final String UPLOADED_CUSTOM_TUNNING_NAME_5 = "//*[@id='another_route']/div[5]/a[@name='custom-tuning-media']";
	public static final String UPLOADED_CUSTOM_TUNNING_NAME_6 = "//*[@id='another_route']/div[6]/a[@name='custom-tuning-media']";
	public static final String UPLOADED_CUSTOM_TUNNING_NAME_7 = "//*[@id='another_route']/div[7]/a[@name='custom-tuning-media']";
	public static final String UPLOADED_CUSTOM_TUNNING_NAME_VER20="//*[@id='user-panel']/div/form/fieldset/div[15]/a";
	
	// Device Tuning
	public static final String DEVICE_TUNING_SECTION_LABEL ="//div[3]/div/div/div[1]/div/form/fieldset/div[15]/h4";
	public static final String ADD_DEVICE_TUNING = "//*[@id='add-device-tuning-material-btn']/span";
	public static final String DEVICE_TUNING = "//*[@id='devicetuningtype']";
	public static final String UPLOADED_DEVICE_TUNING_NAME = "//*[@id='device_tuning_route']/div[1]/a[@name='custom-tuning-media']";
	public static final String DELETE_DEVICE_TUNNING_ICON ="//*[@id='device_tuning_route']/div[1]/a[@class='btn btn-link btn-delete-tuning-link']";
	
	// In-box Headphones
	public static final String INBOX_HEADPHONES = ".//*[@id='inboxHeadphoneContainer']/h4";
	public static final String INBOX_INSTRUCTION = ".//*[@id='inboxHeadphoneContainer']/label";
	public static final String INBOX_FIELD = ".//*[@id='inboxHeadphoneFilter']";
	public static final String INBOX_PRODUCT_NAME = ".//*[@id='inboxHeadphoneName']";
	public static final String DELETE_INBOX = ".//*[@id='deleteInboxHeadphone']";
	// Featured Accessory Promotions
	public static final String FEATURED_HEADPHONE = "//*[@id='device-form']/fieldset/div[contains(@data-bind, 'visible: featureHeadphoneEnable')]";
	public static final String PROMO_SLOT = "//*[@id='numPromoSlots']";
	public static final String GLOBAL_PROMOTIONS_CONTAINER = "//div[starts-with(@class,'has-switch')]";
	public static final String SWITCH_PROMO = "//*[@id='globalPromo']/div/div/label";
	public static final String GLOBAL_PROMOTIONS_ON = "//*[@id='globalPromo']/div/div/span[1]";
	public static final String GLOBAL_PROMOTIONS_OFF = "//*[@id='globalPromo']/div/div/span[2]";
	public static final String PROMOTION_SLOT_1 = "//*[@id='0']/div[2]/input[4]";
	public static final String PROMOTION_SLOT_2 = "//*[@id='1']/div[2]/input[4]";
	
	// Device Promotions
	public static final String PROMOTION_CONTAINER = "//*[@id='promotion_container']";
	public static final String INSTRUCTION_TEXT = "//*[@id='device-form']/fieldset/div[contains(@data-bind, 'visible: featureHeadphoneVisible')]/div[3]/label";
	public static final String Instruction_Text = "Drag to reorder promotion list.";
	public static final String PROMOTION_1 = "//*[@id='0']/div[2]/input[4]";
	public static final String PROMOTION_2 = "//*[@id='1']/div/input[2]";
	public static final String PROMOTION_3 = "//*[@id='2']/div/input[2]";
	public static final String DELETE_1 = "//*[@id='0']/div/a";
	public static final String LABEL_7 = ".//*[@id='6']/div[1]/label";
	public static final String PROMOTION_7 = ".//*[@id='6']/div[2]/input[4]";
	public static final String DELETE_7 = ".//*[@id='6']/div[2]/a";
	public static final String MESSAGE_PRO_1 = "//*[@id='0']/div[2]/span[2]";
	public static final String MESSAGE_PRO_2 = "//*[@id='1']/div[2]/span[2]";
	public static final String PROMOTION_NAME_1 = "//*[@id='0']/div[2]/span[1]";
	
	// Additional Device Promotions
	public static final String INSTRUCTION_TEXT_ADDITION_PROMOTION = "//*[@id='additional_promotion_title']";
	public static final String PROMOTION_ADDITION_CONTAINER = "//*[@id='additional_promotion_container']";
	public static final String INSTRUCTION_ADDITION_TEXT = "//*[@id='device-form']/fieldset/div[contains(@data-bind, 'visible: featureHeadphoneVisible')]/div[3]/label";
	public static final String PROMOTION_ADDITION_5 = "//*[@id='4']/div[2]/input[4]";
	public static final String PROMOTION_ADDITION_6 = "//*[@id='5']/div[2]/input[4]";
	public static final String INVALID_PROMOTION_ADDITION_5 = "//*[@id='promotionIdError4']";
	public static final String INVALID_PROMOTION_ADDITION_6 = "//*[@id='promotionIdError5']";
	
	// Action Module
	public static final String ACTION_MODULE = "//*[@id='add-device-action']";
	public static final String SAVE = "//*[@id='btnSaveRecord']";
	public static final String CANCEL = "//*[@id='add-device-action']/a[2]";
	
	// Title default display if company name = ''
	public static final String TITLE_DEFAULT = "//*[@id='device-form']/fieldset/div[1]/span[1]";
	public static final String DEVICE_TILLE = "//*[@id='device-form']/fieldset/div[1]";
	public static final String TUNING_MESSAGE = "//*[@id='model-tuning-files-msg']";
	public static final String CUSTOM_TUNING_MESS = "//*[@id='tuning-fileupload--2']/table/tbody/tr[2]/td/p/span";
	public static final String CUSTOM_TUNING_MESS1 = ".//*[@id='custom-tuningfile-error-message']";
		
	// Default audio route
	public static final String DELETE_DEFAULT_AUDIO_ROUTE = "//*[contains(@id,'tuning-fileupload-')]/img";
	public static final String DEFAULT_AUDIO_ROUTE_FILE = "//*[@id='930']";
	
	// Multi-channel room models
	public static final String MULTI_CH_ROOM = "//*[@id='device-form']/fieldset/div[contains(@data-bind, 'visible: mcRoomEnable')]";
	public static final String MCROOM0= "//*[@id='room_rodels_group']/label[1]/span";
	public static final String MCROOM1 = "//*[@id='room_rodels_group']/label[2]/span";
	public static final String MCROOM2 = "//*[@id='room_rodels_group']/label[3]/span";
	public static final String CHECK_BOX_MCROOM = "//*[@id='roommodels']";
	public static final String CHECK_BOX_MCROOM_1ST = ".//*[@id='roommodels' and @value='1']";
	public static final String CHECK_BOX_MCROOM_2ND = ".//*[@id='roommodels' and @value='2']";
	public static final String CHECK_BOX_MCROOM_3TH = ".//*[@id='roommodels' and @value='3']";
	
	
	// Headphone Image Sizes
	public static final String LARGE_IMAGE = ".//*[@id='deviceImageSizes' and @value='IMAGE_LARGE']";
	public static final String MEDIUM_IMAGE = ".//*[@id='deviceImageSizes' and @value='IMAGE_MEDIUM']";
	public static final String SMALL_IMAGE = ".//*[@id='deviceImageSizes' and @value='IMAGE_SMALL']";
	public static final String NO_IMAGES = ".//*[@id='deviceImageSizes' and @value='NO_IMAGE']";
	public static final String HEADPHONE_IMAGE_SIZES_INSTRUCTION = "//*[@id='device-form']/fieldset/label[2]";
	
	// Headphone Brands
	public static final String HEADPHONE_BRAND = "//*[@id='device-form']/fieldset/div[contains(@data-bind, 'visible: headphoneBrandEnable')]";
	public static final String ALL_BRANDS = "//*[@id='headphoneBrandSelectionAllBrands']";
	public static final String ONLY_BRANDS = "//*[@id='headphoneBrandSelectionOnlyBrands']";
	public static final String BRAND_FILTER = "//*[@id='brandFilter']";
	public static final String BRAND_LABEL = ".//*[contains(@id,'brand-id-icon-trash-')]/div/div[1]";
	public static final String BRAND_ICON_TRASH = ".//*[contains(@id,'icon-trash-') and @class='icon icon-trash']";
	
	// Warning messages	
	public static final String TYPE_REQUIRE = "//*[@id='type-error-message']";
	public static final String PRODUCT_TYPE_REQUIRE = "//*[@id='device-product-type-error-message']";
	public static final String COMPANY_REQUIRE = "//*[@id='partner-error-message']";
	public static final String BRAND_REQUIRE = "//*[@id='brand-error-message']";
	public static final String NAME_REQUIRE = "//*[@id='name-error-message']";
	public static final String KEY_NAME_REQUIRE = "//*[@id='licensing-keyname-error-message']";
	public static final String ROOM_MODEL_REQUIRE = "//*[@id='roommodels-error-message']";
	public static final String IMAGE_SIZE_REQUIRE = ".//*[@id='deviceimagesizes-error-message']";
	public static final String HEADPHONE_BRAND_REQUIRE = "//*[@id='brand-empty-message']";
	public static final String PROMOTION_ERROR = "//*[@id='0']/div/span[2]";
	public static final String SWITCH_PRODUCT_TYPE = "html/body/div[6]/div[1]";
	public static final String SELECT_PRODUCT_TYPE_BEFORE_UPLOAD = "html/body/div[6]/div[1]";
	
	
	public static String promotionslotlabel(int promotionslot){
		//promotion slot ID begin with 0, so when filling the slot number, the id should minus 1
		promotionslot = promotionslot -1;
		return ".//*[@id='"+promotionslot +"']/div[1]/label";
	}
	
	public static String reorder_icon(int promotionslot){
		//promotion slot ID begin with 0, so when filling the slot number, the id should minus 1
		promotionslot = promotionslot -1;
		return ".//*[@id='"+promotionslot +"']/div[1]/img";
	}
	
	public static final Hashtable<String, String> getHash(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("id", SALESFORCE_ID);
		data.put("type", TYPE);
		data.put("eagle version", EAGLE_VERSION);
		data.put("product type", PRODUCT_TYPE);
		data.put("company", COMPANY);
		data.put("brand", BRAND);
		data.put("name", NAME);
		data.put("os", OS);
		data.put("license",INIT_KEY_NAME);
		data.put("audio route", ADD_DEFAULT_AUDIO_ROUTE);
		data.put("custom tuning", ADD_CUSTOM_TUNING);
		data.put("promotion slot",PROMO_SLOT);
		data.put("global promotion", GLOBAL_PROMOTIONS_ON);
		data.put("MC room models", MCROOM0);
		data.put("save", SAVE);
		return data;
	}
	public static final Hashtable<String, String> getHashLicense(){
		Hashtable<String, String> data = new Hashtable<String, String>();
		data.put("type", TYPE);
		data.put("eagle version", EAGLE_VERSION);
		data.put("product type", PRODUCT_TYPE);
		data.put("brand", BRAND);
		data.put("name", NAME);
		data.put("os", OS_license);
		data.put("license",INIT_KEY_NAME);
		data.put("audio route", ADD_DEFAULT_AUDIO_ROUTE);
		data.put("custom tuning", ADD_CUSTOM_TUNING);
		data.put("promotion slot",PROMO_SLOT);
		data.put("global promotion", GLOBAL_PROMOTIONS_ON);
		data.put("save", SAVE);
		return data;
	}
	
	public static final String types[] = {
		"- Select -",
		"Device",
		"App"
	};
	
	public static final String OS_LIST = "//*[@id='platform-list']";
	public static final String os[] = {
		"Select all that apply", 
		"Android", 
		"iOS", 
		"Windows Phone", 
		"Windows Desktop", 
		"Linux", 
		"Other"
	};
	
	public static enum routes {
		Select("- Select -"), //0
		Line_out0("Line-out0 - Line Out"), 
		Bluetooth0("Bluetooth0 - Bluetooth"), // 2
		USB0("USB0 - USB"),
		Attached0_Internal_Speakers("Attached0 - Internal Speakers (Default)"), // 4
		Attached1_Internal_Speakers_Off("Attached1 - Internal Speakers (Off)"), 
		Attached2_Internal_Speakers_Mode_1("Attached2 - Internal Speakers (mode 1)"), // 6
		Attached3_Internal_Speakers_Mode_2("Attached3 - Internal Speakers (mode 2)"),
		Attached4_Internal_Speakers_Mode_3("Attached4 - Internal Speakers (mode 3)"), // 8
		Attached5_Internal_Speakers_Mode_4("Attached5 - Internal Speakers (mode 4)"), 
		Attached6_Internal_Speakers_Mode_5("Attached6 - Internal Speakers (mode 5)"), 
		Attached7_Internal_Speakers_Mode_6("Attached7 - Internal Speakers (mode 6)"), 
		Attached8_Internal_Speakers_Mode_7("Attached8 - Internal Speakers (mode 7)"), 
		Combo0("Combo0 - Dual Audio"), // 10
		Device_Headphone("Device Headphone Settings");
		private final String name;

		routes (String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	
		
		public static String[] getNames() {
			routes[] route = values();
			String[] result = new String[route.length];
			for (int i = 0; i < route.length; i++) {
				result[i] = route[i].getName();
			}
			return result;
		}
	};
	public static enum routes_low {
		Select("- Select -"), //0
		Attached1_Internal_Speakers_Off("Attached1 - Internal Speakers (Off)"), 
		Attached2_Internal_Speakers_Mode_1("Attached2 - Internal Speakers (mode 1)"), // 6
		Attached3_Internal_Speakers_Mode_2("Attached3 - Internal Speakers (mode 2)"),
		Attached4_Internal_Speakers_Mode_3("Attached4 - Internal Speakers (mode 3)"), // 8
		Device_Headphone("Device Headphone Settings");
		private final String name;

		routes_low (String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	
		
		public static String[] getNames() {
			routes_low[] route = values();
			String[] result = new String[route.length];
			for (int i = 0; i < route.length; i++) {
				result[i] = route[i].getName();
			}
			return result;
		}
	};
	public static enum routes_medium {
		Select("- Select -"), //0
		Line_out0("Line-out0 - Line Out"), 
		Bluetooth0("Bluetooth0 - Bluetooth"), // 2
		USB0("USB0 - USB"),
		Attached0_Internal_Speakers("Attached0 - Internal Speakers (Default)"), // 4
		Attached1_Internal_Speakers_Off("Attached1 - Internal Speakers (Off)"), 
		Attached2_Internal_Speakers_Mode_1("Attached2 - Internal Speakers (mode 1)"), // 6
		Attached3_Internal_Speakers_Mode_2("Attached3 - Internal Speakers (mode 2)"),
		Attached4_Internal_Speakers_Mode_3("Attached4 - Internal Speakers (mode 3)"), // 8
		Device_Headphone("Device Headphone Settings");
		private final String name;

		routes_medium (String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	
		
		public static String[] getNames() {
			routes_medium[] route = values();
			String[] result = new String[route.length];
			for (int i = 0; i < route.length; i++) {
				result[i] = route[i].getName();
			}
			return result;
		}
	};
	
	public static enum Standard_Accessories {
		Select("- Select -"), // 0
		Over_Ear_Headphones("Over-Ear Headphones"), 
		Earbuds("Earbuds"),  // 2
		Ear_piece("Ear-piece"), 
		External_Speakers("External Speakers"), // 4
		Car_Audio("Car-Audio"); 
		
		private final String name;

		Standard_Accessories (String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
		
		public static String[] getNames() {
			Standard_Accessories[] accessories = values();
			String[] result = new String[accessories.length];
			for (int i = 0; i < accessories.length; i++) {
				result[i] = accessories[i].getName();
			}
			return result;
		}
	};
	
	public static enum Product_Types {
		Select("- Select -"), // 0
		HPX_High("DTS:X Ultra"), 
		HPX_Medium("DTS:X Premium"),  // 2
		HPX_Low("DTS Audio Processing"); 
		
		private final String type;

		Product_Types (String type) {
			this.type = type;
		}

		public String getType() {
			return this.type;
		}
		
		public static String[] getNames() {
			Product_Types[] accessories = values();
			String[] result = new String[accessories.length];
			for (int i = 0; i < accessories.length; i++) {
				result[i] = accessories[i].getType();
			}
			return result;
		}
	};
	public static enum Product_Types_Eagle_1_4 {
		Select("- Select -"), // 0
		HPX_High("HPX High"), 
		HPX_Medium("HPX Medium"),  // 2
		HPX_Low("HPX Low"); 
		
		private final String type;

		Product_Types_Eagle_1_4 (String type) {
			this.type = type;
		}

		public String getType() {
			return this.type;
		}
		
		public static String[] getNames() {
			Product_Types_Eagle_1_4[] accessories = values();
			String[] result = new String[accessories.length];
			for (int i = 0; i < accessories.length; i++) {
				result[i] = accessories[i].getType();
			}
			return result;
		}
	};
	
	public static enum requires {
		Type_is_required("Type is required"), // 0
		Product_type_is_required("Product Type is required"),
		Company_is_required("Company is required"), //2
		Brand_is_required("Brand is required"), 
		Name_is_required("Name is required"), //4
		Key_is_required("Key is required"),
		Custom_Tunning_File_is_required("Custom Tunning File is required"), //6
		Room_Models_is_required("Room Models is required"), 
		Headphone_image_sizes_is_required("Headphone image sizes is required"), //8
		Invalid_route("Invalid route"), 
		Invalid_file_contents("! Invalid file: The Route object does not match the Audio Route selection."), //10
		Invalid_AEQ_is_missing("Invalid file: 48k coefficients for AEQ is missing in iirCoefs."),
		Invalid_EQ_is_missing("Invalid file: 48k coefficients for 3D Headphone EQ is missing.") ,//13
		Select_headphone_available("Select which headphone image sizes will be made available to users. For on-device databases, only one size is recommended."),
		Invalid_file_type("! Invalid file: Please upload a dtscs tuning file."), //14
		Invalid_file_Please_upload_the_completed_DTS_Headphone_X_tuning_file("! Invalid file: Please upload the completed DTS Headphone:X tuning file."),
		Invalid_file_48k_coefficients_is_missing_in_TBHDX_MBHL("! Invalid file: 48k coefficients is missing in TBHDX and/or MBHL."),
		Invalid_file_The_MBHL_isnot_applied_to_this_tuning_type("! Invalid file: The MBHL isn't applied to this tuning type. Please upload another tuning file without MBHL data."),
		Switch_Product_Type_Low("Please note: Existing custom tuning of Device Headphone Settings (Default) audio route will be deleted."),
		Switch_Product_Type("Please note: Existing Custom Tunings and Device Tunings will be deleted."),
		Switch_Eagle_Version("Please note: Existing current Audio Routes/Device tuning files will be deleted. This operation cannot be reverted."),
		Invalid_file_Please_upload_the_Eagle_v1_1_tuning_file("! Invalid file: Please upload an appropriate Product tuning file."),
		Invalid_file_Please_upload_the_Eagle_v2_0_tuning_file("! Invalid file: The Route object does not match the Standard Accessories: Line-out0 or Bluetooth0 or USB."),
		Invalid_file_Please_upload_a_exptune_tuning_file("Invalid file: Please upload a exptune tuning file."),
		Select_Product_Type_Before_Upload("Please select the Product Type before selecting the Audio Route.");
		private final String name;

		requires (String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	};
	public static ArrayList<String> getErroMessageXpath(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("//*[@id='type-error-message']");
		list.add("//*[@id='partner-error-message']");
		list.add("//*[@id='brand-error-message']");
		list.add("//*[@id='name-error-message']");
		list.add("//*[@id='licensing-keyname-error-message']");
		return list;
	}
	
	public static final String DEFAULT_AUDIO_ERROR_MESSAGE = "//*[@id='tuning-fileupload--1']/table/tbody/tr[2]/td/p/span";
	public static final String OK_BUTTON= "html/body/div[6]/div[2]/a[2]";
	public static final String Audio_Routes[] = {
		"Default Audio Route",
		"Custom Tuning"
	};
	
	public static enum Content_Modes {
		Select("- Select -"), // 0
		Mode_Default("Default Mode"),
		Mode_1("Attached 2 - Music"), 
		Mode_2("Attached 3 - Movies"),  // 2
		Mode_3("Attached 4 - Game"); 
		
		private final String type;

		Content_Modes (String type) {
			this.type = type;
		}

		public String getType() {
			return this.type;
		}
		
		public static String[] getNames() {
			Content_Modes[] accessories = values();
			String[] result = new String[accessories.length];
			for (int i = 0; i < accessories.length; i++) {
				result[i] = accessories[i].getType();
			}
			return result;
		}
	};
	public static enum Content_Modes_Hig {
		Select("- Select -"), // 0
		Attached_0_Default_Mode("Attached 0 - Default"),
		Attached_2_Music("Attached 2 - Music"),
		Attached_3_Movies("Attached 3 - Movies"),
		Attached_4_Voice("Attached 4 - Game"),
		Attached_5_Game_1("Attached 5 - Game 1"),
		Attached_6_Game_2("Attached 6 - Game 2"),
		Attached_7_Game_3("Attached 7 - Game 3"),
		Attached_8_Custom("Attached 8 - Custom");
		
		private final String type;

		Content_Modes_Hig (String type) {
			this.type = type;
		}

		public String getName() {
			return this.type;
		}
		
		public static String[] getNames() {
			Content_Modes_Hig[] accessories = values();
			String[] result = new String[accessories.length];
			for (int i = 0; i < accessories.length; i++) {
				result[i] = accessories[i].getName();
			}
			return result;
		}
	};
	public static enum Content_Modes_Medium {
		Select("- Select -"), // 0
		Attached_0_Default("Attached 0 - Default"),
		Attached_2_Music("Attached 2 - Music"),
		Attached_3_Movies("Attached 3 - Movies"),
		Attached_4_Game("Attached 4 - Game"),;
		
		private final String type;

		Content_Modes_Medium (String type) {
			this.type = type;
		}

		public String getName() {
			return this.type;
		}
		
		public static String[] getNames() {
			Content_Modes_Medium[] accessories = values();
			String[] result = new String[accessories.length];
			for (int i = 0; i < accessories.length; i++) {
				result[i] = accessories[i].getName();
			}
			return result;
		}
	};
	public static enum Content_Modes_Low {
		Select("- Select -"), // 0
		Attached_2_Music("Attached 2 - Music"),
		Attached_3_Movies("Attached 3 - Movies"),
		Attached_4_Game("Attached 4 - Game"),;
		
		private final String type;

		Content_Modes_Low (String type) {
			this.type = type;
		}

		public String getName() {
			return this.type;
		}
		
		public static String[] getNames() {
			Content_Modes_Low[] accessories = values();
			String[] result = new String[accessories.length];
			for (int i = 0; i < accessories.length; i++) {
				result[i] = accessories[i].getName();
			}
			return result;
		}
	};
	
	public static enum Eagle_Version {
		Eagle1_4("Eagle 1.4"), 
		Eagle2_0("Eagle 2.0"); 
		
		private final String name;

		Eagle_Version (String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
		
		public static String[] getNames() {
			Eagle_Version[] versions = values();
			String[] result = new String[versions.length];
			for (int i = 0; i < versions.length; i++) {
				result[i] = versions[i].getName();
			}
			return result;
		}
	};
	public static enum OS_version {
		SELECT_ALL_THAT_APPLY("Select all that apply"), 
		ANDROIND("Android"),
		IOS("iOS"), 
		WINDOWNS_PHONE("Windowns Phone"), 
		WINDOWNS_DESKTOP("Windowns Desktop"), 
		LINUX("Linux"), 
		OTHER("Other"); 
		
		private final String name;

		OS_version (String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
		
		public static String[] getNames() {
			OS_version[] versions = values();
			String[] result = new String[versions.length];
			for (int i = 0; i < versions.length; i++) {
				result[i] = versions[i].getName();
			}
			return result;
		}
	};
	
	public static enum Device_Tunings {
		Select("- Select -"), // 0
		GPEQ_Tuning("GPEQ Tuning"), 
		CTC_Tuning("CTC Tuning");
		
		
		private final String type;

		Device_Tunings (String type) {
			this.type = type;
		}

		public String getName() {
			return this.type;
		}
		
		public static String[] getNames() {
			Device_Tunings[] accessories = values();
			String[] result = new String[accessories.length];
			for (int i = 0; i < accessories.length; i++) {
				result[i] = accessories[i].getName();
			}
			return result;
		}
};
	public static enum DB_Attached_List {
		Attached0("Attached0"),
		Attached1("Attached1"),
		Attached2("Attached2"),
		Attached3("Attached3"),
		Attached4("Attached4"),
		Attached5("Attached5"),
		Attached6("Attached6"),
		Attached7("Attached7"),
		Attached8("Attached8");
		
		private final String type;

		DB_Attached_List (String type) {
			this.type = type;
		}

		public String getName() {
			return this.type;
		}
		public static String[] getNames() {
			DB_Attached_List[] accessories = values();
			String[] result = new String[accessories.length];
			for (int i = 0; i < accessories.length; i++) {
				result[i] = accessories[i].getName();
			}
			return result;
		}
}
}
