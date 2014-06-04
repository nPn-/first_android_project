package com.gmail.npnster.first_project.api_params;

public class CreateDeviceRequest extends TokenBasedRequest {
	private String gcm_reg_id;
	private String name;
	private boolean primary;

	public CreateDeviceRequest(String gcm_reg_id, String name, boolean primary) {
		super();
		this.gcm_reg_id = gcm_reg_id;
		this.name = name;
		this.primary = primary;
	}
	
	public String getGcmRegId() {
		return gcm_reg_id;
	}
	
	

}
