package com.antares.kawalpemilu.client;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface VoteService {


	@GET("/children/{parent}")
	void getHie(@Path("parent") String parent, Callback<String[][]> callback);

	@GET("/tps/{parent}")
	void getTPS(@Path("parent") String parent, Callback<String[][]> callback);

}
