package com.example.mainproj.activity;

import static com.example.mainproj.config.RequestCodeConfig.REQ_CODE;
import static com.example.mainproj.config.RequestCodeConfig.REQ_MAIN_ACTIVITY;
import static com.example.mainproj.config.ResultCodeConfig.RESULT_CODE_ACTIVITY_OK;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.mainproj.R;
import com.example.mainproj.config.ResultCodeConfig;
import com.example.mainproj.log.LogService;
import com.example.mainproj.utill.ConvertUnitUtil;

public class MainActivity extends AppCompatActivity {
    private Activity activity;

    private ImageButton btn_main_back;

    private ListView lv_main;
    private ListAdapter listAdapter;

    private String[] items = {
            "Tab View",
            "List View",
            "Navigation View",
            "Custom List View",
            "Recycle View",
            "Code Only Layout",
            "Activity Result",
            "CustomList Activity To Param TO FIX!!!"
    };

    private String login_id;

    // Save Parameter
    private ActivityResultLauncher<Intent> resultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            init();
            setting();
            addListener();
        }catch (Exception e){
            LogService.error(this,e.getMessage(),e);
        }
    }
    private void init(){
        activity = this;
        lv_main = findViewById(R.id.lv_main);
        btn_main_back = findViewById(R.id.btn_tab_back);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
    }
    private void setting(){
        lv_main.setAdapter(listAdapter);
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), activityResultCallback);
    }
    private void addListener(){
        Intent intent = getIntent();
        login_id = intent.getStringExtra("id");
        btn_main_back.setOnClickListener(listener_backClick);
        lv_main.setOnItemClickListener(listener_item_click);
    }
    private View.OnClickListener listener_backClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
    private AdapterView.OnItemClickListener listener_item_click = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = null;

            String item = items[i];
            if(item.equals("Tab View")){
                LogService.info(activity,"TabView Click");
                intent = new Intent(activity,TabActivity.class);
            }
            else if(item.equals("List View")){
                LogService.info(activity,"List View Optional Click");
                intent = new Intent(activity, ArrayListActivity.class);
            }
            else if(item.equals("Navigation View")){
                LogService.info(activity,"Navigation View Click");
                intent = new Intent(activity, NaviActivity.class);
                intent.putExtra("id",login_id);
            }
            else if(item.equals("Custom List View")){
                LogService.info(activity,"Custom List View Click");
                intent = new Intent(activity, CustomListActivity.class);
            }
            else if(item.equals("Recycle View")){
                LogService.info(activity, "Recycle Activity Click");
                intent = new Intent(activity, RecycleActivity.class);
            }
            else if(item.equals("Code Only Layout")){
                LogService.info(activity,"Code Only Layout");
                intent = new Intent(activity, CodeActivity.class);
            }
            else if(item.equals("Activity Result")){
                intent = new Intent(activity, ResultCodeActivity.class);
                intent.putExtra(REQ_CODE, REQ_MAIN_ACTIVITY);
            }
            else if(item.equals("CustomList Activity To Param TO FIX!!!")){
                intent = new Intent(activity, MainActivity.class);
                intent.putExtra("DATA", "Code_Move_CustomList");
                setResult(RESULT_CODE_ACTIVITY_OK, intent);
                finish();
            }
            if(intent!=null){
                //startActivity(intent);
                resultLauncher.launch(intent);
            }
        }
    };
    private ActivityResultCallback activityResultCallback = new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            int resultCode = result.getResultCode();

            Intent intent = result.getData();

            if(resultCode == ResultCodeConfig.RESULT_CODE_ACTIVITY_CANCELED){
                LogService.info(activity, "Result Code Activity Canceled!");

            }
            else if(resultCode == RESULT_CODE_ACTIVITY_OK){
                LogService.info(activity, "Result Param Activity OK!");
                if(intent != null){
                    Intent moveIntent;
                    String data = intent.getStringExtra("DATA");
                    LogService.info(activity,data);
                    if(data.equals("Code_Move_Data")){
                        moveIntent = new Intent(activity, CodeActivity.class);
                        resultLauncher.launch(moveIntent);
                    }
                    else if(data.equals("Code_Move_CustomList")){
                        moveIntent = new Intent(activity, CustomListActivity.class);
                        resultLauncher.launch(moveIntent);
                    }
                }
            }
        }
    };
}