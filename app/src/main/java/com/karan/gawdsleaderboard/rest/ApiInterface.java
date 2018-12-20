package com.karan.gawdsleaderboard.rest;

import com.karan.gawdsleaderboard.model.AccessToken;
import com.karan.gawdsleaderboard.model.Commits;
import com.karan.gawdsleaderboard.model.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("{user}/repos")
    Call<List<Repository>> getRepositories(@Path("user") String githubHandle);

    @GET("{user}/{repo}/commits?per_page=100")
    Call<List<Commits>> getCommitsAuthor(@Path("user") String githunHandle, @Path("repo") String repoName);

    @Headers("Accept: applicaton/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
      @Field("client_id") String clientId,
      @Field("client_secret") String clientSecret,
      @Field("code") String code
    );
}
