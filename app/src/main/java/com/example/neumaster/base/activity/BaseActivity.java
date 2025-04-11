package com.example.neumaster.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 基类
 * Created by huanghaibin on 2017/11/16.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static boolean isMiUi = false;

    protected void initWindow() {

    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();
    protected abstract  void initEvent() ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(getLayoutId());
        initData();
        initView();

        initEvent();
    }


}
