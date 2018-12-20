package com.karan.gawdsleaderboard.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL_REPO = "https://api.github.com/users/";
    public static final String BASE_URL_COMMITS = "https://api.github.com/repos/";
    public static final String BASE_URL_AUTH = "https://github.com/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit2 = null;
    private static Retrofit retrofit3 = null;

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

    public static Retrofit getAccessToken(){
        if(retrofit3==null){
            retrofit3 = new Retrofit.Builder()
                    .baseUrl(BASE_URL_AUTH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit3;
    }
}
