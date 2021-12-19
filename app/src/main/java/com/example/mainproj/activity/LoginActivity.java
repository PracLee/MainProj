package com.example.mainproj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mainproj.R;
import com.example.mainproj.lifecycle.LoginLifeCycle;
import com.example.mainproj.log.LogService;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private Activity activity;
    private InputFilter filter_text;

    private EditText et_login_id, et_login_pw;

    private Button btn_login;

    private ImageButton btn_pw_show;

    private CheckBox chk_auto_login;

    private Boolean btn_PwFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_login);
            init();
            setting();
            addListener();

        }catch (Exception e) {
            Log.e("LoginActivity", e.getMessage());
        }
    }
    // constructor collection
    private void init(){
        activity = this;
        et_login_id = findViewById(R.id.et_login_id);
        et_login_pw = findViewById(R.id.et_login_pw);

        btn_login = findViewById(R.id.btn_login);
        btn_pw_show = findViewById(R.id.btn_pw_show);

        chk_auto_login = findViewById(R.id.chk_auto_login);


    }

    private void setting(){
        this.getLifecycle().addObserver(new LoginLifeCycle());

        filter_text = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                Pattern pattern = Pattern.compile("^[a-zA-Z09]*$");
                if(pattern.matcher(charSequence).matches()==false) {
                    return "";
                }
                return null;
            }
        };
    }

    private void addListener(){
        et_login_id.setFilters(new InputFilter[]{filter_text});

        et_login_pw.setFilters(new InputFilter[]{filter_text});

        btn_login.setOnClickListener(listener_pw_show);

        chk_auto_login.setOnClickListener(listener_auto_login);

        btn_login.setOnClickListener(listener_login);
    }

    private View.OnClickListener listener_pw_show = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(btn_PwFlag){
                btn_pw_show.setImageResource(R.drawable.icon_passwd_hide);
                et_login_pw.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                Log.i("LoginActivity","Button Activity");
            }
            else{
                btn_pw_show.setImageResource(R.drawable.icon_passwd_show);
                et_login_pw.setInputType(InputType.TYPE_CLASS_TEXT);

                Log.i("LoginActivity","Button Non-Activity");
            }

        }
    };

    private View.OnClickListener listener_auto_login = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(chk_auto_login.isChecked()){
                LogService.info(activity, "Set Auto Login");
            }
            else{
                LogService.info(activity, "Set Non-Auto Login");
            }
        }
    };

    private View.OnClickListener listener_login = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("id",et_login_id.getText().toString());
                intent.putExtra("pw",et_login_pw.getText().toString());
                startActivity(intent);
            }catch (Exception e){
                LogService.error(activity,e.getMessage(),e);
                Toast.makeText(activity,"Failed Login", Toast.LENGTH_SHORT).show();
            }
        }
    };
}