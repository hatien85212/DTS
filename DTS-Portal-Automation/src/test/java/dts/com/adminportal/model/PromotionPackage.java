package dts.com.adminportal.model;

import org.openqa.selenium.WebElement;

public class PromotionPackage {
	public WebElement Txt_PromotionID;
	public WebElement promotionName;
	public WebElement deleteLink;
	public WebElement warningMessage;

	public PromotionPackage() {
		this.Txt_PromotionID = null;
		this.promotionName = null;
		this.deleteLink = null;
		this.warningMessage = null;

	}
	
	public static final String[] MESSAGE = {
		"Promotion does not exist",
		"Promotion is not published yet",
		"Promotion is duplicate",
		"Promotion doesn't apply for this device"
	};
	
}
