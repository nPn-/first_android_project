package com.gmail.npnster.first_project.api_params;

import java.util.List;

public class GrantFollowerPermissionResponse extends BaseResponse {
	private List<String> granted_permissions;

	public List<String> getGrantedPermissions() {
		return granted_permissions;
	}
}
