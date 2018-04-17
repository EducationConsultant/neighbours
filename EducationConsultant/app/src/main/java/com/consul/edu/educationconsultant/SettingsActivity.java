package com.consul.edu.educationconsultant;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        toolbar.setTitleTextColor(Color.WHITE);
        // To get the toolbar to behave like an app bar. Parameter: the toolbar you want to set as the activity’s app bar
        setSupportActionBar(toolbar);

        // getSupportActionBar: using the toolbar from the Support Library
        ActionBar actionBar = getSupportActionBar();
        // This enables the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void onClickEducationLevels(View view){
        Intent intent = new Intent(SettingsActivity.this, SettingsEducationLevelsActivity.class);
        startActivity(intent);
    }

    public void onClickCategoryFilter(View view) {
        Intent intent = new Intent(SettingsActivity.this, SettingsCategoryActivity.class);
        startActivity(intent);
    }
}
