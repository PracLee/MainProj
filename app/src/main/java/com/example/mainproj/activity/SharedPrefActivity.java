package com.example.mainproj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.mainproj.R;
import com.example.mainproj.log.LogService;

public class SharedPrefActivity extends AppCompatActivity {
    private Activity activity;

    private EditText et_pref_id,
            et_pref_pw,
            et_pref_name,
            et_pref_age;

    private Button btn_pref_save, btn_pref_load;

    private ImageButton btn_pref_back;

    // App Save
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_pref);

            init();
            setting();
            addListener();
        }
        catch (Exception e){
            LogService.error(this,e.getMessage(),e);
        }
    }
    private void init(){
        activity = this;
        btn_pref_back = findViewById(R.id.btn_pref_back);
        et_pref_id = findViewById(R.id.et_pref_id);
        et_pref_pw = findViewById(R.id.et_pref_pw);
        et_pref_name = findViewById(R.id.et_pref_name);
        et_pref_age = findViewById(R.id.et_pref_Age);
        btn_pref_save = findViewById(R.id.btn_pref_save);
        btn_pref_load = findViewById(R.id.btn_pref_load);
    }
    private void setting(){
        // MODE_PRIVATE : 사용자 앱 저장 모드
        preferences = getSharedPreferences("PREF_SETTING",MODE_PRIVATE);
        loadSharedPrefInfo();

    }
    private void addListener(){
         btn_pref_save.setOnClickListener(listener_pref_save);
         btn_pref_load.setOnClickListener(listener_pref_load);
         btn_pref_back.setOnClickListener(listener_pref_back_click);
    }

    // load pref function
    private void loadSharedPrefInfo(){
        et_pref_id.setText(preferences.getString("id",""));
        et_pref_pw.setText(preferences.getString("pw",""));
        et_pref_name.setText(preferences.getString("name",""));
        et_pref_age.setText(preferences.getString("age",""));
    }
    // Back Button Click
    private View.OnClickListener listener_pref_back_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    // Save Button Click
    private View.OnClickListener listener_pref_save = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SharedPreferences.Editor editor = preferences.edit();
            // Save to Preference key : id, value : et_pref_id.getText().toString()
            editor.putString("id", et_pref_id.getText().toString());
            editor.putString("pw",et_pref_pw.getText().toString());
            editor.putString("name",et_pref_name.getText().toString());
            editor.putString("age",et_pref_age.getText().toString());
            // Save to app
            editor.apply();
        }
    };

    // Load Button Click
    private View.OnClickListener listener_pref_load = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // get key "id", if error get ""
            // et_pref_id.setText(preferences.getString("id",""));
            loadSharedPrefInfo();
        }
    };
}