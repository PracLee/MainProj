package com.example.mainproj.listener;

import android.app.Activity;
import android.widget.RadioGroup;

import com.example.mainproj.log.LogService;

public class RadioCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
    private Activity activity;
    private RadioGroup radioGroup;
    private int beforeCheckedId;
    private OnCheckedChangeListener callBack;
    public interface OnCheckedChangeListener{
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId, int beforeCheckedId);
    };

    public RadioCheckedChangeListener(Activity activity, RadioGroup radioGroup, OnCheckedChangeListener callBack) {
        this.activity = activity;
        this.radioGroup = radioGroup;
        // Save recent Checked ID
        this.beforeCheckedId = radioGroup.getCheckedRadioButtonId();
        this.callBack = callBack;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        this.onCheckedChanged(radioGroup,i,beforeCheckedId);
        LogService.info(activity,"Radio Change Check!");
        //LogService.info(activity,"Checked ID == "+ i);
        //LogService.info(activity,"BeforeChecked ID == "+ beforeCheckedId);
    }

    public void onCheckedChanged(RadioGroup group, int i, int beforeCheckedId){
        this.callBack.onCheckedChanged(group,i,beforeCheckedId);
        this.beforeCheckedId = i;
    }
}
