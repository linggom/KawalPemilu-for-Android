package com.antares.kawalpemilu.client;

import retrofit.RestAdapter;

public class RestClient {

	public static final String BASE_URL = "http://kawal-pemilu.appspot.com/api";
	
	private static VoteService mResultVoteService;
	
	public static VoteService getVoteService() {
		if(null == mResultVoteService) {
			RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(BASE_URL).build();
			mResultVoteService = restAdapter.create(VoteService.class);
		}
		return mResultVoteService;
	}
}
