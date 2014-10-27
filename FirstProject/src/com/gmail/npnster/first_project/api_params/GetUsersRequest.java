package com.gmail.npnster.first_project.api_params;

public class GetUsersRequest extends PageableGetRequest {

	private String mSearch;
	private boolean mLoadMore = false;
	
	public GetUsersRequest(int page, int perPage, String search) {
		super(page, perPage);
		mSearch = search;
	}

	public GetUsersRequest() {
		super();
	}
	
	public GetUsersRequest(int page, int perPage, String search, boolean loadMore) {
		super(page, perPage);
		mSearch = search;
		mLoadMore = loadMore;
		
	}
	
	public String getSearch() {
		return mSearch;
	}
	
	public boolean isLoadMore() {
		return mLoadMore;
	}

}
