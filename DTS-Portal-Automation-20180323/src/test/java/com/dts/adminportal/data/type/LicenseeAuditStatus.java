package com.dts.adminportal.data.type;

public enum LicenseeAuditStatus {
	UNSUBMITTED("UNSUBMITTED"),
	PENDING_DTS_REVIEW("PENDING DTS REVIEW"),
	APPROVED("APPROVED"),
	PUBLISHED("PUBLISHED");
	
	private String name;
	private LicenseeAuditStatus(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
