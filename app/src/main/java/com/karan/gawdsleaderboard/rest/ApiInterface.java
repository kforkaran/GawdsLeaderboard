package com.karan.gawdsleaderboard.rest;

import com.karan.gawdsleaderboard.model.Repository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("{user}/repos")
    Call<Repository> getRepositories(@Path("user") String githubHandle);
}
