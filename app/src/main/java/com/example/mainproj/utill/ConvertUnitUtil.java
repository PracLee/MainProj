package com.example.mainproj.utill;

import android.app.Activity;
import android.util.TypedValue;

public class ConvertUnitUtil {
    public static int convertSizeToDp(Activity activity, int size){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,size,activity.getResources().getDisplayMetrics());
    }
}
