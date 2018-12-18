package com.karan.gawdsleaderboard.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.karan.gawdsleaderboard.R;


public class MainActivity extends AppCompatActivity {

    public void gawdsButton(View view){
        Intent intent = new Intent(getApplicationContext(),AllYear.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
