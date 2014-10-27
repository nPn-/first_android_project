package com.gmail.npnster.first_project.api_params;

public class PageableGetRequest {
	
	int mRequestedPageNumber = -1;
	int mRequestedItemsPerPage = -1;
	
	public PageableGetRequest() {		
	}
	
	public PageableGetRequest(int requestedPageNumber) {
		mRequestedPageNumber = requestedPageNumber;
	}
	
	public PageableGetRequest(int requestedPageNumber, int requestedItemsPerPage) {
		mRequestedPageNumber = requestedPageNumber > 0 ? requestedPageNumber : 1;
		mRequestedItemsPerPage = requestedItemsPerPage > 0 ? requestedItemsPerPage : 30;
	}
	
	public int getRequestedPageNumber() {
		return mRequestedPageNumber;
	}
	
	public int getRequestedItemsPerPage() {
		return mRequestedItemsPerPage;
	}

}
