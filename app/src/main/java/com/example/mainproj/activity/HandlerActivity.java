package com.example.mainproj.activity;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mainproj.R;
import com.example.mainproj.log.LogService;

public class HandlerActivity extends AppCompatActivity {
    private Activity activity;
    private Button btn_not_handler_start, btn_handler_start, btn_handler_stop;
    private ImageButton btn_handler_back;
    private TextView tv_handler_time;

    private Thread errorThread;
    private int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_handle);
            init();
            setting();
            addListener();
        }
        catch (Exception e){
            LogService.error(this, e.getMessage(),e);
        }
    }
    private void init(){
        activity = this;
        btn_not_handler_start = findViewById(R.id.btn_not_handler_start);
        btn_handler_start = findViewById(R.id.btn_handler_start);
        btn_handler_stop = findViewById(R.id.btn_handler_stop);
        btn_handler_back = findViewById(R.id.btn_handler_back);
        tv_handler_time = findViewById(R.id.tv_handler_time);
    }
    private void setting(){
        errorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    cnt++;
                    try {
                        tv_handler_time.setText(" - "+cnt+" -");
                        LogService.info(activity," - "+cnt+" -" );
                        sleep(1000);
                    }catch (Exception e){
                        LogService.error(activity,e.getMessage(),e);
                        break;
                    }
                }
            }
        });
    }

    private void addListener(){
        btn_handler_back.setOnClickListener(listener_back_click);
        btn_not_handler_start.setOnClickListener(listener_not_handler_start);
        btn_handler_start.setOnClickListener(listener_handler_start);
        btn_handler_stop.setOnClickListener(listener_handler_stop);
    }

    private View.OnClickListener listener_back_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private View.OnClickListener listener_not_handler_start = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            errorThread.start();
        }
    };

    private View.OnClickListener listener_handler_start = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
    private View.OnClickListener listener_handler_stop = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}