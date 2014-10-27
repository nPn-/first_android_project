package com.gmail.npnster.first_project.api_params;

public class PushLocationsUpdateRequestResponse extends BaseResponse<PushLocationsUpdateRequestRequest> {

	private int rails_status_code;
	private int gcm_status_code;
	private int gcm_failed_count;
	private int gcm_success_count;
	
	
	public int getRailsStatusCode() {
		return rails_status_code;
	}
	public int getGcmStatusCode() {
		return gcm_status_code;
	}
	public int getGcmFailedCount() {
		return gcm_failed_count;
	}
	public int getGcmSuccessCount() {
		return gcm_success_count;
	}
}
