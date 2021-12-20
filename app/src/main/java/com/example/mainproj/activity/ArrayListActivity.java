package com.example.mainproj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mainproj.R;
import com.example.mainproj.log.LogService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListActivity extends AppCompatActivity {

    private Activity activity;
    private ImageButton btn_array_back;

    private Button btn_array_item_add,
            btn_array_item_edit,
            btn_array_item_del;

    private List<String> itemList = new ArrayList<String>(Arrays.asList("First", "Second"));

    private ListView lv_array;

    private ArrayAdapter arrayAdapter;

    private EditText et_array_item;

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
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,itemList);
        lv_array = findViewById(R.id.lv_array);
        et_array_item = findViewById(R.id.et_array_item);
    }
    private void setting(){
        lv_array.setAdapter(arrayAdapter);
        lv_array.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    private void addListener(){
        btn_array_back.setOnClickListener(listener_back);
        lv_array.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        btn_array_item_add.setOnClickListener(listener_item_add);
        btn_array_item_edit.setOnClickListener(listener_item_edit);
        btn_array_item_del.setOnClickListener(listener_item_del);
    }
    private View.OnClickListener listener_back = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private View.OnClickListener listener_item_add = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String item = et_array_item.getText().toString();
            if(item.equals("")){
                Toast.makeText(activity,"Input Text Please!",Toast.LENGTH_SHORT).show();
            }
            else{
                itemList.add(item);
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };

    private View.OnClickListener listener_item_edit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener listener_item_del = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}