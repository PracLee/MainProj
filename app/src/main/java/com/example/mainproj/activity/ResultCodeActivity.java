package com.example.mainproj.activity;

import static com.example.mainproj.config.RequestCodeConfig.REQ_CODE;
import static com.example.mainproj.config.RequestCodeConfig.REQ_MAIN_ACTIVITY;
import static com.example.mainproj.config.ResultCodeConfig.RESULT_CODE_ACTIVITY_CANCELED;
import static com.example.mainproj.config.ResultCodeConfig.RESULT_CODE_ACTIVITY_OK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.mainproj.R;
import com.example.mainproj.config.ResultCodeConfig;
import com.example.mainproj.log.LogService;

public class ResultCodeActivity extends AppCompatActivity {
    private ImageButton btn_code_send_back;
    private Button btn_res_code_send
            ,btn_res_param_send
            ,btn_req_code_send
            ,btn_code_move;
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
        btn_code_move = findViewById(R.id.btn_code_move);

    }
    public void addListener(){
        btn_code_send_back.setOnClickListener(listener_back_click);
        btn_res_code_send.setOnClickListener(listener_res_code_send);
        btn_res_param_send.setOnClickListener(listener_res_param_send);
        btn_req_code_send.setOnClickListener(listener_req_code_send);
        btn_code_move.setOnClickListener(listener_res_code_move);
    }
    private View.OnClickListener listener_back_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setResult(RESULT_CODE_ACTIVITY_CANCELED);
            finish();
        }
    };
    private View.OnClickListener listener_res_code_send = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setResult(RESULT_CODE_ACTIVITY_OK);
            finish();
        }
    };
    private View.OnClickListener listener_res_param_send = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra("DATA","TEST_DATA");
            setResult(RESULT_CODE_ACTIVITY_OK, intent);
            finish();
        }
    };
    private View.OnClickListener listener_req_code_send = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();

            int reqCode = intent.getIntExtra(REQ_CODE, -1);

            if(reqCode == REQ_MAIN_ACTIVITY){
                intent.putExtra("DATA","Main_Data");
            }

            setResult(RESULT_CODE_ACTIVITY_OK,intent);
            finish();
        }
    };

    private View.OnClickListener listener_res_code_move = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();

            int reqCode = intent.getIntExtra(REQ_CODE, -1);

            if(reqCode == REQ_MAIN_ACTIVITY){
                intent.putExtra("DATA","Code_Move_Data");
            }

            setResult(RESULT_CODE_ACTIVITY_OK,intent);
            finish();
        }
    };
}