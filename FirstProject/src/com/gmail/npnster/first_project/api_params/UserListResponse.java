package com.gmail.npnster.first_project.api_params;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserListResponse extends BaseResponse {
	private Integer page;
	private Integer total_user_count;
	private List<User> users = new ArrayList<User>();
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotalCount() {
		return total_user_count;
	}


	public List<User> getUsers() {
		return users;
	}

	public List<String> getUserIds() {
		List<String> userIds = new ArrayList<String>();
		for(User u : users ) {
			System.out.println(u.getId().toString());
			userIds.add(u.getId().toString());
		}
		return userIds;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public class User {

		private Integer id;
		private String name;
		private String email;
		private String phone_number;
		private String gravatar_id;
		private List<String> permissions_granted_by_user_to_current_user;
		private List<String> permissions_granted_by_current_user_to_user;
		
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getGravatar_id() {
			return gravatar_id;
		}

		public void setGravatar_id(String gravatar_id) {
			this.gravatar_id = gravatar_id;
		}

		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

		public void setAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
		}
		
		public String getEmail() {
			return email;
		}
		
		public String getPhoneNumber() {
			return phone_number;
		}
		
		public List<String> getPermissionsGrantedByUserToCurrentUser() {
			return permissions_granted_by_user_to_current_user;
		}

		public List<String> getPermissionsGrantedByCurrentUserToUser() {
			return permissions_granted_by_current_user_to_user;
		}
	

	}
}
