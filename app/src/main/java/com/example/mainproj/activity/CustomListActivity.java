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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mainproj.R;
import com.example.mainproj.log.LogService;

import java.util.ArrayList;

public class CustomListActivity extends AppCompatActivity {
    private Activity activity;
    private EditText et_custom_item_name,et_custom_item_age;
    private Button btn_custom_item_add;
    private ImageButton btn_custom_list_back;
    private ListView lv_custom_item;
    /*private BaseAdapter baseAdapter;
    private ArrayList<String> list_name = new ArrayList<>();
    private ArrayList<String> list_age = new ArrayList<>();*/
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

       /* list_name.add("Lee");
        list_age.add("29");
        list_name.add("Kim");
        list_age.add("15");
        list_name.add("Park");
        list_age.add("30");

        baseAdapter = new BaseAdapter() {
            private ImageView iv_custom_profile;
            private TextView tv_custom_item_name;
            private TextView tv_custom_item_age;
            @Override
            public int getCount() {
                return list_name.size();
            }

            @Override
            public Object getItem(int i) {
                return list_name.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if(view == null){
                    view = LayoutInflater.from(activity).inflate(R.layout.item_custom_list,viewGroup,false);
                    iv_custom_profile = view.findViewById(R.id.iv_custom_profile);
                    tv_custom_item_name = view.findViewById(R.id.tv_custom_item_name);
                    tv_custom_item_age = view.findViewById(R.id.tv_custom_item_age);
                }
                tv_custom_item_name.setText(list_name.get(i));
                tv_custom_item_age.setText(list_age.get(i));

                if(i%2 == 1){
                    iv_custom_profile.setImageResource(R.drawable.icon_woman_profile);
                }

                return view;
            }
        };*/
    }
    private void setting(){
        //lv_custom_item.setAdapter(baseAdapter);
    }
    private void addListener(){
        btn_custom_item_add.setOnClickListener(listener_item_add);
    }
    private View.OnClickListener listener_item_add = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name = et_custom_item_name.getText().toString();
            String age = et_custom_item_age.getText().toString();
            if(!(name.equals(""))&&!(age.equals(""))){
//                list_name.add(name);
//                list_age.add(age);
//                baseAdapter.notifyDataSetChanged();
            }

        }
    };
}
