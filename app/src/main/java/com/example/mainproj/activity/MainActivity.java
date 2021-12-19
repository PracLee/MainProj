package com.example.mainproj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.mainproj.R;
import com.example.mainproj.log.LogService;

public class MainActivity extends AppCompatActivity {
    private Activity activity;

    private ImageButton btn_main_back;

    private ListView lv_main;
    private ListAdapter listAdapter;

    private String[] items = {"Tab View","List View Optional","Third"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            init();
            setting();
            addListener();
        }catch (Exception e){
            LogService.error(this,e.getMessage(),e);
        }
    }
    private void init(){
        activity = this;
        lv_main = findViewById(R.id.lv_main);
        btn_main_back = findViewById(R.id.btn_tab_back);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
    }
    private void setting(){
        lv_main.setAdapter(listAdapter);
    }
    private void addListener(){
        btn_main_back.setOnClickListener(listener_backClick);
        lv_main.setOnItemClickListener(listener_item_click);
    }
    private View.OnClickListener listener_backClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
    private AdapterView.OnItemClickListener listener_item_click = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = null;

            String item = items[i];
            if(item.equals("Tab View")){
                LogService.info(activity,"TabView Click");
                intent = new Intent(activity,TabActivity.class);
            }
            else if(item.equals("List View Optional")){
                LogService.info(activity,"List View Optional Click");
                intent = new Intent(activity, ArrayListActivity.class);
            }
            else if(item.equals("third")){
                LogService.info(activity,"Third Click");
            }
            if(intent!=null){
                startActivity(intent);
            }
        }
    };
}