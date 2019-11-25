package com.dts.adminportal.data.type;

public enum LicenseeProductType {
	HPX_LOW_1("DTS Sound"), 
	HPX_MEDIUM_1("DTS Headphone:X Kansas City"), 
	HPX_HIGH_1("DTS Headphone:X Beijing"),
	HPX_LOW_2("DTS Audio Processing"), 
	HPX_MEDIUM_2("DTS:X Premium"), 
	HPX_HIGH_2("DTS:X Ultra");
	
	private String name;
	
	LicenseeProductType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
