package com.example.mainproj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.mainproj.R;
import com.example.mainproj.log.LogService;

public class ResultCodeActivity extends AppCompatActivity {
    private ImageButton btn_code_send_back;
    private Button btn_res_code_send
            ,btn_res_param_send
            ,btn_req_code_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_result_code);
            init();
            addListener();

        }catch (Exception e){
            LogService.error(this,e.getMessage(),e);
        }
    }
    public void init(){
        btn_code_send_back = findViewById(R.id.btn_code_back);
        btn_res_code_send = findViewById(R.id.btn_res_code_send);
        btn_res_param_send = findViewById(R.id.btn_res_param_send);
        btn_req_code_send = findViewById(R.id.btn_req_code_send);

    }
    public void addListener(){
        btn_code_send_back.setOnClickListener(listener_back_click);
        btn_res_code_send.setOnClickListener(listener_res_code_send);
        btn_res_param_send.setOnClickListener(listener_res_param_send);
        btn_req_code_send.setOnClickListener(listener_req_code_send);
    }
    private View.OnClickListener listener_back_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setResult(33);
            finish();
        }
    };
    private View.OnClickListener listener_res_code_send = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
    private View.OnClickListener listener_res_param_send = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
    private View.OnClickListener listener_req_code_send = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
}