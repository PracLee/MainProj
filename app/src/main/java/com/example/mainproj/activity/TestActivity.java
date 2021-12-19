package com.example.mainproj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mainproj.R;
import com.example.mainproj.lifecycle.TestLifeCycle;
import com.example.mainproj.log.LogService;

public class TestActivity extends AppCompatActivity {

    private Activity activity;
    private Button btn_exit;
    private TextView tv_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_test);
            init();
            setting();
            addListener();

        }catch (Exception e){
            LogService.error(this,e.getMessage(),e);
        }
    }

    private void init(){
        activity = this;
        btn_exit = findViewById(R.id.btn_exit);
        tv_name = findViewById(R.id.tv_name);
    }

    private void setting(){
        this.getLifecycle().addObserver(new TestLifeCycle());
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String pw = intent.getStringExtra("pw");
        tv_name.setText(id);
        LogService.debug(this,pw);
    }

    private void addListener(){
        btn_exit.setOnClickListener(listener_exit);
    }

    private View.OnClickListener listener_exit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

}