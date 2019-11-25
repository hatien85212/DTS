package com.dts.adminportal.controller;

public class UnusedMethodsController {

	/* unused
	public String getCurrentPartner(String xpath) {
		try {
			waitForAjax();
			WebElement footer = driver.findElement(By.xpath(xpath));
			List<WebElement> columns = footer.findElements(By.tagName("li"));
			for (WebElement column : columns) {
				if (column.getAttribute("class").equals("selected")) {
					WebElement webElement = column.findElement(By.tagName("a"));
					addLog(webElement.getAttribute("text"));
					return webElement.getAttribute("text");
					
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException at getCurrentPartner : "
					+ xpath);
		}
		return "";
	}
	*/
//	public boolean isCorrectItemSelected(String xpath, String textInput) {
//		// wait elenment display
//		boolean result = true;
//		waitForAjax();
//		String itemSelected = readField(xpath);
//		addLog("-Check fiter selected");
//		if (!itemSelected.equals(textInput)) {
//			addLog("Fiter select was wrong , selected : " + itemSelected);
//			result = false;
//		} else {
//			addLog("Filter selected : " + itemSelected);
//		}
//		return result;
//	}

//	public String readField(String xpath) {
//		try {
//			waitForAjax();
//			WebElement footer = driver.findElement(By.xpath(xpath));
//			List<WebElement> columns = footer
//					.findElements(By.tagName("option"));
//			for (WebElement column : columns) {
//				if (column.isSelected()) {
//					String selected = column.getText();
//					addLog("Item selected : " + selected);
//					return selected;
//				}
//			}
//		} catch (NoSuchElementException e) {
//			System.err
//					.println("NoSuchElementException at readField : " + xpath);
//		}
//		return "";
//	}

	

//	public boolean selectPartnerByName(String xpath, String name) {
//		// step 1 : Select partner
//		boolean result = false;
//		waitForAjax();
//		try {
//			addLog("-Click dropdown menu view as for choose partner");
//			driver.findElement(By.xpath(xpath)).click();// Xpath.dropDownListViewAs
//			WebElement ul = driver.findElement(By.xpath(xpathListItemPartner));
//			if (!selectValueFromUnorderedList(ul, name)) {
//				System.err.println("Select failed : " + name);
//				result = false;
//			}
//		} catch (NoSuchElementException e) {
//			System.err
//					.println("NoSuchElementException at selectPartnerByName : "
//							+ xpath);
//			result = false;
//		}
//		return result;
//	}

//	public Boolean selectValueFromUnorderedList(WebElement unorderedList,
//			final String value) {
//		try {
//			waitForAjax();
//			List<WebElement> options = unorderedList.findElements(By
//					.tagName("li"));
//			for (WebElement option : options) {
//				if (value.equals(option.getText())) {
//					addLog("-Click partner : " + value);
//					option.findElement(By.tagName("a")).click();
//					waitForAjax();
//					return true;
//				}
//			}
//		} catch (NoSuchElementException e) {
//			System.err
//					.println("NoSuchElementException at selectValueFromUnorderedList : "
//							+ unorderedList);
//		}
//		return false;
//	}

//	public boolean checkHeadingWithEelementDisplayed(String xpath,
//			ArrayList<String> arrayList) {
//		boolean result = true;
//		waitForAjax();
//		try {
//			WebElement footer = driver.findElement(By.xpath(xpath));
//			List<WebElement> columns = footer.findElements(By.tagName("a"));
//			addLog("Column size : " + columns.size());
//			for (WebElement column : columns) {
//				if (column.isDisplayed()) {
//					String text = column.getText().replaceAll(
//							"\\s+\\(\\d+\\)\\s*", "");
//					if (arrayList.contains(text)) {
//						arrayList.remove(text);
//						addLog("Element " + column.getText()
//								+ " Displayed");
//					} else {
//						addLog("Element " + column.getText()
//								+ " doesn't existed!");
//					}
//				}
//			}
//		} catch (NoSuchElementException e) {
//			System.err
//					.println("NoSuchElementException on checkHeadingWithEelementDisplayed : "
//							+ xpath);
//			result = false;
//		}
//		// check element doesn't existed!
//		if (arrayList.size() > 0) {
//			result = false;
//		}
//		return result;
//	}

//	public boolean getFirstItemSelectedByXpath(String xpath) {
//		boolean result = true;
//		try {
//			waitForAjax();
//			WebElement firstColumn = driver.findElement(By.xpath(xpath));
//			if (firstColumn.getText().isEmpty()) {
//				result = false;
//			} else {
//				addLog(firstColumn.getText());
//			}
//		} catch (NoSuchElementException e) {
//			System.err
//					.println("NoSuchElementException on getFirstItemSelectedByXpath : "
//							+ xpath);
//			result = false;
//		}
//		return result;
//	}

//	public boolean verifyLinkAndElements(String xpath, String url, ArrayList<String> listElement) {
//		boolean isPass = true;
//		addLog("- Click link : " + xpath);
//		try {
//			waitForAjax();
//			driver.findElement(By.xpath(xpath)).click();
//			waitForAjax();
//			addLog("- Check All element display");
//			isPass = existsElement(listElement);
//		} catch (NoSuchElementException e) {
//			addLog("No element " + xpath);
//			isPass = false;
//		}
//		return isPass;
//	}

//	public boolean checkURL(String url, String currentURL) {
//		boolean result = true;
//		waitForAjax();
//		if (!currentURL.contains(url)) {
//			addLog("url incorrect");
//			result = false;
//		} else {
//			addLog("url correct : " + driver.getCurrentUrl());
//			result = true;
//		}
//		return result;
//	}

//	public Boolean checkElemenExist(ArrayList<String> arrayList) {
//		// add wait element
//		waitForAjax();
//		for (String item : arrayList) {
//			try {
//				Boolean isPresent = driver.findElements(By.xpath(item)).size() > 0;
//				if (!isPresent) {
//					addLog("Element " + item + " doesn't existed!");
//					return false;
//				}
//			} catch (NoSuchElementException e) {
//				addLog("NoSuchElementException at checkElemenExist");
//				return false;
//			}
//		}
//		return true;
//	}

//	public boolean checkVersion(String xpath, String status) {
//		boolean result = true;
//		try {
//			waitForAjax();
//			WebElement footer = driver.findElement(By.xpath(xpath));
//			List<WebElement> columns = footer.findElements(By.tagName("tr"));
//			ArrayList<String> arrayList = getAllVersion(xpath, columns.size());
//			// check arraylist
//			for (String item : arrayList) {
//				if (!item.contains(status)) {
//					addLog("-Version incorrect");
//					return false;
//				}
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at checkVersion : " + xpath);
//			result = false;
//		}
//		return result;
//	}

//	private ArrayList<String> getAllVersion(String xpath, int size) {
//		ArrayList<String> arrayList = new ArrayList<String>();
//		try {
//			waitForAjax();
//			for (int i = 1; i <= size; i++) {
//				addLog(xpath + "/tr[" + i + "]/td[5]");
//				String colVersion = xpath + "/tr[" + i + "]/td[5]";
//				if (driver.findElements(By.xpath(colVersion)).size() > 0) {
//					arrayList.add(driver.findElement(By.xpath(colVersion))
//							.getText());
//				} else {
//					addLog("No Data!");
//				}
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at getAllVersion : " + xpath);
//		}
//		return arrayList;
//	}
	

//	public boolean selectAndVerifyElementDisplay(String viewAs, String partner,
//			String header, ArrayList<String> listElement) {
//		boolean result = true;
//		try {
//			waitForAjax();
//			// click view as and chosse partner
//			result = selectPartnerByName(viewAs, partner);
//			if (result) {
//				result = verifyElementsByXpath(header, listElement);
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at selectAndVerifyElementDisplay : "
//					+ header);
//			result = false;
//		}
//		return result;
//	}

//	public boolean verifyElementsByXpath(String xpath, ArrayList<String> elements) {
//		// check & get element by xpath
//		boolean result = true;
//		WebElement heading;
//		try {
//			waitForAjax();
//			heading = driver.findElement(By.xpath(xpath));
//			addLog("Found %d elements " + heading.getSize());
//			for (String element : elements) {
//				Boolean isPresent = heading.findElements(By.xpath(element))
//						.size() > 0;
//				if (!isPresent) {
//					addLog("Element " + element + " doesn't existed!");
//					result = false;
//				} else {
//					addLog("Element " + element + "displayed!");
//				}
//			}
//		} catch (NoSuchElementException e) {
//			addLog("Element " + xpath + "doesn't existed!");
//			result = false;
//		}
//		return result;
//	}

//	public boolean verifyOption(String xpath, ArrayList<String> listElements) {
//		boolean result = true;
//		try {
//			waitForAjax();
//			addLog("click filter : " + xpath);
//			ArrayList<String> arrayList = getOptionList(xpath);
//			// compare two array list
//			Boolean match = listElements.containsAll(arrayList);
//			if (!match) {
//				result = false;
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at verifyOption : " + xpath);
//			result = false;
//		}
//		return result;
//	}

//	public boolean clickOption(String xpath, String name) {
//		boolean result = true;
//		addLog("Find & click dropdow menu : " + xpath + " option : "
//				+ name);
//		try {
//			waitForAjax();
//			WebElement select = driver.findElement(By.xpath(xpath));
//			List<WebElement> options = select
//					.findElements(By.tagName("option"));
//			for (WebElement option : options) {
//				if (name.equals(option.getText())) {
//					addLog("Click " + name);
//					option.click();
//					waitForAjax();
//				}
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at clickOption : " + xpath);
//			result = false;
//		}
//		return result;
//	}

//	public boolean checkAlertMsg(String createnewaccessoryXpath,
//			String globalAlertXpath, String addAccessoriesError) {
//		boolean result = true;
//		try {
//			waitForAjax();
//			driver.findElement(By.xpath(createnewaccessoryXpath)).click();
//			waitForAjax();
//			if (driver.findElement(By.xpath(globalAlertXpath)).getText()
//					.equals(addAccessoriesError)) {
//				addLog("Message error : " + addAccessoriesError);
//			} else {
//				addLog("Message error : "
//						+ driver.findElement(By.xpath(globalAlertXpath))
//								.getText());
//				result = false;
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at checkAlertMsg : "
//					+ createnewaccessoryXpath);
//			result = false;
//		}
//		return result;
//	}

	// click next, check page index++
//	public boolean checkNext(String xpath, String next) {
//		boolean result = true;
//		int sizePage;
//		int currentPageTemp;
//		try {
//			waitForAjax();
//			sizePage = getSizePage(xpath);
//			currentPageTemp = getCurrentPage(xpath);
//			addLog("Click next : " + next);
//			driver.findElement(By.xpath(next)).click();
//			waitForAjax();
//			if (currentPageTemp != sizePage
//					&& getCurrentPage(xpath) != currentPageTemp + 1) {
//				result = false;
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at checkNext : " + xpath);
//			result = false;
//		}
//		return result;
//	}

	// click previous, check page index--
//	public boolean checkPrevious(String xpath, String previous) {
//		boolean result = true;
//		int currentPageTemp;
//		try {
//			waitForAjax();
//			currentPageTemp = getCurrentPage(xpath);
//			addLog("Click Previous : " + previous);
//			driver.findElement(By.xpath(previous)).click();
//			waitForAjax();
//			if (currentPageTemp != 1
//					&& getCurrentPage(xpath) != currentPageTemp - 1) {
//				result = false;
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at checkPrevious : " + xpath);
//			result = false;
//		}
//		return result;
//	}

