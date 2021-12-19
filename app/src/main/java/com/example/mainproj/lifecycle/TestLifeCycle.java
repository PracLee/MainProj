package com.example.mainproj.lifecycle;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.mainproj.log.LogService;

public class TestLifeCycle implements DefaultLifecycleObserver {
    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        LogService.debug((Activity)owner,"Start onStart");
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        LogService.debug((Activity)owner,"Start onResume");

    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        LogService.debug((Activity)owner,"Start onPause");

    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        LogService.debug((Activity)owner,"Start onStop");

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        LogService.debug((Activity)owner,"Start onDestory");

    }
}
