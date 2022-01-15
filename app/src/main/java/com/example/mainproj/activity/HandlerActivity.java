package com.example.mainproj.activity;
/*
import static java.lang.Thread.sleep;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mainproj.R;
import com.example.mainproj.listener.RadioCheckedChangeListener;
import com.example.mainproj.log.LogService;

import java.util.Timer;
import java.util.TimerTask;

public class HandlerActivity extends AppCompatActivity {
    private Activity activity;
    private Button btn_not_handler_start, btn_handler_start, btn_handler_stop, btn_handler_delay_start, btn_handler_delay_stop;

    private Button btn_timer_start, btn_timer_stop;
    private Timer timer;
    private HandlerTask timerTask;        // Non - Reusable object
    private int taskMode = 1;
    private RadioGroup rg_timer;

    private ImageButton btn_handler_back;
    private TextView tv_handler_time;

    private Thread errorThread;
    private int cnt = 0;

    private Thread handlerThread;
    private Handler handler;
    private final int SEND_INFO = 0;

    // code reusable
    public class HandlerTask extends TimerTask{
        private TextView tv_handler_time;
        private int taskMode;
        private int cnt;
        private boolean checkRun = false;
        public boolean hasRun(){
            return checkRun;
        }
        public int getCnt(){
            return cnt;
        }
        private HandlerTask(TextView tv_handler_time,int taskMode, int cnt){
            this.tv_handler_time = tv_handler_time;
            this.cnt = cnt;
            this.taskMode = taskMode;
        }
        @Override
        public void run() {
            checkRun = true;
            cnt++;
            if(taskMode == 1){
                LogService.info(activity,"Widget Post Called");
                tv_handler_time.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_handler_time.setText(" - "+cnt+" - ");
                    }
                });
            }
            else if(taskMode == 2){
                LogService.info(activity,"Run On UI Thread Called");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_handler_time.setText(" - "+cnt+" - ");
                    }
                });
            }
            else if(taskMode == 3){
                LogService.info(activity,"Handler Use Called");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_handler_time.setText(" - "+cnt+" - ");
                    }
                });
            }
        }
    }

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
        timer = new Timer();
        handler = new Handler();
        btn_not_handler_start = findViewById(R.id.btn_not_handler_start);
        btn_handler_start = findViewById(R.id.btn_handler_start);
        btn_handler_stop = findViewById(R.id.btn_handler_stop);
        btn_handler_back = findViewById(R.id.btn_handler_back);
        btn_handler_delay_start = findViewById(R.id.btn_handler_delay_start);
        btn_handler_delay_stop = findViewById(R.id.btn_handler_delay_stop);
        btn_timer_start = findViewById(R.id.btn_timer_start);
        btn_timer_stop = findViewById(R.id.btn_timer_stop);
        tv_handler_time = findViewById(R.id.tv_handler_time);
        rg_timer = findViewById(R.id.rg_timer);
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

        handlerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    cnt++;
                    try {
                        Message msg = handler.obtainMessage();
                        msg.what = SEND_INFO;
                        msg.obj = " - " + cnt + " - ";

                        handler.sendMessage(msg);

                        LogService.info(activity, "Thread Start");
                        sleep(1000);
                    }catch (InterruptedException inE){
                        LogService.info(activity,"End Thread");
                    }
                    catch (Exception e){
                        LogService.error(activity,e.getMessage(),e);
                        break;
                    }
                }
            }
        });
        handler = new Handler(Looper.getMainLooper())
        {
            @Override
            public void handleMessage(@NonNull Message msg)
            {
                super.handleMessage(msg);

                if(msg.what == SEND_INFO)
                {
                    String data = (String) msg.obj;
                    tv_handler_time.setText(data);
                }
            }
        };
        timerTask = new HandlerTask(tv_handler_time,taskMode,cnt);

    }


    private void addListener(){
        btn_handler_back.setOnClickListener(listener_back_click);
        btn_not_handler_start.setOnClickListener(listener_not_handler_start);
        btn_handler_start.setOnClickListener(listener_handler_start);
        btn_handler_stop.setOnClickListener(listener_handler_stop);
        btn_handler_delay_start.setOnClickListener(listener_handler_delay_start);
        btn_handler_delay_stop.setOnClickListener(listener_handler_delay_stop);
        btn_timer_start.setOnClickListener(listener_timer_start);
        btn_timer_stop.setOnClickListener(listener_timer_stop);
        //rg_timer.setOnCheckedChangeListener(listener_timer_mode_change);
        //rg_timer.setOnCheckedChangeListener(new RadioCheckedChangeListener(activity,rg_timer));
        rg_timer.setOnCheckedChangeListener(new RadioCheckedChangeListener(activity, rg_timer, new RadioCheckedChangeListener.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId, int beforeCheckedId) {
                LogService.info(activity,"checkedID : " + checkedId);
                LogService.info(activity,"beforeCheckedID : " + beforeCheckedId);
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if(timerTask.hasRun()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage("Timer already Running!" +
                                "\n Timer Stop?");
                        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // listener Click
                                listener_timer_stop.onClick(btn_timer_stop);
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(beforeCheckedId == R.id.rb_timer_wiget){
                                    taskMode = 1;
                                    ((RadioButton) rg_timer.getChildAt(0)).setChecked(true);
                                }
                                else if(beforeCheckedId == R.id.rb_timer_run){
                                    taskMode = 2;
                                    ((RadioButton) rg_timer.getChildAt(1)).setChecked(true);
                                }
                                else if(beforeCheckedId == R.id.rb_timer_handler){
                                    taskMode = 3;
                                    ((RadioButton) rg_timer.getChildAt(2)).setChecked(true);
                                }
                            }
                        });
                        // only Yes or No
                        builder.setCancelable(false);
                    }
                    if(i == R.id.rb_timer_wiget){
                        taskMode = 1;
                    }
                    else if(i == R.id.rb_timer_run){
                        taskMode = 2;
                    }
                    else if(i == R.id.rb_timer_handler){
                        taskMode = 3;
                    }
                    timerTask = new HandlerTask(tv_handler_time,taskMode,cnt);
                }
            }
        }));
    }
    private RadioCheckedChangeListener.OnCheckedChangeListener listener_timer_mode_change = new RadioCheckedChangeListener.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId, int beforeCheckedId) {

        }
    };

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
            if(handlerThread.getState()==Thread.State.NEW||handlerThread.getState()==Thread.State.TERMINATED){
                handlerThread.start();
            }
        }
    };
    private View.OnClickListener listener_handler_stop = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // duplicate
            // handlerThread.stop();

            // throw Exception
            handlerThread.interrupt();

            LogService.info(activity,"End Handler");
        }
    };
    private View.OnClickListener listener_handler_delay_start = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity,"Handler Delay Start",Toast.LENGTH_SHORT).show();
                }
            }, 3000);
        }
    };
    private View.OnClickListener listener_handler_delay_stop = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            handler.removeCallbacksAndMessages(null);
        }
    };
    private View.OnClickListener listener_timer_start = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!timerTask.hasRun()) {
                timer.schedule(timerTask, 0, 1000);
            }
        }
    };
    private View.OnClickListener listener_timer_stop = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            timerTask.cancel();
            cnt = timerTask.getCnt();
            timerTask = new HandlerTask(tv_handler_time,taskMode,cnt) {
                @Override
                public void run() {
                    cnt++;
                    tv_handler_time.setText("- "+cnt+" -");
                }
            };
        }
    };





    // Click Back Button -> Thread Destroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.interrupt();
        if(timerTask.hasRun()) {
            timerTask.cancel();
        }
        timerTask = null;
        LogService.info(activity,"End Intent");
    }
}
*/
