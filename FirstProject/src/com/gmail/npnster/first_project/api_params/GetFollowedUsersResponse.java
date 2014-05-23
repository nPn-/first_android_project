package com.gmail.npnster.first_project.api_params;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetFollowedUsersResponse extends BaseResponse {

	private Integer page;
	private Integer total_followed_users_count;
	private List<FollowedUsers> following = new ArrayList<FollowedUsers>();
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal_followed_users_count() {
		return total_followed_users_count;
	}

	public void setTotal_followed_users_count(Integer total_followed_users_count) {
		this.total_followed_users_count = total_followed_users_count;
	}

	public List<FollowedUsers> getFollowedUsers() {
		return following;
	}

	public void setFollowedUsers(List<FollowedUsers> followedUsers) {
		this.following = followedUsers;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public class FollowedUsers {

		private Integer id;
		private String name;
		private String gravatar_id;
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

	}
}