package com.example.mainproj.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mainproj.R;
import com.example.mainproj.log.LogService;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/*
    자바에서 제공하는 쓰레드를 사용 할 경우 안드로이드에서 신경써야할 부분이 많기 때문에,
    핸들러를 사용하여 메인 쓰레드에 영향을 주지 않게 동작하게 하여
    AsyncTask 를 사용하여 백그란운드에서 원격의 이미지 파일을 다운로드 하는 등의
    소비적인 작업을 메인 쓰레드에 영향을 주지 않으면서 처리하였다.
    그렇게 사용하던 AsyncTask 가 메모리 누수등의 문제로 인해
    2019년 11월 8일 Duplicate 되어, 대체 수단인 rxjava 를 사용한다.
 */
public class AsyncTaskActivity extends AppCompatActivity {
    private Activity activity;
    private ImageButton btn_async_back;
    private TextView tv_server_conn_status;
    private Button btn_server_conn;
    
    // rxjava Object
    // Must Add gradle impl
    /*
    * implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
    * implementation 'io.reactivex.rxjava2:rxjava:2.2.15'
    * */
    private Disposable backgroundTask;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_async);
/*
        NonUse
            Thread.sleep(3000);

            AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    return null;
                }
                // PreWork
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }
                // After Background Work
                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                }
            };
*/
            init();
            setting();
            addListener();
        }catch (Exception e){
            LogService.error(this,e.getMessage(),e);
        }
    }
    private void init(){
        activity = this;
        btn_async_back = findViewById(R.id.btn_async_back);
        btn_server_conn = findViewById(R.id.btn_server_conn);
        tv_server_conn_status = findViewById(R.id.tv_server_conn_status);
    }
    private void setting(){
        asyncTask();
    }
    private void addListener(){
        btn_async_back.setOnClickListener(listener_async_back);
        btn_server_conn.setOnClickListener(listener_server_conn);
    }

    private void disableUI(){
        btn_async_back.setEnabled(false);
        btn_server_conn.setEnabled(false);
    }

    private void enableUI(){
        btn_async_back.setEnabled(true);
        btn_server_conn.setEnabled(true);
    }

    // asyncTask
    private void asyncTask() {
        //  onPreExecute : 백그라운드 작업 전의 코드
        disableUI();
        progress = new ProgressDialog(activity);
        progress.setMessage("Try Contact Server...");
        progress.setCancelable(false);
        progress.show();

        backgroundTask = Observable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                String data = "";
                //  doInBackground : 백그라운드 작업 코드
                try {
                    // Ex) Server Connect 3s
                    data = connectServer();
                }
                catch (Exception e){
                    LogService.error(activity,e.getMessage(),e);
                }

                return data;
            }
        })
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        //  onPostExecute : 백그라운드 작업 후의 코드
                        LogService.info(activity,o.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_server_conn_status.setText(o.toString());
                                enableUI();
                            }
                        });
                        backgroundTask.dispose();
                        progress.dismiss();
                    }
                });
    }

    // set onClick Listener
    private View.OnClickListener listener_async_back = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
    private View.OnClickListener listener_server_conn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            connectServer();
        }
    };


    private String connectServer(){
        String addr = "http://192.168.104.188:8081/conn";
        String resBody = "";

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = null;
        String result = "";

        try {
            URL url = new URL(addr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 연결 최대시간 3초 설정(웹 표준)
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept","application/json");
            connection.setRequestProperty("Content-Type","application/json");

            // After API 28 version
            /* AndroidManifest 파일에
            * <uses-permission android:name="android.permission.INTERNET" />
            * 을 추가하여 주어야 한다.
            * 또한 https 통신이 아닌경우
            * android:usesCleartextTraffic="true"
            * 를 추가하여야 한다.
            * */
            connection.connect();

            int code = connection.getResponseCode();
            LogService.info(activity,"resCode : "+ code);

            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
            resBody = stringBuffer.toString();

            connection.disconnect();

            LogService.info(activity,resBody);

            JSONObject jsonObject = new JSONObject(resBody);

            if (jsonObject.get("result").equals(true)){
                result = (String) jsonObject.get("msg");
            }
        }
        catch (ConnectException connectException){
            result = "Failed Server Connection";
            LogService.error(activity,connectException.getMessage(),connectException);
        }
        catch (SocketTimeoutException socketTimeoutException){
            result = "Server Connect Timeout";
            LogService.error(activity,socketTimeoutException.getMessage(),socketTimeoutException);
        }
        catch (SocketException socketException){
            result = "Operation not permitted";
            LogService.error(activity,socketException.getMessage(),socketException);
        }
        catch (Exception e){
            result = "Unknown Error";
            LogService.error(activity,e.getMessage(),e);
        }
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }catch (IOException ioException){
                LogService.error(activity,ioException.getMessage(),ioException);
            }
        }

        return result;
    }
}






