package com.om.taskmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {
private static int SPLASH_TIME_OUT=1000 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                SharedPreferences sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
//                String username=sharedPreferences.getString("username","");
//                String password=sharedPreferences.getString("password","");
//
//                if(username.equals("admin") && password.equals("admin1")){
//                    Intent maintent= new Intent(Welcome.this,Dashboard.class);
//                    startActivity(maintent);
//
//
//                }else{
//                    Intent maintent= new Intent(Welcome.this,MainActivity.class);
//                    startActivity(maintent);
//                }
                Intent maintent= new Intent(Welcome.this,MainActivity.class);
                startActivity(maintent);
                finish();
            }
        },SPLASH_TIME_OUT);

    }
}
