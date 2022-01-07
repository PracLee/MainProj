package com.example.mainproj.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainproj.R;
import com.example.mainproj.adapter.ClickApplyAdapter;
import com.example.mainproj.log.LogService;

import java.util.ArrayList;
import java.util.List;

public class ClickApplyActivity extends AppCompatActivity implements View.OnClickListener, View.OnCreateContextMenuListener {
    private Activity activity;

    private ImageButton btn_click_apply_back;

    private EditText et_click_item_add;

    private Button btn_click_item_add;

    private RecyclerView rv_click_applay;

    private ClickApplyAdapter clickApplyAdapter;

    private List<String> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_click_apply);

            init();

            setting();

            addListener();

        }catch (Exception e){
            LogService.error(this,e.getMessage(),e);
        }
    }
    private void init(){
        activity = this;
        btn_click_apply_back = findViewById(R.id.btn_click_apply_back);
        btn_click_item_add = findViewById(R.id.btn_click_item_add);
        et_click_item_add = findViewById(R.id.et_click_item_add);
        rv_click_applay = findViewById(R.id.rv_click_apply);
        itemList = new ArrayList<>();
        clickApplyAdapter = new ClickApplyAdapter(this,itemList,this,this);

    }

    private void setting(){
        rv_click_applay.setAdapter(clickApplyAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,1);

        rv_click_applay.setLayoutManager(gridLayoutManager);
    }

    private void addListener() {
        btn_click_apply_back.setOnClickListener(this);

        btn_click_item_add.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_click_apply_back){
            finish();
        }
        else if(id == R.id.btn_click_item_add){
            String item = et_click_item_add.getText().toString();
            if(item.isEmpty() == false){
                itemList.add(item);
                clickApplyAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(activity,"Input Item Name!",Toast.LENGTH_SHORT).show();
            }
        }
        else if(id == R.id.btn_click_item){
            String tag = view.getTag().toString();
            Toast.makeText(activity,tag + " Inner Button Click!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        clickApplyAdapter.setCurrentPosition((int)v.getTag());
        menu.setHeaderTitle("Change Text Background Color!");
        menu.add(0,201,0,"Red");
        menu.add(0,202,0,"Blue");
        menu.add(0,203,0,"Green");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        int poisition = clickApplyAdapter.getCurrentPosition();

        TextView textView = rv_click_applay.findViewHolderForLayoutPosition(poisition).itemView.findViewById(R.id.tv_click_name);
        if(id == 201){
            textView.setBackgroundColor(Color.RED);
            Toast.makeText(activity,"Click Red!",Toast.LENGTH_SHORT).show();
        }
        else if(id == 202){
            textView.setBackgroundColor(Color.BLUE);
            Toast.makeText(activity,"Click Blue!",Toast.LENGTH_SHORT).show();
        }
        else if(id == 203){
            textView.setBackgroundColor(Color.GREEN);
            Toast.makeText(activity,"Click Green!",Toast.LENGTH_SHORT).show();

        }
        return super.onContextItemSelected(item);
    }
}