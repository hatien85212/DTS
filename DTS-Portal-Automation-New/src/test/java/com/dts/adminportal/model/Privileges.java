package com.dts.adminportal.model;


public class Privileges {
	public static enum privileges {
		Add_and_manage_products("Add and manage products"), // 0
		Publish_and_suspend_products("Publish and suspend products"),
//		Request_product_tunings("Request product tunings"),
		Can_request_DTS_tune_products("Can request DTS tune products"),
		Edit_Company_Info("Edit Company Info"),
		Edit_brand_info("Edit brand info"),
		Add_and_manage_users("Add and manage users"); // 5
		//Add_and_manage_Apps_Devices("Add and manage Apps & Devices"),
		//Publish_and_suspend_apps_and_devices("Publish and suspend apps and devices"),
		//Add_and_manage_promotions("Add and manage promotions");
		private final String privilege;

		privileges (String privilege) {
			this.privilege = privilege;
		}

		public String getName() {
			return this.privilege;
		}
		
		public static String[] getNames(){
			privileges[] privileges = values();
			String[] result = new String[privileges.length];
			for (int i = 0; i < privileges.length; i++) {
				result[i] = privileges[i].getName();
			}
			return result;
		}
	};
	public static enum license_privileges {
		Add_and_manage_products("Add and manage products"), // 0
		Publish_and_suspend_products("Publish and suspend products"),
//		Request_product_tunings("Request product tunings"),
		Can_request_DTS_tune_products("Can request DTS tune products"),
		Edit_Company_Info("Edit Company Info"),
		Edit_brand_info("Edit brand info"),
		Add_and_manage_users("Add and manage users"), // 5
		Add_and_manage_Apps_Devices("Add and manage Apps & Devices"),
		Publish_and_suspend_apps_and_devices("Publish and suspend apps and devices"),
		Add_and_manage_promotions("Add and manage promotions");
		private final String license_privileges;

		license_privileges (String license_privileges) {
			this.license_privileges = license_privileges;
		}

		public String getName() {
			return this.license_privileges;
		}
		
		public static String[] getNames(){
			license_privileges[] privileges = values();
			String[] result = new String[privileges.length];
			for (int i = 0; i < privileges.length; i++) {
				result[i] = privileges[i].getName();
			}
			return result;
		}
	};
	
	public static enum dtsPrivileges {
		Approve_product_tunings("Approve product tunings"), // 0
		Approve_marketing_information("Approve marketing information"),
		Manage_audio_routes("Manage audio routes"),  //2
		Add_and_manage_apps_and_devices("Add and manage apps and devices"),
		Publish_and_suspend_apps_and_devices("Publish and suspend apps and devices"),	// 4
		Add_and_manage_promotions("Add and manage promotions"),
		Add_and_manage_company_accounts("Add and manage company accounts"),
		Add_and_manage_DTS_users("Add and manage DTS users");
											
		private final String privilege;

		dtsPrivileges (String privilege) {
			this.privilege = privilege;
		}

		public String getName() {
			return this.privilege;
		}
		
		public static String[] getNames(){
			dtsPrivileges[] privileges = values();
			String[] result = new String[privileges.length];
			for (int i = 0; i < privileges.length; i++) {
				result[i] = privileges[i].getName();
			}
			return result;
		}
	};
	
}
