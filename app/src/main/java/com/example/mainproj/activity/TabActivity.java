package com.example.mainproj.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mainproj.R;
import com.example.mainproj.adapter.TabAdapter;
import com.example.mainproj.fragment.TabOneFragment;
import com.example.mainproj.fragment.TabTwoFragment;
import com.example.mainproj.log.LogService;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {
    private Activity activity;

    private ImageButton btn_tab_back;

    private ViewPager2 vp_tab;

    private TabAdapter tabAdapter;

    private List<Fragment> fragmentList;
    private TabOneFragment fragment_tab_one;
    private TabTwoFragment  fragment_tab_two;

    private TabLayout tl_tab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_tab);
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
        vp_tab = findViewById(R.id.vp_tab);
        btn_tab_back = findViewById(R.id.btn_tab_back);

        tl_tab = findViewById(R.id.tl_tab);

        fragmentList = new ArrayList<>();
        fragment_tab_one = new TabOneFragment();
        fragment_tab_two = new TabTwoFragment();

        tabAdapter = new TabAdapter(this,fragmentList);
    }

    private void setting(){
        fragmentList.add(fragment_tab_one);
        fragmentList.add(fragment_tab_two);

        vp_tab.setAdapter(tabAdapter);

        tl_tab.addTab(tl_tab.newTab());
        tl_tab.addTab(tl_tab.newTab());

        new TabLayoutMediator(tl_tab, vp_tab, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView = new TextView(activity);
                if(position==0){
                    textView.setText("First Tab!");
                }
                else if(position==1){
                    textView.setText("Second Tab!");
                }
                tab.setCustomView(textView);
            }
        }).attach();
        // Default Tab Setting
        vp_tab.setCurrentItem(0);
    }

    private void addListener(){
        btn_tab_back.setOnClickListener(listener_tab_back);
        tl_tab.addOnTabSelectedListener(listener_tab_click);
    }

    private View.OnClickListener listener_tab_back = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private TabLayout.OnTabSelectedListener listener_tab_click = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {      // current page
            LogService.info(activity, ((TextView)tab.getCustomView()).toString()+" selected");
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {    // hide page
            LogService.info(activity, ((TextView)tab.getCustomView()).toString()+" UnSelected");
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {    // reClick page
            LogService.info(activity, ((TextView)tab.getCustomView()).toString()+" ReSelected");
        }
    };
}