	// click last, check page is last index
//	public boolean checkLast(String xpath, String last) {
//		boolean result = true;
//		int sizePage;
//		try {
//			waitForAjax();
//			sizePage = getSizePage(xpath);
//			addLog("Click Last : " + last);
//			driver.findElement(By.xpath(last)).click();
//			waitForAjax();
//			if (getCurrentPage(xpath) < sizePage) {
//				result = false;
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at checkLast : " + xpath);
//			result = false;
//		}
//		return result;
//	}

	// click first, check page is first index (1)
//	public boolean checkFirst(String xpath, String first) {
//		boolean result = true;
//		try {
//			waitForAjax();
//			addLog("Click First : " + first);
//			driver.findElement(By.xpath(first)).click();
//			waitForAjax();
//			if (getCurrentPage(xpath) != 1) {
//				result = false;
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at checkFirst : " + xpath);
//			result = false;
//		}
//		return result;
//	}

//	private int getCurrentPage(String xpath) {
//		try {
//			waitForAjax();
//			WebElement page = driver.findElement(By.xpath(xpath));
//			WebElement currentPage = page.findElement(By
//					.className("paginate_active"));
//			return Integer.parseInt(currentPage.getText());
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at getCurrentPage : " + xpath);
//		}
//		return 0;
//	}

//	private int getSizePage(String xpath) {
//		try {
//			waitForAjax();
//			WebElement page = driver.findElement(By.xpath(xpath));
//			List<WebElement> pages = page.findElements(By.tagName("a"));
//			return pages.size();
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at getCurrentPage : " + xpath);
//		}
//		return 0;
//	}
//
//	public ArrayList<String> getListDataRow(String rowXpath) {
//		ArrayList<String> list = new ArrayList<String>();
//		try {
//			waitForAjax();
//			WebElement row = driver.findElement(By.xpath(rowXpath));
//			List<WebElement> elements = row.findElements(By.tagName("td"));
//			for (WebElement element : elements) {
//				addLog(element.getText());
//				list.add(element.getText());
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at getListDataRow : "
//					+ rowXpath);
//		}
//		return list;
//	}

//	public boolean verifyListDataWithListXpath(ArrayList<String> listData,
//			ArrayList<String> listXpath) {
//		boolean result = true;
//		waitForAjax();
//		String text = new String();
//		// check 0 : if variants is 0 -> ""
//		if (listData.get(2).equals("0")) {
//			System.out.println("check 0");
//			listData.set(2, "");
//		}
//		for (int i = 1; i < listData.size(); i++) {
//			text = "";
//			try {
//				if (driver.findElements(By.xpath(listXpath.get(i - 1))).size() < 1) {
//					break;
//				}
//				text = driver.findElement(By.xpath(listXpath.get(i - 1)))
//						.getText();
//				if (listData.get(i).equalsIgnoreCase(text)
//						|| text.equalsIgnoreCase("CLOSED")
//						|| text.contains(listData.get(i))
//						|| text.equalsIgnoreCase("Pending DTS Review")
//						|| text.equalsIgnoreCase("UNSUBMITTED")
//						|| text.equalsIgnoreCase("")) {
//					addLog("Data correct " + text + ":"
//							+ listData.get(i));
//				} else {
//					result = false;
//					addLog("Data incorrect " + text + ":"
//							+ listData.get(i));
//				}
//			} catch (NoSuchElementException e) {
//				addLog("NoSuchElementException :  "
//						+ listXpath.get(i - 1));
//				result = false;
//			}
//		}
//		return result;
//	}

//	public boolean selectItemAt(String modelVariants, int index, String waitItem) {
//		boolean result = true;
//		try {
//			waitForAjax();
//			WebElement variant = driver.findElement(By.xpath(modelVariants));
//			List<WebElement> listVariants = variant.findElements(By
//					.tagName("a"));
//			if (listVariants.size() == 0) {
//				addLog("No variant!!!");
//				result = false;
//			} else if (index > listVariants.size()) {
//				addLog("Out of range");
//				result = false;
//			} else {
//				addLog("select at index : " + index);
//				//setExpected(listVariants.get(index).getText());
//				listVariants.get(index).click();
//				// wait element display
//				waitForAjax();
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at selectItemAt :  "
//					+ modelVariants);
//			result = false;
//		}
//		return result;
//	}

//	public boolean clickAndChooseOption(String xpath, String option) {
//		// click
//		boolean result = true;
//		try {
//			waitForAjax();
//			addLog("click : " + xpath);
//			driver.findElement(By.xpath(xpath)).click();
//			waitForAjax();
//			Set<String> availableWindows = driver.getWindowHandles();
//			if (!availableWindows.isEmpty()) {
//				for (String windowId : availableWindows) {
//					WebDriver popup = driver.switchTo().window(windowId);
//					popup.findElement(By.linkText(option)).click();
//					waitForAjax();
//				}
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at selectItemAt :  " + xpath);
//			result = false;
//		}
//		return result;
//	}

//	public boolean editVariantSave(String editVariant, String fieldXpath,
//			String data, String editXpath) {
//		boolean result = true;
//		try {
//			waitForAjax();
//			// GET DATA
//			addLog("click : " + editVariant);
//			result = clickAndWaitElementDisplayed(editVariant, editXpath);
//			editData(editXpath, data);
//			driver.findElement(By.linkText("Save")).click();
//			waitForAjax();
//			if (driver.findElement(By.xpath(fieldXpath)).getText().equals(data)) {
//				addLog("Data have been changed successful : " + data);
//			} else {
//				addLog("can't change data : " + data);
//				result = false;
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at editVariantSave :  "
//					+ fieldXpath);
//			result = false;
//		}
//		return result;
//	}

//	public boolean editVariantCancel(String editVariant, String fieldXpath,
//			String data, String editXpath) {
//		boolean result = true;
//		try {
//			waitForAjax();
//			// GET DATA
//			String temp = driver.findElement(By.xpath(fieldXpath)).getText();
//			addLog("click : " + editVariant);
//			result = clickAndWaitElementDisplayed(editVariant, editXpath);
//			editData(editXpath, data);
//			driver.findElement(By.linkText("Cancel")).click();
//			waitForAjax();
//			if (driver.findElement(By.xpath(fieldXpath)).getText().equals(temp)) {
//				addLog("Data doesn't changed : " + data);
//			} else {
//				addLog("Data changed when cancel : " + data);
//				result = false;
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at editVariantCancel :  "
//					+ fieldXpath);
//			result = false;
//		}
//		return result;
//	}

//	public void editData(String editXpath, String data) {
//		if (data != null) {
//			try {
//				waitForAjax();
////				Thread.sleep(2000);
//				WebElement elem= driver.findElement(By.xpath(editXpath));
//				elem.clear();
//				addLog("change data : " + data);
//				elem.sendKeys(data);
//				waitForAjax();
//				// Work around due to issue PDPP-836
//				try {
//					if (driver.findElement(By.xpath(AddUser.BRAND_PRIVILEGES)).isDisplayed()) {
//						if (driver.findElement(By.xpath(AddUser.BRAND_PRIVILEGES))
//								.findElement(By.tagName("button")).getText()
//								.equals("None selected")) {
//							selectBrandPrivilege(AddUser.BRAND_PRIVILEGES,"All brands");
//						}
//					}
//				} catch (NoSuchElementException e) {
//				}
//				// End work around
//			} catch (NoSuchElementException e) {
//				addLog("NoSuchElementException at editData :  "
//						+ editXpath);
//			} 
////			catch (InterruptedException e) {
////				addLog("interupt exception");
////			}
//		}
//	}

//	public void editData(WebElement element, String data) {
//		try {
//			waitForAjax();
//			element.clear();
//			addLog("change data : " + data);
//			element.sendKeys(data);
//			waitForAjax();
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at editData :  " + element);
//		}
//	}

//	public ArrayList<String> getAccessoriesDetail(
//			ArrayList<String> listAccessoriesOnDetailPage) {
//		ArrayList<String> listAccessoriesDetail = new ArrayList<String>();
//		String currentItem = listAccessoriesOnDetailPage.get(0);
//		try {
//			waitForAjax();
//			for (String item : listAccessoriesOnDetailPage) {
//				currentItem = item;
//				addLog(driver.findElement(By.xpath(item)).getText());
//				listAccessoriesDetail.add(driver.findElement(By.xpath(item))
//						.getText());
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException : " + currentItem);
//		}
//		return listAccessoriesDetail;
//	}

//	public Boolean arrayListContainsAllIgnoreCase(
//			ArrayList<String> listAccessoriesDetail, ArrayList<String> listData) {
//		for (int i = 0; i < listAccessoriesDetail.size(); i++) {
//			if (listAccessoriesDetail.get(i).toLowerCase().contains("closed")) {
//				addLog("Case CLOSE : " + listAccessoriesDetail.get(i));
//				break;
//			} else if (listAccessoriesDetail.get(i).equals("")) {
//				addLog("Case empty : " + listAccessoriesDetail.get(i));
//				break;
//			} else if (!listAccessoriesDetail.get(i).toLowerCase()
//					.contains(listData.get(i).toLowerCase())) {
//				addLog("Case fail : "
//						+ listAccessoriesDetail.get(i).toLowerCase() + ":"
//						+ listData.get(i).toLowerCase());
//				return false;
//			}
//		}
//		return true;
//	}

//	// Get text by Xpath
//	public String getTextByXpath(String xpath) {
//		try {
//			waitForAjax();
//			String text = driver.findElement(By.xpath(xpath)).getText();
//			addLog("Text : " + text);
//			
//			return text;
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at getTextByXpath : " + xpath);
//		}
//		return "";
//	}
	

//	public boolean editAccessoriesAndSave(String xpath, String data,
//			String resultXpath) {
//		boolean result = true;
//		// get data before edit
//		try {
//			waitForAjax();
//			editData(xpath, data);
//			addLog("Click Save");
//			driver.findElement(By.linkText("Save")).click();
//			waitForAjax();
//			if (driver.findElement(By.xpath(resultXpath)).getText()
//					.equals(data)) {
//				addLog("Data changed : " + data);
//			} else {
//				addLog("can't change data : " + data);
//				result = false;
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at editAccessoriesAndSave : "
//					+ xpath);
//			result = false;
//		}
//		return result;
//	}

//	public boolean editAccessoriesAndCancel(String xpath, String xpathResult,
//			String url) {
//		boolean result = true;
//		try {
//			waitForAjax();
//			String data = RandomStringUtils.randomAlphabetic(20);
//			editData(xpath, data);
//			addLog("Click Cancel");
//			driver.findElement(By.linkText("Cancel")).click();
//			waitForAjax();
//			if (driver.getCurrentUrl().equals(url)
//					&& !driver.findElement(By.xpath(xpathResult)).getText()
//							.equals(data)) {
//				addLog("Data not changed!");
//				return result;
//			} else {
//				result = false;
//			}
//		} catch (NoSuchElementException e) {
//			System.err.println("NoSuchElementException : " + xpathResult);
//			System.err.println("Data changed or back to incorrect page!");
//			result = false;
//		}
//		return result;
//	}

//	public boolean clickAndCheckElements(String xpath, ArrayList<String> allElements) {
//		boolean isPass = true;
//		try {
//			waitForAjax();
//			driver.findElement(By.xpath(xpath)).click();
//			// wait loading
//			waitForAjax();
//			isPass = existsElement(allElements);
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at clickAndCheckElements : " + xpath);			
//			isPass = false;
//		}
//		return isPass;
//	}

//	public boolean selectAndCheckMessage(String save, String errorMsg) {
//		boolean result = true;
//		try {
//			waitForAjax();
//			driver.findElement(By.xpath(save)).click();
//			waitForAjax();
//			if (driver.getPageSource().contains(errorMsg)) {
//				addLog("Error message displayed!");
//				return result;
//			} else {
//				result = false;
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at clickAndCheckElements : "
//					+ save);
//			result = false;
//		}
//		return result;
//	}

//	public boolean checkValue(String xpath, String value) {
//		boolean result = true;
//		try {
//			waitForAjax();
//			if (driver.findElement(By.xpath(xpath)).getText().contains(value)) {
//				addLog("Value correct : " + value);
//			} else {
//				addLog("Value incorrect : " + value);
//				result = false;
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException : " + xpath);
//			result = false;
//		}
//		return result;
//	}

//	public int getNumOfImageMarketingMaterials(String marketingMaterials) {
//		try {
//			waitForAjax();
//			WebElement img = driver.findElement(By.xpath(marketingMaterials));
//			return img.findElements(By.tagName("a")).size() / 2;
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at getNumOfImageMarketingMaterials : "
//					+ marketingMaterials);
//		}
//		return 0;
//	}
//
//	public boolean clickDeleteImgMarketingMaterials(String marketingMaterials) {
//		boolean result = true;
//		int currentSize = getNumOfImageMarketingMaterials(marketingMaterials);
//		try {
//			waitForAjax();
//			WebElement img = driver.findElement(By.xpath(marketingMaterials));
//			List<WebElement> elements = img.findElements(By.tagName("a"));
//			for (WebElement webElement : elements) {
//				if (webElement.getText().equals("Delete")) {
//					webElement.click();
//					waitForAjax();
//					break;
//				}
//			}
//			Set<String> availableWindows = driver.getWindowHandles();
//			if (!availableWindows.isEmpty()) {
//				for (String windowId : availableWindows) {
//					WebDriver popup = driver.switchTo().window(windowId);
//					addLog("Click Delete");
//					popup.findElement(By.linkText("Delete")).click();
//					waitForAjax();
//				}
//			}
//			if (currentSize == getNumOfImageMarketingMaterials(marketingMaterials) - 1) {
//				addLog("Deleted");
//				return result;
//			}
//		} catch (NoSuchElementException e) {
//			result = false;
//			addLog("NoSuchElementException : " + marketingMaterials);
//		}
//		return result;
//	}

//	public boolean clickAnAccessoryHaveImage(String body, String imageFile,
//			String paginate) {
//		boolean result = true;
//		// find accessory have image and click
//		Boolean flag = false;
//		try {
//			// body display
//			waitForAjax();
//			// click next
//			driver.findElement(By.linkText("Last")).click();
//			// sleep for load data
//			waitForAjax();
//			// get list all row
//			WebElement table = driver.findElement(By.xpath(body));
//			List<WebElement> rows = table.findElements(By.tagName("tr"));
//			for (WebElement row : rows) {
//				WebElement img = row.findElement(By.tagName("img"));
//				if (img.getAttribute("src").length() > 0) {
//					System.out.println("src : " + img.getAttribute("src"));
//					WebElement link = row.findElement(By.tagName("a"));
//					addLog("Click accessory : " + link.getText());
//					link.click();
//					waitForAjax();
//					flag = true;
//					break;
//				} else {
//					System.err.println("src = null");
//				}
//			}
//		} catch (NoSuchElementException e) {
//			result = false;
//			addLog("NoSuchElementException at clickAnAccessoryHaveImage: "
//					+ body);
//		}
///*		if (!flag) {
//			setExpected("No Image");
//		}
//*/		return result;
//	}

//	private int getSizePaginate(String paginate) {
//		try {
//			waitForAjax();
//			System.out.println("on : getSizePaginate");
//			WebElement page = driver.findElement(By.xpath(paginate));
//			WebElement table = page.findElement(By.tagName("span"));
//			List<WebElement> pages = table.findElements(By.tagName("a"));
//			return pages.size();
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at getSizePaginate : "
//					+ paginate);
//		}
//		return 0;
//	}

//	public boolean deleteImageFileAndCheck(String imageFile) {
//		boolean result = true;
//		System.out.println("On : deleteImageFileAndCheck");
//		try {
//			waitForAjax();
//			WebElement imageRow = driver.findElement(By.xpath(imageFile));
//			int totalImg = imageRow.findElements(By.tagName("img")).size();
//			if (totalImg > 0) {
//				WebElement img = imageRow.findElement(By.tagName("img"));
//				String src = img.getAttribute("src");
//				addLog("src : " + src);
//				// get link delete and click
//				WebElement delete = imageRow.findElement(By.tagName("a"));
//				if (delete.getText().equals("Delete")) {
//					delete.click();
//					// wait pop up
//					waitForAjax();
//					switchWindowClickOption("Delete");
//					// wait delete
//					waitForAjax();
//					// check img deleted
//					// find element
//					imageRow = driver.findElement(By.xpath(imageFile));
//					int remainImg = imageRow.findElements(By.tagName("img"))
//							.size();
//					if (totalImg > remainImg) {
//						addLog("Image deleted");
//					} else {
//						addLog("Can't delete img : " + src);
//						result = false;
//					}
//				}
//			} else {
//				addLog("No image file for delete");
//			}
//
//		} catch (NoSuchElementException e) {
//			System.err.println("NoSuchElementException : " + imageFile);
//			result = false;
//		}
//		return result;
//	}

//	

//	public boolean findAndGoPageExistVariant(String tbodyXpath, String paginate) {
//		boolean result = true;
//		try {
//			waitForAjax();
//			// find row exist variant
//			// count pages
//			int sizePage = getSizePaginate(paginate);
//			// check variant page
//			for (int i = 0; i < sizePage; i++) {
//				if (checkExistVariant(tbodyXpath, 2)) {
//					addLog("On page exist Variant");
//					return result;
//				} else {
//					addLog("Next Page");
//					driver.findElement(By.linkText("Next")).click();
//					// wait for load
//					waitForAjax();
//				}
//			}
//			// doesn't existed page have variant
//			addLog("doesn't existed page have variant");
//			result = false;
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at findAndGoPageExistVariant : "
//					+ tbodyXpath);
//			result = false;
//		}
//		return result;
//	}

//	private boolean checkExistVariant(String tbodyXpath, int index) {
//		try {
//			waitForAjax();
//			// get tbody element
//			WebElement tbody = driver.findElement(By.xpath(tbodyXpath));
//			// get all rows
//			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
//			// get each column of row
//			for (WebElement row : rows) {
//				List<WebElement> columns = row.findElements(By.tagName("td"));
//				if (Integer.parseInt(columns.get(index).getText()) > 0) {
//					addLog("Variant existed");
//					return true;
//				}
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException on checkExistVariant");
//		}
//		return false;
//	}

//	public boolean findElementExistVariantAndClick(String brandAccessoriesTable,
//			String modelVariants) {
//		boolean result = true;
//		Boolean flag = false;
//		try {
//			waitForAjax();
//			WebElement linkElement = getRowExistVariant(brandAccessoriesTable);
//			if (linkElement != null) {
//				// click
//				linkElement.findElement(By.tagName("a")).click();
//				// wait
//				waitForAjax();
//				flag = true;
//			} else {
//				addLog("Doesn't existed variant");
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at findElementExistVariantAndClick : "
//					+ brandAccessoriesTable);
//			result = false;
//		}
///*		if (!flag) {
//			setExpected("No Variant");
//		}
//*/		return result;
//	}

//	private WebElement getRowExistVariant(String tbodyXpath) {
//		WebElement rowVariant = null;
//		try {
//			waitForAjax();
//			// get tbody element
//			// get tbody element
//			WebElement tbody = driver.findElement(By.xpath(tbodyXpath));
//			// get all rows
//			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
//			// get each column of row
//			for (WebElement row : rows) {
//				List<WebElement> columns = row.findElements(By.tagName("td"));
//				if (Integer.parseInt(columns.get(2).getText()) > 0) {
//					addLog("Variant existed, name "
//							+ columns.get(1).getText());
//					rowVariant = columns.get(1);
//					break;
//				}
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException on checkExistVariant "
//					+ tbodyXpath);
//		}
//		return rowVariant;
//	}

//	public boolean clickLinkAndWaitElementDisplayed(String modelVariants, String editVariant) {
//		boolean result = true;
//		addLog("on : clickLinkAndWaitElementDisplayed");
//		Boolean flag = false;
//		try {
//			waitForAjax();
//			WebElement model = driver.findElement(By.xpath(modelVariants));
//			List<WebElement> variants = model.findElements(By.tagName("a"));
//			if (variants.size() > 0) {
//				addLog("Click variant");
//				variants.get(0).click();
//				waitForAjax();
//				flag = true;
//			} else {
//				addLog("No variant existed!");
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException " + modelVariants);
//			result = false;
//		}
//		return result;
//	}
	

//	public boolean deleteMaketingMaterialFile(String marketingMaterials) {
//		boolean result = true;
//		addLog("On : deleteMaketingMaterialFile");
//		try {
//			waitForAjax();
//			WebElement maketingImgWebElement = driver.findElement(By
//					.xpath(marketingMaterials));
//			List<WebElement> hrefs = maketingImgWebElement.findElements(By
//					.tagName("a"));
//			int currentSize = hrefs.size();
//			if (hrefs.size() > 1) {
//				if (hrefs.get(1).getText().equals("Delete")) {
//					hrefs.get(1).click();
//					waitForAjax();
//					// wait popup
//					switchWindowClickOption("Delete");
//					// sleep for reload
//					waitForAjax();
//					// check img delete
//					if (driver.findElements(By.xpath(marketingMaterials))
//							.size() > 0) {
//						maketingImgWebElement = driver.findElement(By
//								.xpath(marketingMaterials));
//						hrefs = maketingImgWebElement.findElements(By
//								.tagName("a"));
//						if (hrefs.size() < currentSize) {
//							addLog("Delete sucessful");
//						} else {
//							addLog("Can't delete");
//							result = false;
//						}
//					}
//				} else {
//					addLog("No image");
//				}
//			} else {
//				addLog("No image for delete");
//			}
//		} catch (NoSuchElementException e) {
//			System.err.println("NoSuchElementException " + marketingMaterials);
//			result = false;
//
//		}
//		return result;
//	}

//	public boolean clickAndCheckElements(String xpath, String[] option) {
//		boolean result = true;
//		try {
//			waitForAjax();
//			driver.findElement(By.xpath(xpath)).click();
//			// wait loading
//			waitForAjax();
//			result = elementExist(option);
//		} catch (NoSuchElementException e) {
//			System.err
//					.println("NoSuchElementException at  clickAndCheckElements : "
//							+ xpath);
//			result = false;
//		}
//		return result;
//	}

//	private boolean elementExist(String[] elements) {
//		boolean result = true;
//		addLog("Check all element by array");
//		waitForAjax();
//		for (String element : elements) {
//			Boolean isPresent = driver.findElements(By.xpath(element)).size() > 0;
//			if (!isPresent) {
//				addLog("Element " + element + " doesn't existed!");
//				result = false;
//			} else {
//				addLog("Element "
//						+ driver.findElement(By.xpath(element)).getText()
//						+ " displayed!");
//			}
//		}
//		return result;
//	}

//	public boolean clickOptionAndCheckFilterSelected(String xpath, String name) {
//		boolean result = true;
//		waitForAjax();
//		// Select Report Filter and choose "name" item and waitting for page
//		// load
//		result = clickOption(xpath, name);
//		// chech Filter have default value "name"
//		if (result) {
//			result = isCorrectItemSelected(xpath, name);
//			return result;
//		} else {
//			addLog("Can't click option");
//			return result;
//		}
//	}

//	public boolean checkTimeAdded(String tableBody, int index, int days) {
//		boolean result = true;
//		waitForAjax();
//		// get total page
//		int totalPage = totalPage();
//		// get data column index
//		ArrayList<String> listTimes = new ArrayList<String>();
//		for (int i = 0; i < totalPage; i++) {
//			listTimes = getListTextAt(tableBody, index);
//			// check time within "days (14)" rolling days
//			for (String time : listTimes) {
//				if (!DateUtil.isBetween(time, days)) {
//					addLog("Time doesn't within 14 rolling day");
//					result = false;
//					return result;
//				} else {
//					addLog("Time OK!");
//				}
//			}
//			// click next page
//			try {
//				driver.findElement(By.linkText("Next")).click();
//				waitForAjax();
//			} catch (NoSuchElementException e) {
//				addLog("NoSuchElementException : Next");
//				result = false;
//			}
//		}
//		return result;
//	}
	
//	public ArrayList<String> getAllPartner(String menuViewAs) {
//		boolean result = true;
//		ArrayList<String> partners = new ArrayList<String>();
//		try {
//			waitForAjax();
//			addLog("-Click dropdown menu view as for choose partner");
//			driver.findElement(By.xpath(menuViewAs)).click();
//			WebElement ul = driver.findElement(By.xpath(xpathListItemPartner));
//			List<WebElement> options = ul.findElements(By.tagName("li"));
//			for (WebElement option : options) {
//				addLog(option.getText());
//				partners.add(option.getText());
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at getAllPartner : "
//					+ menuViewAs);
//			result = false;
//		}
//		return partners;
//	}

//	public String getStringByXpath(String xpath) {
//		String text = "";
//		try {
//			waitForAjax();
//			text = driver.findElement(By.xpath(xpath)).getText();
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException : " + xpath);
//		}
//		return text;
//	}

//	public Hashtable<String, String> getHashDataCompanyAt(String tableBody,
//			int index) {
//		Hashtable<String, String> data = new Hashtable<String, String>();
//		try {
//			// wait for data load
//			waitForAjax();
//			// get table
//			WebElement table = driver.findElement(By.xpath(tableBody));
//			// get all row
//			List<WebElement> rows = table.findElements(By.tagName("tr"));
//			// get row at index
//			if (index > rows.size()) {
//				addLog("Out of range");
//				return null;
//			} else {
//				WebElement rowAt = rows.get(index);
//				List<WebElement> columns = rowAt.findElements(By.tagName("td"));
//				addLog("size : " + columns.size());
//				// set data to hash
//				data.put("Company", columns.get(0).getText());
//				data.put("Status", columns.get(1).getText());
//				data.put("Partner Type", columns.get(2).getText());
//				data.put("All Models", columns.get(3).getText());
//				data.put("Published Models", columns.get(4).getText());
//				data.put("Variants", columns.get(5).getText());
//			}
//		} catch (NoSuchElementException e) {
//			System.err.println("NoSuchElementException : getHashDataCompanyAt");
//		}
//		return data;
//	}

//	public boolean clickDataAt(String tableBody, int index, String edit) {
//		boolean result = true;
//		// get table
//		WebElement table = driver.findElement(By.xpath(tableBody));
//		// get all row
//		List<WebElement> rows = table.findElements(By.tagName("tr"));
//		// get row at index
//		if (index > rows.size()) {
//			addLog("Out of range");
//			result = false;
//			return result;
//		} else {
//			WebElement rowAt = rows.get(index);
//			List<WebElement> columns = rowAt.findElements(By.tagName("td"));
//			// set data to hash
//			addLog("Click : " + columns.get(0).getText());
//			columns.get(0).findElement(By.tagName("a")).click();
//			waitForAjax();
//		}
//		return result;
//	}

//	public Boolean checkMatchData(ArrayList<String> listInfo,
//			Hashtable<String, String> hash) {
//		try {
//			waitForAjax();
//			String name = driver.findElement(By.xpath(listInfo.get(0)))
//					.getText();
//			addLog("name : " + name);
//			String status = driver.findElement(By.xpath(listInfo.get(1)))
//					.getText();
//			addLog("status : " + status);
//			String partnerType = driver.findElement(By.xpath(listInfo.get(2)))
//					.getText();
//			addLog("partner Type : " + partnerType);
//			if (!name.equals(hash.get("Company"))) {
//				addLog("Name doesn't match : " + name + " : "
//						+ hash.get("Company"));
//				return false;
//			} else if (!status.equals(hash.get("Status"))) {
//				addLog("Status doesn't match : " + status + " : "
//						+ hash.get("Status"));
//				return false;
//			} else if (!partnerType.equals(hash.get("Partner Type"))) {
//				addLog("Partner Type doesn't match : " + partnerType
//						+ " : " + hash.get("Partner Type"));
//				return false;
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException");
//			return false;
//		}
//		return true;
//	}

//	public ArrayList<UserInfo> getUsers(String tbody) {
//		ArrayList<UserInfo> users = new ArrayList<UserInfo>();
//		try {
//			waitForAjax();
//			waitForLoadElement(tbody);
//			WebElement body = driver.findElement(By.xpath(tbody));
//			// get all rows
//			List<WebElement> rows = body.findElements(By.tagName("tr"));
//			// get data
//			for (WebElement row : rows) {
//				// get all colums
//				List<WebElement> colums = row.findElements(By.tagName("td"));
//				UserInfo info = new UserInfo();
//				if (colums.size() == 6) {
//					info.setFirstName(colums.get(0).getText());
//					info.setLastName(colums.get(1).getText());
//					info.setCompany(colums.get(2).getText());
//					info.setTitle(colums.get(3).getText());
//					info.setPhone(colums.get(4).getText());
//					info.setEmail(colums.get(5).getText());
//					// add to array list
//					users.add(info);
//				}
//
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException on getUsers");
//		}
//		return users;
//	}

//	public String getTableCellValue(String xpath, int row, int column) {
//		try {
//			WebElement tbody = driver.findElement(By.xpath(xpath)).findElement(
//					By.tagName("tbody"));
//			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
//			List<WebElement> columns = rows.get(row).findElements(
//					By.tagName("td"));
//			String cell_value = columns.get(column).getText();
//			addLog("Value at row: " + row + " and colume: " + column
//					+ " is: " + cell_value);
//			return cell_value;
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException: " + xpath);
//			return "";
//		}
//	}

//	public boolean verifyData(Hashtable<String, String> hash,
//			Hashtable<String, String> info) {
//		boolean result = true;
//		try {
//			waitForAjax();
//			Set<String> keys = hash.keySet();
//			for (String key : keys) {
//				System.out.println(key);
//				String data = driver.findElement(By.xpath(hash.get(key)))
//						.getText();
//				if (data.contains(info.get(key))) {
//					addLog("data correct ".concat(data).concat(" : ")
//							.concat(info.get(key)));
//				} else {
//					addLog("data incorrect ".concat(data).concat(" : ")
//							.concat(info.get(key)));
//					result = false;
//				}
//			}
//		} catch (NoSuchElementException e) {
//			addLog(e.toString());
//			result = false;
//		}
//		return result;
//	}

//	public ArrayList<DataPartnerAccessoriesTable> getListObject(String tbody) {
//		ArrayList<DataPartnerAccessoriesTable> users = new ArrayList<DataPartnerAccessoriesTable>();
//		try {
//			waitForAjax();
//			waitForLoadElement(tbody);
//			WebElement body = driver.findElement(By.xpath(tbody));
//			// get all rows
//			List<WebElement> rows = body.findElements(By.tagName("tr"));
//			// get data
//			for (WebElement row : rows) {
//				// get all colums
//				List<WebElement> colums = row.findElements(By.tagName("td"));
//				DataPartnerAccessoriesTable info = new DataPartnerAccessoriesTable();
//				if (colums.size() == 8) {
//					info.setPicture(colums.get(0).getText());
//					info.setModel(colums.get(1).getText());
//					info.setVariants(colums.get(2).getText());
//					info.setPublished(colums.get(3).getText());
//					info.setVersion(colums.get(4).getText());
//					info.setTuning(colums.get(5).getText());
//					info.setHeadphone(colums.get(6).getText());
//					info.setMarketing(colums.get(7).getText());
//					// add to array list
//					users.add(info);
//				}
//			}
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException on getUsers");
//		}
//		return users;
//	}

//	public boolean checkUesrInfo(UserInfo userInfo,
//			Hashtable<String, String> elementsInfo) {
//		boolean result = true;
//		try {
//			waitForAjax();
//			String name = driver
//					.findElement(By.xpath(elementsInfo.get("name"))).getText();
//			String company = driver.findElement(
//					By.xpath(elementsInfo.get("company"))).getText();
//			String title = driver.findElement(
//					By.xpath(elementsInfo.get("title"))).getText();
//			String phone = driver.findElement(
//					By.xpath(elementsInfo.get("phone"))).getText();
//			String email = driver.findElement(
//					By.xpath(elementsInfo.get("email"))).getText();
//			// check name
//			if (name.contains(userInfo.getFirstName())
//					&& name.contains(userInfo.getLastName())) {
//				addLog("name correct : " + name);
//			} else {
//				addLog("name inccorect : " + name + " : "
//						+ userInfo.getFirstName() + " "
//						+ userInfo.getLastName());
//				result = false;
//			}
//			// check company
//			if (company.contains(userInfo.getCompany())) {
//				addLog("company correct : " + company);
//			} else {
//				addLog("company inccorect : " + company + " : "
//						+ userInfo.getCompany());
//				result = false;
//			}
//			// check title
//			if (title.contains(userInfo.getTitle())) {
//				addLog("title correct : " + title);
//			} else {
//				addLog("title inccorect : " + title + " : "
//						+ userInfo.getTitle());
//				result = false;
//			}
//			// check phone
//			if (phone.contains(userInfo.getPhone())) {
//				addLog("phone correct : " + phone);
//			} else {
//				addLog("phone inccorect : " + phone + " : "
//						+ userInfo.getPhone());
//				result = false;
//			}
//			// check email
//			if (email.contains(userInfo.getEmail())) {
//				addLog("email correct : " + email);
//			} else {
//				addLog("email inccorect : " + email + " : "
//						+ userInfo.getEmail());
//				result = false;
//			}
//		} catch (NoSuchElementException e) {
//			result = false;
//			addLog("NoSuchElementException");
//		}
//		return result;
//	}

//	public Boolean fillAddAccessoriesPartner(
//			Hashtable<String, String> hashXpath, Hashtable<String, String> data) {
//		try {
//			waitForAjax();
//			selectFirstOption(hashXpath.get("brand"));
//			selectInputChannel(hashXpath.get("wired"), data.get("wired"));
//			selectInputChannel(hashXpath.get("bluetooth"),
//					data.get("bluetooth"));
//			editData(hashXpath.get("name"), data.get("name"));
//			editData(hashXpath.get("number"), data.get("number"));
//			editData(hashXpath.get("upc"), data.get("upc"));
//			selectOptionByName(hashXpath.get("type"), data.get("type"));
//			editData(hashXpath.get("description"), data.get("description"));
//
//			return true;
//		} catch (NoSuchElementException e) {
//			addLog(e.toString());
//		}
//		return false;
//	}

//	public boolean partnerSelectUserByEmail(String email) {
//		boolean result = true;
//		Boolean flag = true;
//		int limit = getPageSize(PageHome.ADMIN_USER_LIST_TABLE_INFO);
//		try {
//			while (flag) {
//				if (driver.getPageSource().contains(email)) {
//					flag = false;
//					// find and click user by email
//					// step 1 : find element tbody
//					WebElement tbody = driver.findElement(By.tagName("tbody"));
//					// step 2 : find all row
//					List<WebElement> rows = tbody
//							.findElements(By.tagName("tr"));
//					// step 3 : for all row get element contain email and click
//					for (WebElement row : rows) {
//						// get all colums
//						List<WebElement> colums = row.findElements(By
//								.tagName("td"));
//						if (colums.size() == 5) {
//							if (colums.get(4).getText().contains(email)) {
//								// found element
//								// click element
//								WebElement link = colums.get(0).findElement(
//										By.tagName("a"));
//								link.click();
//								waitForAjax();
//								return result;
//							}
//						}
//
//					}
//				}
//				limit--;
//				if (limit < 0) {
//					flag = false;
//				}
//				driver.findElement(By.linkText("Next")).click();
//				waitForAjax();
//			}
//		} catch (NoSuchElementException e) {
//			System.err.println("No element exception : " + email);
//			result = false;
//		}
//		return result;
//	}

//	public Boolean existsElement(String name) {
//		return isElementPresent(name);
//	}

//	public Boolean selectAnAccessoryDTS() {
//		try {
//			// get table
//			WebElement tbody = driver.findElement(By.tagName("tbody"));
//			// get all rows
//			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
//			for (WebElement row : rows) {
//				WebElement link = row.findElement(By.tagName("a"));
//				link.click();
//				waitForAjax();
//				break;
//			}
//		} catch (NoSuchElementException e) {
//			System.err.println("No element exception");
//			return false;
//		}
//		return true;
//	}

//	public void clickText(String text) {
//		try {
//			waitForAjax();
//			addLog("Click : " + text);
//			Thread.sleep(2000);
//			driver.findElement(By.linkText(text)).click();
//			Thread.sleep(1000);
//			waitForAjax();
//		} catch (NoSuchElementException e) {
//			System.err.println("No element exception : " + text);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}

//	public ArrayList<String> getStandardAccessory() {
//		ArrayList<String> data = new ArrayList<String>();
//		try {
//			WebElement tbody = driver.findElement(By.tagName("tbody"));
//			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
//			for (WebElement row : rows) {
//				List<WebElement> columns = row.findElements(By.tagName("td"));
//				String text = columns.get(1).getText();
//				data.add(text);
//			}
//		} catch (NoSuchElementException e) {
//			System.err.println("No element exception : getStandardAccessory ");
//		}
//		return data;
//	}
	
//	public String getHintText(String xpath) {
//	try {
//		waitForAjax();
//		String text = driver.findElement(By.xpath(xpath)).getAttribute(
//				"placeholder");
//		addLog("Text : " + text);
//		return text;
//	} catch (NoSuchElementException e) {
//		addLog("NoSuchElementException at getTextByXpath : " + xpath);
//		return "";
//	}
//}
	

//	public String getInnerHTML(String xpath) {
//		try {
//			waitForAjax();
//			String text = driver.findElement(By.xpath(xpath)).getAttribute(
//					"innerHTML");
//			addLog("innerHTML : " + text);
//			return text;
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException at getTextByXpath : " + xpath);
//			return "";
//		}
//	}
	

//	public String selectADeviceAt(int i) {
//		String device = "";
//		try {
//			WebElement tbody = driver.findElement(By.tagName("tbody"));
//			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
//			if (rows.size() < i) {
//				return "";
//			}
//			WebElement rowAt = rows.get(i);
//			List<WebElement> columns = rowAt.findElements(By.tagName("td"));
//			device = columns.get(2).getText();
//			System.out.println("Click : " + device);
//			columns.get(2).findElement(By.tagName("a")).click();
//			waitForAjax();
//		} catch (NoSuchElementException e) {
//		}
//		return device;
//	}

//	public Boolean checkDataEquals(String titleCompany, String company) {
//		try {
//			WebElement title = driver.findElement(By.xpath(titleCompany));
//			String titleText = title.getText();
//
//			WebElement data = driver.findElement(By.xpath(company));
//			String dataText = data.getText();
//			return titleText.equals(dataText);
//		} catch (NoSuchElementException e) {
//			return false;
//		}
//	}

//	public boolean fillDeviceEdit(HashMap<String, String> data) {
//		try {
//			editData(DeviceEdit.SALESFORCE_ID, data.get("id"));
//			selectOptionByName(DeviceEdit.TYPE, data.get("type"));
//			selectOptionByName(DeviceEdit.COMPANY, data.get("company"));
//			selectOptionByName(DeviceEdit.BRAND, data.get("brand"));
//			editData(DeviceEdit.NAME, data.get("name"));
//			selectOptionByName(DeviceEdit.OS_LIST, data.get("os"));
//			editData(DeviceEdit.INIT_KEY_NAME, data.get("keyName"));
//			// editData(DeviceEdit.KEY_VALUE_INPUT, data.get("keyValue"));
//		} catch (NoSuchElementException e) {
//			return false;
//		}
//		return true;
//	}

//	public boolean isClickable(String[] links) {
//		try {
//			for (String xpath : links) {
//				WebElement element = driver.findElement(By.xpath(xpath));
//				if (!element.isEnabled()) {
//					return false;
//				}
//			}
//		} catch (NoSuchElementException e) {
//			return false;
//		}
//		return true;
//	}

//	public Boolean clickEditDevices() {
//		try {
//			if (isTextPresent("Create New Version")) {
//				clickText("Create New Version");
//			} else if (isTextPresent("Edit Version")) {
//				clickText("Edit Version");
//			}
//		} catch (NoSuchElementException e) {
//			return false;
//		}
//		return true;
//	}

//	public String selectACompanyAt(int i) {
//		String company = "";
//		try {
//			WebElement tbody = driver.findElement(By.tagName("tbody"));
//			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
//			if (rows.size() < i) {
//				return "";
//			}
//			WebElement rowAt = rows.get(i);
//			List<WebElement> columns = rowAt.findElements(By.tagName("td"));
//			company = columns.get(0).getText();
//			System.out.println("Click : " + company);
//			columns.get(0).findElement(By.tagName("a")).click();
//			waitForAjax();
//		} catch (NoSuchElementException e) {
//			return "";
//		}
//		return company;
//	}


//	public TuningFile getTuningFile(String xpath) {
//		TuningFile tuningFile = new TuningFile();
//		try {
//			WebElement anotherRoute = driver.findElement(By.xpath(xpath));
//			WebElement tuning = anotherRoute.findElement(By.tagName("div"));
//			List<WebElement> links = tuning.findElements(By.tagName("a"));
//			if (links.size() == 2) {
//				tuningFile.name = links.get(0).getAttribute("download");
//				tuningFile.href = links.get(0).getAttribute("href");
//				tuningFile.link = links.get(0).getText();
//				tuningFile.btnDelete = links.get(1).getText();
//				tuningFile.download = links.get(0);
//				tuningFile.delete = links.get(1);
//			}
//		} catch (NoSuchElementException e) {
//		}
//		return tuningFile;
//	}

//	public String getGlobleAlert(String globalAlert) {
//		System.out.println("get Globle Alert");
//		String error = "";
//		try {
//			int timeWait = 20;
//			while (timeWait > 0) {
//				if (isElementPresent(globalAlert)) {
//					error = getTextByXpath(globalAlert);
//					System.out.println("Error : " + error);
//				}
//				Thread.sleep(500);
//				timeWait--;
//			}
//		} catch (NoSuchElementException e) {
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		return error;
//	}

//	public Boolean selectRowByText(String dtsUser) {
//		boolean result = true;
//		int size = getPageSize();
//		try {
//			for (int i = 0; i < size; i++) {
//				if (driver.getPageSource().contains(dtsUser)) {
//					// find and click user by email
//					// step 1 : find element tbody
//					WebElement tbody = driver.findElement(By.tagName("tbody"));
//					// step 2 : find all row
//					List<WebElement> rows = tbody
//							.findElements(By.tagName("tr"));
//					// step 3 : for all row get element contain email and click
//					for (WebElement row : rows) {
//						// get all colums
//						List<WebElement> colums = row.findElements(By
//								.tagName("td"));
//						if (colums.size() > 0) {
//							for (WebElement colum : colums) {
//								if (colum.getText().contains(dtsUser)) {
//									// found element
//									// click element
//									WebElement link = colums.get(0)
//											.findElement(By.tagName("a"));
//									link.click();
//									waitForAjax();
//									return true;
//								}
//							}
//						}
//					}
//					driver.findElement(By.linkText("Next")).click();
//					waitForAjax();
//				} else {
//					driver.findElement(By.linkText("Next")).click();
//				}
//			}
//		} catch (NoSuchElementException e) {
//			System.err.println("No element exception : " + dtsUser);
//			result = false;
//		}
//		return result;
//	}

//	public int getSizeTableList() {
//		try {
//			WebElement tbody = driver.findElement(By.tagName("tbody"));
//			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
//			return rows.size();
//		} catch (NoSuchElementException e) {
//			System.err.println("No element exception : getSizeTableList");
//		}
//		return 0;
//	}


//	public boolean isRadioChecked(String xpath, int index) {
//		try {
//			WebElement element = driver.findElement(By.xpath(xpath));
//			// get all radio buttons
//			List<WebElement> all_buttons = element.findElements(By
//					.tagName("label"));
//			for (int i = 0; i < all_buttons.size(); i++) {
//				Boolean status = all_buttons.get(index).isEnabled();
//				if (status.equals("checked")) {
//					return status;
//				}
//			}
//		} catch (NoSuchElementException e) {
//		}
//		return false;
//	}

//	public Boolean isShowDialog() {
//		String confirm[] = new String[2];
//		int i = 0;
//		try {
//			List<WebElement> links = driver.findElements(By.tagName("a"));
//			for (WebElement webElement : links) {
//				String href = webElement.getAttribute("href");
//				if (href != null && href.contains("javascript:;")) {
//					confirm[i] = webElement.getText();
//					System.out.println("Confirm : " + confirm[i]);
//					i++;
//				}
//			}
//		} catch (NoSuchElementException e) {
//			System.err.println("No Such elemetn exception : getConfirm");
//		}
//		return confirm[0].equals("Cancel");
//	}

//	public String getWarningMsg(WebElement element) {
//		String warning = getInnerHTML(element);
//		String attr = getAtribute(element, "style");
//		if (attr.contains("none")) {
//			return "";
//		}
//		return warning;
//	}

//	public boolean checkBrandimagexist(String Xpath, String imageName) {
//		try {
//			// Get all brands in the brand table
//			WebElement brandtable = driver.findElement(By.xpath(Xpath));
//			WebElement brandcontainer = brandtable.findElement(By
//					.tagName("tbody"));
//			// Get each rows of brand table
//			List<WebElement> each_brand = brandcontainer.findElements(By
//					.tagName("tr"));
//			for (int i = 0; i < each_brand.size(); i++) {
//				// Get each columns of each row
//				List<WebElement> image_column = each_brand.get(i).findElements(
//						By.tagName("td"));
//				// Get brand name
//				WebElement brand_image_tag = image_column.get(0).findElement(
//						By.tagName("a"));
//				String brand_image = brand_image_tag.getText();
//				if (brand_image.contains(imageName)) {
//					return true;
//				}
//			}
//		} catch (NoSuchElementException e) {
//			System.out.println("Failed to locate Element");
//		}
//		return false;
//	}

//	public Boolean deleteAudioTuningFile(String xpath) {
//		try {
//			WebElement tuning = driver.findElement(By.xpath(xpath));
//			List<WebElement> links = tuning.findElements(By.tagName("a"));
//			for (WebElement link : links) {
//				if (link.getText().equals("Delete")) {
//					doDelete(link);
//				}
//			}
//		} catch (NoSuchElementException e) {
//			System.err
//					.println("NoSuchElementException : deleteAudioTuningFile");
//		}
//		return false;
//	}

//	public void doDeleteXpartner(WebElement link) {
//		click(link);
//		switchWindowClickOption("Delete Partnership");
//	}

//	public AudioTuning getAudioTuning(String xpath) {
//		AudioTuning audioTuning = new AudioTuning();
//		try {
//			WebElement tuning = driver.findElement(By.xpath(xpath));
//			List<WebElement> links = tuning.findElements(By.tagName("a"));
//			if (links.size() > 0) {
//				audioTuning.name = links.get(0).getText();
//				audioTuning.link = links.get(0);
//				audioTuning.delete = links.get(1);
//			}
//		} catch (NoSuchElementException e) {
//			System.err.println("NoSuchElementException : getAudioTuning");
//			return null;
//		}
//		return audioTuning;
//	}

//	public void selectContactPersonAt(String Xpath, int index) {
//		try {
//			waitForAjax();
//			// Find the contact person package
//			WebElement webElement = driver.findElement(By.xpath(Xpath));
//			webElement = webElement.findElement(By.tagName("tbody"));
//			// Get all rows
//			List<WebElement> rows = webElement.findElements(By.tagName("tr"));
//			// Get info of the first column
//			WebElement first_column = rows.get(index).findElement(
//					By.tagName("td"));
//			String first_name = first_column.findElement(By.tagName("a"))
//					.getText();
//			System.out.println("Clicking : " + first_name);
//			click(first_column.findElement(By.tagName("a")));
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElementException : " + Xpath);
//		}
//
//	}

//	public ContactInfo getContactPersonInfoAt(String xpath, int index) {
//		List<ContactInfo> list = new ArrayList<ContactInfo>();
//		ContactInfo contactpackage = new ContactInfo();
//		try {
//			// Get the contact row
//			WebElement element = driver.findElement(By.xpath(xpath));
//			WebElement body = element.findElement(By.tagName("tbody"));
//			List<WebElement> eachpackage_row = body.findElements(By
//					.tagName("tr"));
//			// Get all columns of each row
//			List<WebElement> eachpackage_column = eachpackage_row.get(index)
//					.findElements(By.tagName("td"));
//			// Put info of each column into array
//			contactpackage.firstnamelink = eachpackage_column.get(0)
//					.findElement(By.tagName("a"));
//			contactpackage.lastnamelink = eachpackage_column.get(1)
//					.findElement(By.tagName("a"));
//			contactpackage.title = eachpackage_column.get(2).getText();
//			contactpackage.phone = eachpackage_column.get(3).getText();
//			contactpackage.email = eachpackage_column.get(4).getText();
//			list.add(contactpackage);
//		} catch (NoSuchElementException e) {
//		}
//		return contactpackage;
//	}

//	public String getTuningProcessStatus() {
//		waitForAjax();
//		try {
//			// Locate to Xpath
//			WebElement element = driver.findElement(By
//					.xpath(AccessoryInfo.TUNING_APPROVAL_PROCESS));
//			// Get status of tunning process
//			String status = element.findElement(By.tagName("span")).getText();
//			return status;
//		} catch (NoSuchElementException e) {
//			System.out.println("NoSuchElementException" + e);
//		}
//		return "";
//	}

//	public void closeCurrentWindow() {
//		waitForAjax();
//		try {
//			Robot robot = new Robot();
//			robot.keyPress(KeyEvent.VK_ALT);
//			robot.keyPress(KeyEvent.VK_F4);
//			robot.delay(200);
//			robot.keyRelease(KeyEvent.VK_ALT);
//			robot.keyRelease(KeyEvent.VK_F4);
//		} catch (AWTException e) {
//			e.printStackTrace();
//		}
//	}

//	public void setGlobalPromotions(String Xpath, String status) {
//		waitForAjax();
//		System.out.println(Xpath);
//		WebElement global_container = driver.findElement(By.xpath(Xpath));
//		String current_status = global_container.getAttribute("class");
//		if (current_status.contains(status)) {
//			System.out.println("global promotion is " + status);
//			return;
//		}
//		driver.findElement(By.xpath(DeviceEdit.GLOBAL_PROMOTIONS_SWITCH))
//				.click();
//	}

//	public void fillAddUser(Hashtable<String, String> hashXpath,
//			Hashtable<String, String> data) {
//		waitForAjax();
//		editData(hashXpath.get("firstName"), data.get("firstName"));
//		editData(hashXpath.get("lastName"), data.get("lastName"));
//		selectOptionByName(hashXpath.get("company"), data.get("company"));
//		editData(hashXpath.get("title"), data.get("title"));
//		editData(hashXpath.get("email"), data.get("email"));
//		editData(hashXpath.get("code"), data.get("code"));
//		editData(hashXpath.get("phone"), data.get("phone"));
//	}

//	public ArrayList<InputSpecifications> getInputSpecifications(String xpath) {
//		try {
//			System.out.println("getInputSpecifications by xpath : " + xpath);
//			ArrayList<InputSpecifications> list = new ArrayList<InputSpecifications>();
//			WebElement tbody = driver.findElement(By.tagName("tbody"));
//			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
//			if (rows.size() < 2) {
//				return null;
//			}
//			for (int i = 1; i < rows.size(); i++) {
//				List<WebElement> columns = rows.get(i).findElements(
//						By.tagName("td"));
//				InputSpecifications inputSpecifications = new InputSpecifications();
//				inputSpecifications.connectiontype = columns.get(0);
//				inputSpecifications.supportedinputchannels = columns.get(1);
//				list.add(inputSpecifications);
//			}
//			return list;
//		} catch (NoSuchElementException e) {
//			return null;
//		}
//	}

//	public boolean partnerSelectUserByTuningStatusNotPublished(
//			String tuningStatus) {
//		boolean result = true;
//		Boolean flag = true;
//		String isPublished = "Published";
//		int limit = getPageSize();
//		try {
//			while (flag) {
//				if (driver.getPageSource().contains(tuningStatus)) {
//					flag = false;
//					WebElement tbody = driver.findElement(By.tagName("tbody"));
//					// step 2 : find all row
//					List<WebElement> rows = tbody
//							.findElements(By.tagName("tr"));
//					// step 3 : for all row get element contain email and click
//					for (WebElement row : rows) {
//						// get all colums
//						List<WebElement> colums = row.findElements(By
//								.tagName("td"));
//						if (colums.size() == 8) {
//							if (colums.get(5).getText().contains(tuningStatus)
//									&& !colums.get(3).getText()
//											.contains(isPublished)) {
//								// found element
//								// click element
//								WebElement link = colums.get(1).findElement(
//										By.tagName("a"));
//								link.click();
//								waitForAjax();
//								return result;
//							}
//						}
//
//					}
//				}
//				limit--;
//				if (limit < 0) {
//					flag = false;
//				}
//				driver.findElement(By.linkText("Next")).click();
//				waitForAjax();
//			}
//		} catch (NoSuchElementException e) {
//			System.err.println("No element exception : " + tuningStatus);
//			result = false;
//		}
//		return result;
//	}

//	public boolean selectUserByTuningStatusHaveVariant(String tuningStatus) {
//		boolean result = true;
//		Boolean flag = true;
//		String isPublished = "Published";
//		int limit = getPageSize();
//		try {
//			while (flag) {
//				if (driver.getPageSource().contains(tuningStatus)) {
//					flag = false;
//					// find and click user by email
//					// step 1 : find element tbody
//					WebElement tbody = driver.findElement(By.tagName("tbody"));
//					// step 2 : find all row
//					List<WebElement> rows = tbody
//							.findElements(By.tagName("tr"));
//					// step 3 : for all row get element contain email and click
//					for (WebElement row : rows) {
//						// get all colums
//						List<WebElement> colums = row.findElements(By
//								.tagName("td"));
//						if (colums.size() == 8) {
//							if (colums.get(5).getText().contains(tuningStatus)
//									&& !colums.get(3).getText()
//											.contains(isPublished)
//									&& Integer
//											.parseInt(colums.get(2).getText()) > 0) {
//								// found element
//								// click element
//								WebElement link = colums.get(1).findElement(
//										By.tagName("a"));
//								link.click();
//								waitForAjax();
//								return result;
//							}
//						}
//
//					}
//				}
//				limit--;
//				if (limit < 0) {
//					flag = false;
//				}
//				driver.findElement(By.linkText("Next")).click();
//				waitForAjax();
//			}
//		} catch (NoSuchElementException e) {
//			System.err.println("No element exception : " + tuningStatus);
//			result = false;
//		}
//		return result;
//	}

//	public boolean selectUserByTuningStatus(String tuningStatus) {
//		boolean result = true;
//		Boolean flag = true;
//		int limit = getPageSize();
//		try {
//			while (flag) {
//				if (driver.getPageSource().contains(tuningStatus)) {
//					flag = false;
//					// find and click user by email
//					// step 1 : find element tbody
//					WebElement tbody = driver.findElement(By.tagName("tbody"));
//					// step 2 : find all row
//					List<WebElement> rows = tbody
//							.findElements(By.tagName("tr"));
//					// step 3 : for all row get element contain email and click
//					for (WebElement row : rows) {
//						// get all colums
//						List<WebElement> colums = row.findElements(By
//								.tagName("td"));
//						if (colums.size() == 8) {
//							if (colums.get(5).getText().contains(tuningStatus)) {
//								// found element
//								// click element
//								WebElement link = colums.get(1).findElement(
//										By.tagName("a"));
//								link.click();
//								waitForAjax();
//								return result;
//							}
//						}
//
//					}
//				}
//				limit--;
//				if (limit < 0) {
//					flag = false;
//				}
//				driver.findElement(By.linkText("Next")).click();
//				waitForAjax();
//			}
//		} catch (NoSuchElementException e) {
//			System.err.println("No element exception : " + tuningStatus);
//			result = false;
//		}
//		return result;
//	}

//	public String test(ArrayList<String> elements) {
//		boolean result = true;
//		addLog("Check all element by ArrayList");
//		waitForAjax();
//		for (String element : elements) {
//			Boolean isPresent = driver.findElements(By.xpath(element)).size() > 0;
//			if (!isPresent) {
//				addLog("Element " + element + " doesn't existed!");
//				result = false;
//			} else {
//				addLog("Element \""
//						+ driver.findElement(By.xpath(element)).getText()
//						+ "\" displayed!");
//				return driver.findElement(By.xpath(element)).getText();
//			}
//		}
//		return null;
//	}

//	public PartnerContact getPartnerContact(String xpath) {
//		PartnerContact contact = new PartnerContact();
//		String text = getTextByXpath(xpath);
//		String name = text.substring(0, text.indexOf("Email"));
//		String email = text.substring(text.indexOf("Email"));
//		Properties pro = new Properties();
//		try {
//			pro.load(new StringReader(email));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		contact.name = name;
//		contact.email = pro.getProperty("Email");
//		contact.phone = pro.getProperty("Phone");
//		return contact;
//	}

//	public boolean clickFirstVariant() {
//		String modelVariantId = "model-variants";
//		try {
//			WebElement element = driver.findElement(By
//					.className(modelVariantId));
//			List<WebElement> modelVariants = element.findElements(By
//					.tagName("a"));
//			modelVariants.get(0).click();
//			return true;
//		} catch (NoSuchElementException e) {
//			System.out.println(e.getCause());
//		}
//		return false;
//	}

//	public boolean selectUserByTuningMarketApproved(boolean imageChoosed) {
//		boolean result = true;
//		Boolean flag = true;
//		String isApproved = "Approved";
//		int limit = getPageSize();
//		try {
//			while (flag) {
//				if (driver.getPageSource().contains(isApproved)) {
//					flag = false;
//
//					WebElement tbody = driver.findElement(By.tagName("tbody"));
//					// step 2 : find all row
//					List<WebElement> rows = tbody
//							.findElements(By.tagName("tr"));
//					// step 3 : for all row get element contain email and click
//					for (WebElement row : rows) {
//						// get all colums
//						List<WebElement> colums = row.findElements(By
//								.tagName("td"));
//						if (colums.size() == 8) {
//							if (colums.get(5).getText().contains(isApproved)
//									&& colums.get(7).getText()
//											.contains(isApproved)) {
//								if (imageChoosed) {
//									colums.get(0)
//											.findElement(By.tagName("img"))
//											.getAttribute("src");
//								} else {
//									colums.get(0)
//											.findElement(By.tagName("img"))
//											.getAttribute("src");
//								}
//								WebElement link = colums.get(1).findElement(
//										By.tagName("a"));
//								link.click();
//								waitForAjax();
//								return result;
//							}
//						}
//
//					}
//				}
//				limit--;
//				if (limit < 0) {
//					flag = false;
//				}
//				driver.findElement(By.linkText("Next")).click();
//				waitForAjax();
//			}
//		} catch (NoSuchElementException e) {
//			System.err.println("No element exception");
//			result = false;
//		}
//		return result;
//	}

//	public boolean partnerSelectUserByTuningStatusAndName(String tuningStatus,
//			String name) {
//		boolean result = true;
//		Boolean flag = true;
//		int limit = getPageSize();
//		try {
//			while (flag) {
//				if (driver.getPageSource().contains(tuningStatus)) {
//					flag = false;
//					WebElement tbody = driver.findElement(By.tagName("tbody"));
//					// step 2 : find all row
//					List<WebElement> rows = tbody
//							.findElements(By.tagName("tr"));
//					// step 3 : for all row get element contain email and click
//					for (WebElement row : rows) {
//						// get all colums
//						List<WebElement> colums = row.findElements(By
//								.tagName("td"));
//						if (colums.size() == 8) {
//							if (colums.get(5).getText().contains(tuningStatus)
//									&& colums.get(1).getText().contains(name)) {
//								// found element
//								// click element
//								WebElement link = colums.get(1).findElement(
//										By.tagName("a"));
//								link.click();
//								waitForAjax();
//								return result;
//							}
//						}
//
//					}
//				}
//				limit--;
//				if (limit < 0) {
//					flag = false;
//				}
//				driver.findElement(By.linkText("Next")).click();
//				waitForAjax();
//			}
//		} catch (NoSuchElementException e) {
//			System.err.println("No element exception : " + tuningStatus);
//			result = false;
//		}
//		return result;
//	}

//	public boolean partnerSelectUserByName(String name) {
//		boolean result = true;
//		Boolean flag = true;
//		int limit = getPageSize(PageHome.ADMIN_USER_LIST_TABLE_INFO);
//		try {
//			while (flag) {
//				if (driver.getPageSource().contains(name)) {
//					flag = false;
//					WebElement tbody = driver.findElement(By.tagName("tbody"));
//					// step 2 : find all row
//					List<WebElement> rows = tbody
//							.findElements(By.tagName("tr"));
//					// step 3 : for all row get element contain email and click
//					for (WebElement row : rows) {
//						// get all colums
//						List<WebElement> colums = row.findElements(By
//								.tagName("td"));
//						if (colums.size() == 8) {
//							if (colums.get(1).getText().contains(name)) {
//								// found element
//								// click element
//								WebElement link = colums.get(1).findElement(
//										By.tagName("a"));
//								link.click();
//								waitForAjax();
//								return result;
//							}
//						}
//
//					}
//				}
//				limit--;
//				if (limit < 0) {
//					flag = false;
//				}
//				driver.findElement(By.linkText("Next")).click();
//				waitForAjax();
//			}
//		} catch (NoSuchElementException e) {
//			System.err.println("No element exception : " + name);
//			result = false;
//		}
//		return result;
//	}

//	public Boolean fillAddAccessoriesUploadTuning(
//			Hashtable<String, String> hashXpath, Hashtable<String, String> data) {
//		try {
//			waitForAjax();
//			selectOptionByName(hashXpath.get("brand"), data.get("brand"));
//			selectInputChannel(hashXpath.get("wired"), data.get("wired"));
//			selectInputChannel(hashXpath.get("bluetooth"),
//					data.get("bluetooth"));
//			editData(hashXpath.get("name"), data.get("name"));
//			editData(hashXpath.get("number"), data.get("number"));
//			editData(hashXpath.get("upc"), data.get("upc"));
//			selectOptionByName(hashXpath.get("type"), data.get("type"));
//			editData(hashXpath.get("description"), data.get("description"));
//			uploadFile(hashXpath.get("add tunning"), data.get("add tunning"));
//			uploadFile(hashXpath.get("add marketing"),
//					data.get("add marketing"));
//			return true;
//		} catch (NoSuchElementException e) {
//			addLog(e.toString());
//		}
//		return false;
//	}

//	public Boolean selectAnaccessorybyTuningStatus(String tuningstatus) {
//		try {
//			System.out.println("Select accessory : " + tuningstatus);
//			int size = getPageSize(PageHome.BRAND_ACCESSORY_TABLE_INFO);
//			for (int i = 0; i < size; i++) {
//				WebElement tbody = driver.findElement(By.tagName("tbody"));
//				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
//				for (WebElement row : rows) {
//					List<WebElement> columns = row.findElements(By
//							.tagName("td"));
//					if (columns.get(5).getText().equals(tuningstatus)
//							&& !columns.get(7).getText()
//									.equals("DTS Marketing Request Reivew")
//							&& !columns.get(3).getText().equals("Published")) {
//						columns.get(1).findElement(By.tagName("a")).click();
//						waitForAjax();
//						return true;
//					}
//				}
//				clickText("Next");
//			}
//		} catch (NoSuchElementException e) {
//			return false;
//		}
//		return false;
//	}

//	public Boolean selectAnaccessorybyTuningStatusApproved(String tuningstatus) {
//		try {
//			System.out.println("Select accessory : " + tuningstatus);
//			int size = getPageSize(PageHome.BRAND_ACCESSORY_TABLE_INFO);
//			for (int i = 0; i < size; i++) {
//				WebElement tbody = driver.findElement(By.tagName("tbody"));
//				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
//				for (WebElement row : rows) {
//					List<WebElement> columns = row.findElements(By
//							.tagName("td"));
//					if (columns.get(5).getText().equals(tuningstatus)) {
//						columns.get(1).findElement(By.tagName("a")).click();
//						waitForAjax();
//						return true;
//					}
//				}
//				clickText("Next");
//			}
//		} catch (NoSuchElementException e) {
//			return false;
//		}
//		return false;
//	}

//	public Boolean fillAddNewVariant(Hashtable<String, String> hashXpath,
//			Hashtable<String, String> data) {
//		try {
//			waitForAjax();
//			editData(hashXpath.get("upc"), data.get("upc"));
//			editData(hashXpath.get("name"), data.get("name"));
//			return true;
//		} catch (NoSuchElementException e) {
//			addLog(e.toString());
//		}
//		return false;
//	}

//	public boolean checkTuningActionsDisplayed() {
//		boolean chechTuning = false;
//		WebElement e = driver.findElement(By.id("tuning-action-id"));
//		List<WebElement> e1 = e.findElements(By.tagName("a"));
//		for (int i = 0; i < e1.size(); i++) {
//			String tmp = e1.get(i).getText();
//			if (tmp.equalsIgnoreCase("")) {
//				chechTuning = true;
//			} else {
//				return false;
//			}
//
//		}
//		return chechTuning;
//	}

//	public void createNewAccessory() {
//		// Navigate to �Accessories� page
//		click(PartnerHomePage.LINK_PARTNER_ACCESSORIES);
//		// VP. Verify that the �Add Accessory� link is displayed inside
//		// �Actions� module and the accessories table is also displayed.
//		isElementPresent(PageHome.createNewAccessory);
//		// Click "Add Accessory" link
//		click(PageHome.createNewAccessory);
//		// Fill valid value into all required fields.
//		// init data
//		Hashtable<String, String> data = new Hashtable<String, String>();
//		data.put("company", "TestCorp");
//		data.put("brand", "test corp");
//		data.put("id", RandomStringUtils.randomNumeric(9));
//		String displayName = "Test" + RandomStringUtils.randomNumeric(4);
//		data.put("name", displayName);
//		data.put("number", RandomStringUtils.randomNumeric(5));
//		data.put("upc", RandomStringUtils.randomNumeric(5));
//		data.put("type", "In-Ear");
//		data.put("wired", "1 (Mono)");
//		data.put("bluetooth", "");
//		data.put("date", "");
//		data.put("aliases", "Test ALIASES");
//		data.put("description", "Test DESCRIPTION");
//		data.put("add tunning", "");
//		data.put("img", "");
//		data.put("add marketing", "");
//		// Add
//		fillAddAccessoriesDTS(AddAccessory.getProductInputFieldIdsHash(), data);
//		// Click �Save� link
//		click(AddAccessory.SAVE);
//
//	}

//	public WebElement getElementByTag(String xpath, String tag, int index) {
//		List<WebElement> elements;
//		try {
//			elements = driver.findElement(By.xpath(xpath)).findElements(
//					By.tagName("a"));
//		} catch (NoSuchElementException e) {
//			System.out.println(e.getCause());
//			return null;
//		}
//		if (elements.size() >= index + 1) {
//			return elements.get(index);
//		} else {
//			return null;
//		}
//	}


//	public void createNewVersionAccessory(String AccessoryName,
//			String nameVersion) { // Should login with DTS user
//		/*
//		 * Init data
//		 */
//		Hashtable<String, String> data = new Hashtable<String, String>();
//		data.put("company", "TestCorp");
//		data.put("name", AccessoryName);
//		data.put("number", RandomStringUtils.randomNumeric(5));
//		data.put("upc", RandomStringUtils.randomNumeric(5));
//		data.put("type", "In-Ear");
//		data.put("wired", "2 (Stereo)");
//		data.put("description", "Test DESCRIPTION");
//		data.put("add tunning", Constant.tuning_hpxtt);
//		data.put("img1", AddAccessory.IMG_NAME[0]);
//		data.put("img2", AddAccessory.IMG_NAME[1]);
//		data.put("img3", AddAccessory.IMG_NAME[2]);
//		data.put("add marketing", Constant.AUDIO_ROUTE_FILE);
//		/*
//		 * Create accessory
//		 */
//		addAccessoriesPartner(AddAccessory.getProductInputFieldIdsHash(), data);
//		// Publish accessory
//		click(AccessoryInfo.PARTNER_ACTIONS_STEP_1);
//		click(AccessoryInfo.APPROVE_TUNING);
//		click(AccessoryInfo.PARTNER_ACTIONS_STEP_2);
//		click(AccessoryInfo.REQUEST_MARKETING_REVIEW);
//		click(AccessoryInfo.APPROVE_MARKETING);
//		click(AccessoryInfo.PUBLISH);
//		// click Create New Version
//		click(AccessoryInfo.EDIT_MODE);
//		switchWindowClickOption("Ok");
//		// Change name
//		type(AddAccessory.NAME, nameVersion);
//		// SAVE
//		click(AddAccessory.SAVE);
//
//	}

//	public String getTuningFileNameForAudioRoutes(String xpath, String tag) {
//		try {
//			WebElement element = driver.findElement(By.xpath(xpath));
//			WebElement webElement = element.findElement(By.tagName(tag));
//			return webElement.getAttribute("download");
//		} catch (NoSuchElementException e) {
//			System.err
//					.println("No element exception : getTuningFileNameForAudioRoutes");
//		}
//		return "";
//	}

//	public ArrayList<String> getListWebElementByTag(String xpath, String tag) {
//		ArrayList<String> data = new ArrayList<String>();
//		try {
//			List<WebElement> list = driver.findElement(By.xpath(xpath))
//					.findElements(By.tagName(tag));
//			for (WebElement item : list) {
//				data.add(item.getText());
//			}
//			return data;
//		} catch (NoSuchElementException e) {
//			System.err.println("No element exception : getListWebElementByTag");
//		}
//		return null;
//	}

//	public WebElement getWebElementByText(String xpath, String name) {
//		try {
//			WebElement element = driver.findElement(By.xpath(xpath));
//			List<WebElement> links = element.findElements(By.tagName("a"));
//			for (WebElement link : links) {
//				if (link.getText().equals(name)) {
//					return link;
//				}
//			}
//		} catch (NoSuchElementException e) {
//		}
//		return null;
//	}

//	public boolean isStepActive(String processStep) {
//		try {
//			String getClass = driver.findElement(By.xpath(processStep))
//					.getAttribute("class");
//			System.out.println("Class : " + getClass);
//			return getClass.contains("process-step-container step-active");
//		} catch (NoSuchElementException e) {
//		}
//		return false;
//	}

//	public Boolean isUserInfoEnable(ArrayList<String> list) {
//		try {
//			for (String UserInfo : list) {
//				String disable = getAtributeValue(UserInfo, "disable");
//				if (disable == null) {
//					addLog("User info: " + UserInfo
//							+ " is enable and editable");
//				} else {
//					addLog("User info: " + UserInfo
//							+ " is disable and uneditable");
//					return false;
//				}
//			}
//			return true;
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElement is present");
//			return false;
//		}
//	}
	
//	public boolean isAllPrivilegeSelected(String table) {
//		try {
//			// Get table
//			WebElement tbody = driver.findElement(By.xpath(table)).findElement(
//					By.tagName("tbody"));
//			// Get rows
//			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
//			for (WebElement row : rows) {
//				// Get columns
//				List<WebElement> columns = row.findElements(By.tagName("td"));
//				// Get privilege
//				WebElement privilege = columns.get(0);
//				Boolean isSelected = privilege.findElement(By.tagName("input"))
//						.isSelected();
//				if (isSelected) {
//					addLog("Privilege: " + privilege.getText()
//							+ " is selected");
//				} else {
//					addLog("Privilege: " + privilege.getText()
//							+ " is not selected");
//					return false;
//				}
//			}
//			return true;
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElement is present");
//			return false;
//		}
//	}
	
//	public String getABrandName(String Xpath) {
//		try {
//			// Get brand list
//			WebElement brandlist = driver.findElement(By.xpath(Xpath));
//			// Get a brand tag
//			WebElement brandName_tag = brandlist.findElement(By.tagName("div"))
//					.findElement(By.tagName("a"));
//			// Get brand name
//			String brandName = brandName_tag.getText();
//			addLog("Brand name: " + brandName);
//			return brandName;
//		} catch (NoSuchElementException e) {
//			addLog("NoSuchElement: " + Xpath);
//		}
//		return "";
//	}

