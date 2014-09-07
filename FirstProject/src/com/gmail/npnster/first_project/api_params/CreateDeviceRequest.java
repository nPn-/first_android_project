package com.gmail.npnster.first_project.api_params;

public class CreateDeviceRequest extends TokenBasedRequest {
	private String gcm_reg_id;
	private String name;
	private String phone_number;
	private boolean primary;

	public CreateDeviceRequest(String gcm_reg_id, String name, boolean primary, String phone_number) {
		super();
		this.gcm_reg_id = gcm_reg_id;
		this.name = name;
		this.phone_number = phone_number;
		this.primary = primary;
	}
	
	public String getGcmRegId() {
		return gcm_reg_id;
	}
	
	

}
