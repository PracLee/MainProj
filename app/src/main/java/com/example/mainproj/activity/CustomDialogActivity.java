package com.example.mainproj.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mainproj.R;
import com.example.mainproj.log.LogService;

public class CustomDialogActivity extends AppCompatActivity {
    private Activity activity;
    private TextView tv_dialog_result;
    private ImageButton btn_custom_dialog_back;
    private Button btn_call_custom_dialog;
    private Button btn_call_calender_dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_custom_dialog);
            init();
            setting();
            addListener();

        }catch (Exception e){
            LogService.error(this,e.getMessage(),e);
        }
    }
    private void init(){
        activity = this;
        tv_dialog_result = findViewById(R.id.tv_dialog_result);
        btn_custom_dialog_back = findViewById(R.id.btn_custom_dialog_back);
        btn_call_custom_dialog = findViewById(R.id.btn_call_custom_dialog);
        btn_call_calender_dialog = findViewById(R.id.btn_call_calender_dialog);
    }
    private void setting(){

    }
    private void addListener(){
        btn_custom_dialog_back.setOnClickListener(listener_back_click);
        btn_call_custom_dialog.setOnClickListener(listener_call_custom_dialog);
        btn_call_calender_dialog.setOnClickListener(listener_call_calender_dialog);
    }

    private View.OnClickListener listener_back_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
    private View.OnClickListener listener_call_custom_dialog = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
    private View.OnClickListener listener_call_calender_dialog = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}