	// public boolean checkType(String tableBody, int index, String type) {
	// boolean result = true;
	// waitForAjax();
	// // get total page
	// int totalPage = totalPage();
	// // get data column index
	// ArrayList<String> listType = new ArrayList<String>();
	// for (int i = 0; i < totalPage; i++) {
	// listType = getListTextAt(tableBody, index);
	// // check time within "days (14)" rolling days
	// for (String item : listType) {
	// if (!item.equals(type)) {
	// addLog("Type not match : " + item + " : " + type);
	// result = false;
	// return result;
	// } else {
	// addLog("Type match : " + item + " : " + type);
	// }
	// }
	// // click next page
	// try {
	// driver.findElement(By.linkText("Next")).click();
	// waitForAjax();
	// } catch (NoSuchElementException e) {
	// addLog("NoSuchElementException : Next");
	// result = false;
	// }
	// }
	// return result;
	// }

	// public Boolean fillAddAccessoriesDTS(Hashtable<String, String> hashXpath,
	// Hashtable<String, String> data) {
	// try {
	// waitForAjax();
	// selectOptionByName(hashXpath.get("company"), data.get("company"));
	// selectOptionByName(hashXpath.get("brand"), data.get("brand"));
	// selectInputChannel(hashXpath.get("wired"), data.get("wired"));
	// selectInputChannel(hashXpath.get("bluetooth"), data.get("bluetooth"));
	// editData(hashXpath.get("id"), data.get("id"));
	// editData(hashXpath.get("name"), data.get("name"));
	// editData(hashXpath.get("number"), data.get("number"));
	// editData(hashXpath.get("upc"), data.get("upc"));
	// selectOptionByName(hashXpath.get("type"), data.get("type"));
	// editData(hashXpath.get("description"), data.get("description"));
	// uploadFile(hashXpath.get("add tunning"), data.get("add tunning"));
	// uploadFile(hashXpath.get("add marketing"), data.get("add marketing"));
	// return true;
	// } catch (NoSuchElementException e) {
	// addLog(e.toString());
	// }
	// return false;
	// }

