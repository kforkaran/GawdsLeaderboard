package com.karan.gawdsleaderboard.model;

import com.google.gson.annotations.SerializedName;

public class AccessToken {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokentype;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokentype() {
        return tokentype;
    }
}
