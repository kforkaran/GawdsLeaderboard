package com.karan.gawdsleaderboard.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL_REPO = "https://api.github.com/users/";
    public static final String BASE_URL_COMMITS = "https://api.github.com/repos/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit2 = null;

    public static Retrofit getClientRepo(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_REPO)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientCommits(){
        if(retrofit2 == null){
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(BASE_URL_COMMITS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit2;
    }
}