	// public Boolean isTextPresent(String text) {
	// /*
	// * int size = driver.findElements(By.linkText(text)).size(); if(size >
	// * 0){ System.out.println(text + " is present"); return true; }else{
	// * System.err.println(text + " doesn't existed"); return false; }
	// */
	// try {
	// if (driver.findElement(By.xpath("//*[contains(.,'" + text + "')]")) !=
	// null) {
	// return true;
	// }
	// } catch (NoSuchElementException e) {
	// return false;
	// }
	//
	// return driver.getPageSource().contains(text);
	// }

	// public Boolean deleteFileByXpath(String xpath) {
	// try {
	// WebElement webElement = driver.findElement(By.xpath(xpath));
	// WebElement del = webElement.findElement(By.linkText("Delete"));
	// del.click();
	// switchWindowClickOption("Delete");
	// waitForAjax();
	// } catch (NoSuchElementException e) {
	// return false;
	// }
	// return true;
	// }

	// public String getInnerHTML(WebElement web) {
	// try {
	// waitForAjax();
	// String text = web.getAttribute("innerHTML");
	// addLog("innerHTML : " + text);
	// return text;
	// } catch (NoSuchElementException e) {
	// addLog("NoSuchElementException at getTextByXpath : " + web);
	// return "";
	// }
	// }

