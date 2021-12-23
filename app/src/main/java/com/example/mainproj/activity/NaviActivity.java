package com.example.mainproj.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mainproj.R;
import com.example.mainproj.log.LogService;
import com.google.android.material.navigation.NavigationView;

public class NaviActivity extends AppCompatActivity {
    private Activity activity;
    private DrawerLayout layout_navi;
    private Toolbar tb_navi;
    private NavigationView nv_navi;
    private TextView tv_navi_login_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_navi);
            init();
            setting();
            addListener();
        }catch (Exception e){
            LogService.error(this,e.getMessage(),e);
        }
    }

    private void init(){
        activity = this;
        layout_navi = findViewById(R.id.layout_navi);
        tb_navi = findViewById(R.id.tb_navi);
        nv_navi = findViewById(R.id.nv_navi);
        //tv_navi_login_id = findViewById(R.id.tv_navi_login_id);
        tv_navi_login_id = nv_navi.getHeaderView(0).findViewById(R.id.tv_navi_login_id);
    }

    private void setting(){
        // Get LoginID
        Intent intent = getIntent();
        String loginID = intent.getStringExtra("id");

        // Set LoginID
        tv_navi_login_id.setText(loginID);

        // Set Toolbar to Action
        setSupportActionBar(tb_navi);
        // Set Toolbar Home Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Toolbar Home Button to optional
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_navi_menu);

    }

    private void addListener(){
        nv_navi.setNavigationItemSelectedListener(listener_navi_menu_click);
    }

    // Action Bar Click Event
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            layout_navi.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private NavigationView.OnNavigationItemSelectedListener listener_navi_menu_click = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            return false;
        }
    };
}