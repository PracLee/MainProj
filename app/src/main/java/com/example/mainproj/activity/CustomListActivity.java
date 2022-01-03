package com.example.mainproj.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mainproj.R;
import com.example.mainproj.adapter.CustomAdapter;
import com.example.mainproj.log.LogService;
import com.example.mainproj.vo.CustomMemberVO;

import java.util.ArrayList;

public class CustomListActivity extends AppCompatActivity {
    private Activity activity;
    private EditText et_custom_item_name,et_custom_item_age;
    private Button btn_custom_item_add;
    private ImageButton btn_custom_list_back;
    private ListView lv_custom_item;
    private CustomAdapter customAdapter;
    private ArrayList<CustomMemberVO> memberList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_custom_list);
            init();

            setting();
            addListener();

        }catch (Exception e){
            LogService.error(this,e.getMessage(),e);
        }
    }

    private void init(){
        activity = this;
        et_custom_item_age = findViewById(R.id.et_custom_item_age);
        et_custom_item_name = findViewById(R.id.et_custom_item_name);
        btn_custom_item_add = findViewById(R.id.btn_custom_item_add);
        btn_custom_list_back = findViewById(R.id.btn_custom_list_back);

        lv_custom_item = findViewById(R.id.lv_custom_item);
        memberList = new ArrayList<CustomMemberVO>();
        customAdapter = new CustomAdapter(activity, memberList);

    }
    private void setting(){
        lv_custom_item.setAdapter(customAdapter);
    }
    private void addListener(){
        btn_custom_item_add.setOnClickListener(listener_item_add);
        btn_custom_list_back.setOnClickListener(listener_back_click);
    }
    private View.OnClickListener listener_item_add = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name = et_custom_item_name.getText().toString();
            String age = et_custom_item_age.getText().toString();
            if(!(name.equals(""))&&!(age.equals(""))){
                customAdapter.addItem(new CustomMemberVO(name,age));
            }else{
                Toast.makeText(activity,"Check Input!",Toast.LENGTH_SHORT).show();
            }

        }
    };
    private View.OnClickListener listener_back_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
}