	// public String getStatuSwitch(String xpath) {
	// String status = getTextByXpath(xpath);
	// return status;
	// }

	// unused
	// public void takeScreenShot(Boolean flag) {
	// // take screen shot when fail
	// System.out.println("Take screen shot if fail : " + flag);
	// if (!flag) {
	// try {
	// System.out.println("Take screen shot");
	// File scrnsht = ((TakesScreenshot)
	// driver).getScreenshotAs(OutputType.FILE);
	// String destDir = "target/surefire-reports/screenshots";
	// DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ssaa");
	// String destFile = dateFormat.format(new Date()) + ".png";
	// org.apache.commons.io.FileUtils.copyFile(scrnsht, new File(destDir + "/"
	// + destFile));
	// addLog("View error : <a href=../screenshots/" + destFile +
	// ">Screenshot</a>");
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }

	// public XPartnerShipPackage getPartnershipPackage(String Xpath, int index)
	// {
	// waitForAjax();
	// XPartnerShipPackage partnerPackage = new XPartnerShipPackage();
	// try {
	// // Get all brands in the brand table
	// WebElement brandtable = driver.findElement(By.xpath(Xpath));
	// List<WebElement> each_li = brandtable.findElements(By.tagName("li"));
	// if (each_li.size() > index) {
	// partnerPackage.type =
	// each_li.get(index).findElement(By.tagName("span")).getText();
	// partnerPackage.deletelink =
	// each_li.get(index).findElement(By.tagName("a"));
	// }
	// } catch (NoSuchElementException e) {
	// System.out.println("NoSuchElementException : ");
	// }
	// return partnerPackage;
	// }

	// public void selectAVariant(String Xpath) {
	// try {
	// // Get the Xpath
	// WebElement model_variant = driver.findElement(By.xpath(Xpath));
	// // Get the link
	// click(model_variant.findElement(By.tagName("a")));
	// } catch (NoSuchElementException e) {
	// System.out.println("NoSuchElementException");
	// }
	// }

	

	// public Boolean selectARandomUser() {
	// try {
	// // Get table
	// WebElement tbody = driver.findElement(By.tagName("tbody"));
	// // Get all rows
	// List<WebElement> rows = tbody.findElements(By.tagName("tr"));
	// // Generate a random user on User table
	// Random r = new Random();
	// int user_amount = rows.size() - 1;
	// int random_user = r.nextInt(user_amount);
	// // Get another random user if the selected random user is super user
	// if(rows.get(random_user).findElement(By.tagName("a")).getText().equals("fae")){
	// random_user = r.nextInt(user_amount);
	// }
	// // Select random user
	// addLog("Select user: " + random_user + " has name: " +
	// rows.get(random_user).findElement(By.tagName("a")).getText());
	// rows.get(random_user).findElement(By.tagName("a")).click();
	// waitForAjax();
	// } catch (NoSuchElementException e) {
	// System.err.println("No element exception");
	// return false;
	// }
	// return true;
	// }



	// public boolean isPromotionPublished(String xpath, String promotionID) {
	// try {
	// int size = getPageSize(PromotionList.PROMOTION_TABLE_INFO);
	// while (size > 0) {
	// // Get table
	// WebElement table = driver.findElement(By.xpath(xpath))
	// .findElement(By.tagName("tbody"));
	// // Get all rows
	// List<WebElement> rows = table.findElements(By.tagName("tr"));
	// // Find promotion ID
	// for (WebElement row : rows) {
	// List<WebElement> columns = row.findElements(By
	// .tagName("td"));
	// if (columns.get(0).getText().equals(promotionID)) {
	// // Promotion ID found
	// // Check publish status
	// if (columns.get(4).getText() != "") {
	// addLog("Promotion ID: " + promotionID
	// + " is published");
	// return true;
	// }
	// addLog("Promotion ID: " + promotionID
	// + " is not published");
	// return false;
	// }
	// }
	// size--;
	// if (size == 0) {
	// break;
	// }
	// clickText("Next");
	// }
	// addLog("Promotion ID: " + promotionID + " not found");
	// return false;
	// } catch (NoSuchElementException e) {
	// addLog("No element: " + xpath);
	// return null;
	// }
	// }


	// public void deleteAPromotionByName(String name) {
	// click(PageHome.linkPromotions);
	// selectAPromotionByName(PromotionList.PROMOTION_TABLE, name);
	// doDelete(PromotionInfo.DELETE);
	// try {
	// if (driver.findElement(By.xpath(PromotionList.PROMOTION_TABLE))
	// .isDisplayed()) {
	// addSuccessLog("Promotion: " + name
	// + " is deleted successfully");
	// return;
	// }
	// addErrorLog("Promotion: " + name
	// + " is not deleted successfully");
	// } catch (NoSuchElementException e) {
	// addErrorLog("Promotion: " + name
	// + " is not deleted successfully");
	// }
	// }

	
	
}
