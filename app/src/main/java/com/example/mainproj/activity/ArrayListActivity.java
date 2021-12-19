package com.example.mainproj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mainproj.R;
import com.example.mainproj.log.LogService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListActivity extends AppCompatActivity {

    private Activity activity;
    private ImageButton btn_array_back;

    private Button btn_array_item_add, btn_array_item_edit, btn_array_item_del;

    private List<String> itemList = new ArrayList<String>(Arrays.asList("First", "Second"));

    private ListView lv_array;

    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_array);
            init();
            setting();
            addListener();
        }catch (Exception e){
            LogService.error(this, e.getMessage(),e);
        }
    }

    private void init(){
        activity = this;
        btn_array_back = findViewById(R.id.btn_array_back);
        btn_array_item_add = findViewById(R.id.btn_array_item_add);
        btn_array_item_edit = findViewById(R.id.btn_array_item_edit);
        btn_array_item_del = findViewById(R.id.btn_array_item_del);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice,itemList);
        lv_array = findViewById(R.id.lv_array);
    }
    private void setting(){
        lv_array.setAdapter(arrayAdapter);
    }

    private void addListener(){
        btn_array_back.setOnClickListener(listener_back);
    }
    private View.OnClickListener listener_back = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
}