package com.gmail.npnster.first_project.api_params;


import java.util.HashMap;
import java.util.Map;

public class GetUserProfileResponse {

	private Integer id;
	private String name;
	private String gravatar_id;
	private String email;
	private Integer microposts_count;
	private Integer followed_users_count;
	private Integer followers_count;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	
	public String getEmail() {
		return email;
	}
	
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

	public Integer getMicroposts_count() {
		return microposts_count;
	}

	public void setMicroposts_count(Integer microposts_count) {
		this.microposts_count = microposts_count;
	}

	public Integer getFollowed_users_count() {
		return followed_users_count;
	}

	public void setFollowed_users_count(Integer followed_users_count) {
		this.followed_users_count = followed_users_count;
	}

	public Integer getFollowers_count() {
		return followers_count;
	}

	public void setFollowers_count(Integer followers_count) {
		this.followers_count = followers_count;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
