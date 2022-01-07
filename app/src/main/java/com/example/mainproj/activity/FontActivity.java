package com.example.mainproj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mainproj.R;
import com.example.mainproj.log.LogService;

public class FontActivity extends AppCompatActivity {
    private Activity activity;
    private TextView tv_font_change;
    private ImageButton btn_font_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_font);

            init();

            setting();

            addListener();
        }catch (Exception e){
            LogService.error(this,e.getMessage(),e);
        }
    }
    private void init(){
        btn_font_back = findViewById(R.id.btn_font_back);
        tv_font_change = findViewById(R.id.tv_font_change);
    }
    private void setting(){
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/nexon.ttf");
        tv_font_change.setTypeface(typeface);
    }
    private void addListener(){
        btn_font_back.setOnClickListener(listener_back);
    }
    View.OnClickListener listener_back = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
}