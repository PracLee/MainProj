package com.example.mainproj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mainproj.R;
import com.example.mainproj.log.LogService;
import com.example.mainproj.utill.ConvertUnitUtil;

import java.util.ArrayList;

public class CodeActivity extends AppCompatActivity {
    private Activity activity;
    private LinearLayout mainLayout;
    private ImageButton btn_code_back;
    private ArrayList<Button> buttonList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView();
            init();
            addListener();
        }catch (Exception e) {
            LogService.error(this, e.getMessage(), e);
        }
    }
    private void setContentView(){
        // Create Main Layout
        mainLayout = new LinearLayout(this);
        mainLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(Color.rgb(239,227,173));

        // Create Menu Layout
        LinearLayout menuLayout = new LinearLayout(this);

        menuLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ConvertUnitUtil.convertSizeToDp(this,46)));
        menuLayout.setOrientation(LinearLayout.HORIZONTAL);
        menuLayout.setBackgroundColor(Color.rgb(255,255,255));
        btn_code_back = new ImageButton(this);
        btn_code_back.setLayoutParams(new LinearLayout.LayoutParams(ConvertUnitUtil.convertSizeToDp(this,100), LinearLayout.LayoutParams.WRAP_CONTENT));
        btn_code_back.setImageResource(R.drawable.icon_back_arrow);
        btn_code_back.setBackgroundColor(Color.TRANSPARENT);
        btn_code_back.setScaleType(ImageView.ScaleType.FIT_START);
        btn_code_back.setPadding(ConvertUnitUtil.convertSizeToDp(this,35),ConvertUnitUtil.convertSizeToDp(this,15),0,ConvertUnitUtil.convertSizeToDp(this,15));

        // Set View
        menuLayout.addView(btn_code_back);
        mainLayout.addView(menuLayout);

        Button button;
        buttonList = new ArrayList<>();
        for(int i = 0; i < 10;i++){
            button = new Button(this);
            button.setTag(i+1);
            button.setText(i+1+", Button");
            buttonList.add(button);
            mainLayout.addView(button);
        }

        setContentView(mainLayout);
    }
    private void init(){
        activity = this;

    }
    private void addListener(){
        btn_code_back.setOnClickListener(listener_back_click);
        for(int i = 0; i<10; i++){
            buttonList.get(i).setOnClickListener(listener_btn_list_click);
        }
    }
    private View.OnClickListener listener_back_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
    private View.OnClickListener listener_btn_list_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = (int)view.getTag();
            if(id == 1){
                Toast.makeText(activity,"First Button Click!",Toast.LENGTH_SHORT).show();
            }
            else if(id==2){
                Toast.makeText(activity,"Second Button Click!",Toast.LENGTH_SHORT).show();
            }
            else if(id==3){
                Toast.makeText(activity,"Third Button Click!",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(activity,"Else Button Click!",Toast.LENGTH_SHORT).show();
            }
        }
    };
}