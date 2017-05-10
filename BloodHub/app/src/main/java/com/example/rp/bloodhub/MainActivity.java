package com.example.rp.bloodhub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.sql.SQLOutput;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public float multiply(){
        int i=1;
        int j=2;
        int k= j+i;
        System.out.println(k);
        return 0;

    }
}
