package com.gmail.npnster.first_project.api_params;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetFollowersResponse extends BaseResponse {

	private Integer page;
	private Integer total_followers_count;
	private List<Follower> followers = new ArrayList<Follower>();
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal_followers_count() {
		return total_followers_count;
	}

	public void setTotal_followers_count(Integer total_followers_count) {
		this.total_followers_count = total_followers_count;
	}

	public List<Follower> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Follower> followers) {
		this.followers = followers;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public class Follower {

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
