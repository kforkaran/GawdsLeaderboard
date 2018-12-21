package com.karan.gawdsleaderboard;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.karan.gawdsleaderboard.activity.MainActivity;
import com.karan.gawdsleaderboard.model.AccessToken;
import com.karan.gawdsleaderboard.rest.ApiClient;
import com.karan.gawdsleaderboard.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {

    private String client_id ="c8f7c00e27aa4c85e7fd";
    private String client_secret = "32bcc720ef697242693a6221b70ba2eef5c38e8c";
    private String redirect_url = "localhost://callback";

    public void signIn(View view){

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/login/oauth/authorize"+"?client_id=" + client_id + "&scope=repo&redirect_url="+ redirect_url ));
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                        Toast.makeText(SignInActivity.this, "Access Token received", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Toast.makeText(SignInActivity.this, "Nooo", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
