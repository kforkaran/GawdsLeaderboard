package com.karan.gawdsleaderboard;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.karan.gawdsleaderboard.activity.MainActivity;
import com.karan.gawdsleaderboard.model.AccessToken;
import com.karan.gawdsleaderboard.rest.ApiClient;
import com.karan.gawdsleaderboard.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        setContentView(R.layout.activity_sign_in);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
