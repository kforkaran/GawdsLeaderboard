package com.karan.gawdsleaderboard.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karan.gawdsleaderboard.R;
import com.karan.gawdsleaderboard.model.AccessToken;
import com.karan.gawdsleaderboard.rest.ApiClient;
import com.karan.gawdsleaderboard.rest.ApiInterface;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private String client_id ="c8f7c00e27aa4c85e7fd";
    private String client_secret = "32bcc720ef697242693a6221b70ba2eef5c38e8c";
    private String redirect_url = "localhost://callback";

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 123;
    private boolean isAuthenticated = false;

    public void gawdsButton(View view){
        Intent intent = new Intent(getApplicationContext(),AllYear.class);
        startActivity(intent);
    }

    public void isSignedIn(View view){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri uri = getIntent().getData();

        if(uri!=null && uri.toString().startsWith(redirect_url)){
            String code = uri.getQueryParameter("code");

            ApiInterface apiService = ApiClient.getAccessToken().create(ApiInterface.class);
            Call<AccessToken> accessTokenCall = apiService.getAccessToken(
                    client_id,client_secret,code
            );

            accessTokenCall.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    AccessToken accessToken = response.body();
                    Toast.makeText(MainActivity.this, "Access Token received", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Nooo", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
