package com.example.mainproj.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mainproj.R;
import com.example.mainproj.log.LogService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomDialog extends Dialog {

    public interface OnDialogClickListener{
        public void onYesClick();
        public void onNoClick();
    }

    public interface OnCalenderDialogClickListener{
        public void onDoneClick();
    }

    public enum DialogMode{MODE_CONFIRM, MODE_CALENDER};

    private DialogMode dialogMode;

    private Activity activity;

    private TextView tv_dialog_title;

    private ImageButton btn_close;


    private String title;
    private String content;

    // Custom Dialog Object Declare
    private View layout_dialog_confirm;
    private TextView tv_dialog_content;
    private Button btn_dialog_yes;
    private Button btn_dialog_no;
    private OnDialogClickListener onDialogClickListener;

    // Calender Dialog Object Declare
    private View layout_dialog_date;
    private CalendarView cv_dialog;
    private Button btn_dialog_done;
    private Date date;
    private SimpleDateFormat simpleDateFormat;
    private OnCalenderDialogClickListener onCalenderDialogClickListener;

    public CustomDialog(@NonNull Context context, DialogMode dialogMode) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.activity = (Activity) context;
        this.dialogMode = dialogMode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            setContentView(R.layout.dialog_custom);
            init();

            setting();

            addListener();

        }catch (Exception e){
            LogService.error(activity,e.getMessage(),e);
        }
    }
    private void init(){
        tv_dialog_title = findViewById(R.id.tv_dialog_title);
        btn_close = findViewById(R.id.btn_close);
        if(dialogMode == DialogMode.MODE_CONFIRM){
            layout_dialog_confirm = findViewById(R.id.layout_dialog_confirm);
            tv_dialog_content = findViewById(R.id.tv_dialog_content);
            btn_dialog_yes = findViewById(R.id.btn_dialog_yes);
            btn_dialog_no = findViewById(R.id.btn_dialog_no);
        }
        else if(dialogMode == DialogMode.MODE_CALENDER){
            layout_dialog_date = findViewById(R.id.layout_dialog_date);
        }

    }
    private void setting(){
        tv_dialog_title.setText(title);
        if(dialogMode == DialogMode.MODE_CONFIRM){
            layout_dialog_confirm.setVisibility(View.VISIBLE);
            tv_dialog_content.setText(content);
        }
        else if(dialogMode == DialogMode.MODE_CALENDER){
            layout_dialog_date.setVisibility(View.VISIBLE);
            cv_dialog = findViewById(R.id.cv_dialog);
            btn_dialog_done = findViewById(R.id.btn_dialog_done);
        }
    }

    private void addListener(){
        btn_close.setOnClickListener(listener_btn_close);
        if(dialogMode == DialogMode.MODE_CONFIRM){
            btn_dialog_yes.setOnClickListener(listener_dialog_yes);
            btn_dialog_no.setOnClickListener(listener_dialog_no);
        }
        else if(dialogMode == DialogMode.MODE_CALENDER){
            cv_dialog.setOnDateChangeListener(listener_change_date);
            btn_dialog_done.setOnClickListener(listener_dialog_done);
        }
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener){
        this.onDialogClickListener = onDialogClickListener;
    }

    public void setOnCalenderDialogClickListener(OnCalenderDialogClickListener onCalenderDialogClickListener){
        this.onCalenderDialogClickListener = onCalenderDialogClickListener;
    }

    public SimpleDateFormat getSimpleDateFormat(){
        return this.simpleDateFormat;
    }

    public Date getDate(){
        return this.date;
    }

    // OnClickListener
    private View.OnClickListener listener_btn_close = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Close Dialog
            dismiss();
        }
    };
    private View.OnClickListener listener_dialog_yes = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(onDialogClickListener != null){
                onDialogClickListener.onYesClick();
                dismiss();
            }
        }
    };
    private View.OnClickListener listener_dialog_no = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onDialogClickListener != null){
                onDialogClickListener.onNoClick();
                dismiss();
            }
        }
    };
    private View.OnClickListener listener_dialog_done = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(onCalenderDialogClickListener!=null){
                date = new Date(cv_dialog.getDate());
                simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                LogService.info(activity, simpleDateFormat.format(date)+" : Selected!");
                onCalenderDialogClickListener.onDoneClick();
                dismiss();
            }
        }
    };
    private CalendarView.OnDateChangeListener listener_change_date = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {    // year, month, day
            Calendar calendar = Calendar.getInstance();
            calendar.set(i,i1,i2);
            cv_dialog.setDate(calendar.getTimeInMillis());
        }
    };
}