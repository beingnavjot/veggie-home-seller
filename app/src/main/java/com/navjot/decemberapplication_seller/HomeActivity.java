package com.navjot.decemberapplication_seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;

public class HomeActivity extends AppCompatActivity {

    CardView cvadd,cvnew,cvaccepted,cvlist;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        cvadd=findViewById(R.id.addNew);
        cvnew=findViewById(R.id.newOrder);
        cvlist=findViewById(R.id.list);
        aSwitch=findViewById(R.id.switchMode);




        cvadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), AddVegetableActivity.class);
                startActivity(intent);
            }
        });
        cvnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        cvlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), VegetableListActivity.class);
                startActivity(intent);
            }
        });


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


               if (isChecked)
               {
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                   //Intent intent= new Intent(getApplicationContext(),HomeActivity.class);
                  // startActivity(intent);
               }
              if (!isChecked)
               {
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                  // Intent intent= new Intent(getApplicationContext(),HomeActivity.class);
                  // startActivity(intent);
               }


            }
        });


    }
}
