package dts.com.adminportal.model;

public class AudioRoutesEdit {

	public static final String DISPLAY_MODEL  = "//*[@id='display-model']";
	public static final String UUID  = "//*[@id='entity-uuid']";
	public static final String SALESFORCE_ID  = "//*[@id='input-salesforceid']";
	public static final String DISPLAY_NAME  = "//*[@id='display-name']";
	public static final String LANGUAGE_CONTAINER  = "//*[@id='lang-div-container']";
	public static final String AUDIO_ROUTE_ID  = "//*[@id='router-routeid']";
	public static final String TYPE  = "//*[@id='router-type']";
	public static final String INPUT_SPECIFICATION  = "//*[@id='input-specifications-div']";
	public static final String  WIRED_CONTAINER  = "//*[@id='input-specifications-div']//tr[2]";
	public static final String WIRED_CHECKBOX  = "//*[@id='checkboxWired']";
	public static final String WIRED_DROPBOX = "//*[@id='input-specifications-div']//tr[2]//td[2]//button";
	public static final String  BLUETOOTH_CONTAINER  = "//*[@id='input-specifications-div']//tr[3]";
	public static final String BLUETOOTH_DROPBOX  = "//*[@id='input-specifications-div']//tr[3]//td[2]//button";
	public static final String BLUETOOTH_CHECKBOX  = "//*[@id='checkboxBluetooth']";
	public static final String ROUTER_TUNING  = "//*[@id='router-tuning']";
	public static final String ACTION_MODULE  = "//*[@id='audio-route-action']";
	public static final String SAVE  = "//*[@id='save_action']";
	public static final String CANCEL  = "//*[@id='cancel_action']";
	
	//Audio route
	public static final String DELETE_TUNING_ICON  = "//img[contains(@onclick,'confirmDeleteTuningFile')]";
	public static final String ADD_TUNING  = "//*[@id='add-tuning-material-btn']/span";
	public static final String AUDIOROUTE_UPLOAD_MESSAGE = "//*[contains(@id,'tuning-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String RETRY_UPLOAD_AUDIOROUTE = "//img[contains(@onclick,'retryUploadTuning')]";
	public static final String CANCEL_UPLOAD_AUDIOROUTE = "//*[@id='tuning-progress']//img[@class='icon-close-icon']";
	public static final String AUDIOROUTE_DRAG_DROP_AREA = "//*[@id='dropzone-tuning']"; 
	public static final String UPLOADED_AUDIO_ROUTE = "//*[@id='uploaded-tuning-material']"; 

	// Image
	public static final String IMAGE_CATALOG  = "//div[@id='product-image']/parent::div";
	public static final String ADD_IMAGE250  = "//*[@id='add-logo124Image-btn']/span";
	public static final String ADD_IMAGE500  = "//*[@id='add-logo256Image-btn']/span";
	public static final String ADD_IMAGE1000  = "//*[@id='add-logo512Image-btn']/span";
	public static final String IMAGE250_DISPLAY  = "//*[@id='logo124ImageShow']";
	public static final String IMAGE500_DISPLAY  = "//*[@id='logo256ImageShow']";
	public static final String IMAGE1000_DISPLAY  = "//*[@id='logo512ImageShow']";
	public static final String CATALOG_IMAGE_UPLOAD_THUMBNAIL_250="//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/th/a/img";
	public static final String CATALOG_IMAGE_UPLOAD_THUMBNAIL_500="//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/th/a/img";
	public static final String CATALOG_IMAGE_UPLOAD_THUMBNAIL_1000="//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[1]/th/a/img";
	public static final String CATALOG_IMAGE_UPLOAD_MESSAGE_250 ="//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String CATALOG_IMAGE_UPLOAD_MESSAGE_500="//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String CATALOG_IMAGE_UPLOAD_MESSAGE_1000="//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/p/span";
	public static final String CATALOG_IMAGE_UPLOAD_INFO_250 = "//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td";
	public static final String CATALOG_IMAGE_UPLOAD_INFO_500 = "//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td";
	public static final String CATALOG_IMAGE_UPLOAD_INFO_1000 = "//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td";
	public static final String CATALOG_IMAGE_TITLE_LINK_250 ="//span[@class='maupload-logo124Image']";
	public static final String CATALOG_IMAGE_TITLE_LINK_500 ="//span[@class='maupload-logo256Image']";
	public static final String CATALOG_IMAGE_TITLE_LINK_1000 ="//span[@class='maupload-logo512Image']";
	public static final String CATALOG_IMAGE_FILE_SIZE_250 ="//div[@id='uploaded-logo124Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/span";
	public static final String CATALOG_IMAGE_FILE_SIZE_500 ="//div[@id='uploaded-logo256Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/span";
	public static final String CATALOG_IMAGE_FILE_SIZE_1000 ="//div[@id='uploaded-logo512Image-material']//div[contains(@id,'media-fileupload')]/table/tbody/tr[2]/td/span";
	public static final String RETRY_UPLOAD_IMAGE_250 ="//div[@id='uploaded-logo124Image-material']//img[contains(@onclick, 'retryUploadImage')]";
	public static final String RETRY_UPLOAD_IMAGE_500 ="//div[@id='uploaded-logo256Image-material']//img[contains(@onclick, 'retryUploadImage')]";
	public static final String RETRY_UPLOAD_IMAGE_1000 ="//div[@id='uploaded-logo512Image-material']//img[contains(@onclick, 'retryUploadImage')]";
	public static final String CANCEL_UPLOAD_IMAGE_250 ="//*[@id='imglogo124Image-progress']//img[@class = 'icon-close-icon']";
	public static final String CANCEL_UPLOAD_IMAGE_500 ="//*[@id='imglogo256Image-progress']//img[@class = 'icon-close-icon']";
	public static final String CANCEL_UPLOAD_IMAGE_1000 ="//*[@id='imglogo512Image-progress']//img[@class = 'icon-close-icon']";
	public static final String IMAGE250_DRAG_DROP_AREA ="//*[@id='dropzone-logo124Image']";
	public static final String IMAGE500_DRAG_DROP_AREA ="//*[@id='dropzone-logo256Image']";
	public static final String IMAGE1000_DRAG_DROP_AREA ="//*[@id='dropzone-logo512Image']";
	public static final String LIGHTBOX_CLOSE ="//*[@id='lightbox']/div[2]/div/div[2]/a";
	public static final String LIGHTBOX_STYLE_IMAGE = "//img[@class = 'lb-image']";
	public static final String CANCEL_UPLOAD_MARKETING = "//*[@id='marketing-progress']//img[@class = 'icon-close-icon']";
	
	public static final String IMG_NAME[]={
		"250x250.jpg",
		"500x500.jpg",
		"1000x1000.jpg",
		"250x250.png",
		"500x500.png",
		"1000x1000.png"
	};
	
	public static final String CONTENT_CHANNEL_CAPABILITY[]={
		"Select all that apply",
		"1 (Single Channel)",
		"2 (Stereo)"
	};
	
	public static final String UPLOAD_FILE_MESSSAGE[]={
		"File upload successful",
		"Invalid file",
		"File upload canceled",
		"File upload error",
		"Automatically resize. Original 250x250 px",
		"Automatically resize. Original 500x500 px",
		"Automatically resize. Original 1000x1000 px"
	};

	
}
