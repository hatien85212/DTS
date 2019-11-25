package com.dts.adminportal.model;

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
	
	public static enum Messages{
		Promotion_NotExist("Promotion does not exist"),
		Promotion_NotPublished("Promotion is not published yet"),
		Promotion_Duplicate("Promotion is duplicate"),
		Promotion_NotApplyDevice("Promotion doesn't apply for this device");
		
		private final String mess;
		
		private Messages(String mess){
			this.mess = mess;
		}
		
		public String getName(){
			return this.mess;
		}
	}
	
}
