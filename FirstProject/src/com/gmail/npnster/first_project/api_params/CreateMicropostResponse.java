package com.gmail.npnster.first_project.api_params;

import java.util.HashMap;
import java.util.Map;

public class CreateMicropostResponse extends BaseResponse<CreateMicropostRequest> {
	private Micropost micropost;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Micropost getMicropost() {
		return micropost;
	}

	public void setMicropost(Micropost micropost) {
		this.micropost = micropost;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public class Micropost {

		private Integer id;
		private String content;
		private Integer user_id;
		private String created_at;
		private String updated_at;
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public Integer getUser_id() {
			return user_id;
		}

		public void setUser_id(Integer user_id) {
			this.user_id = user_id;
		}

		public String getCreated_at() {
			return created_at;
		}

		public void setCreated_at(String created_at) {
			this.created_at = created_at;
		}

		public String getUpdated_at() {
			return updated_at;
		}

		public void setUpdated_at(String updated_at) {
			this.updated_at = updated_at;
		}

		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

		public void setAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
		}

	}
}
