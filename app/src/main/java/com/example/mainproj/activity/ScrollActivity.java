package com.example.mainproj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mainproj.R;
import com.example.mainproj.log.LogService;
import com.example.mainproj.utill.ConvertUnitUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ScrollActivity extends AppCompatActivity {
    private Activity activity;
    private ImageButton btn_scroll_back;
    private LinearLayout layout_scroll;
    private FloatingActionButton fab_scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_scroll);

            init();
            setting();
            addListener();
        }catch (Exception e){
            LogService.error(this, e.getMessage(),e);
        }
    }
    private void init(){
        activity = this;
        btn_scroll_back = findViewById(R.id.btn_header_back);
        layout_scroll = findViewById(R.id.layout_scroll);
        fab_scroll = findViewById(R.id.fab_scroll);
    }
    private void setting(){

    }
    private void addListener(){
        btn_scroll_back.setOnClickListener(listener_back);
        fab_scroll.setOnClickListener(listener_scroll);
    }

    View.OnClickListener listener_back = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
    View.OnClickListener listener_scroll = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            layout_scroll.addView(createNewView());
        }
    };

    // Create New View
    private TextView createNewView(){
        TextView textView = new TextView(this);
        textView.setText("New Text View");
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ConvertUnitUtil.convertSizeToDp(this,50)
            )
        );
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.rgb(0,0,0));
        textView.setBackgroundColor(Color.rgb(255,174,201));
        return textView;
    }
}