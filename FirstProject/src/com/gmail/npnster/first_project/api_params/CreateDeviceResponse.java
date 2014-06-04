package com.gmail.npnster.first_project.api_params;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateDeviceResponse extends BaseResponse {

	private String name;
	private String gcm_reg_id;
	private boolean primary;

	
	public String getName() {
		return name;
	}
	public String getGcmRegId() {
		return gcm_reg_id;
	}
	public boolean isPrimary() {
		return primary;
	}


}
