package com.example.myapplication;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hicommon.common.ui.HiBaseActivity;
import com.example.myapplication.logic.HiMainActivityLogic;

/**
 * Date: 2021/2/23
 * Author: huozhenpeng
 */
public class HiMainActivity extends HiBaseActivity implements HiMainActivityLogic.ActivityProvider{
    private HiMainActivityLogic hiMainActivityLogic;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_main);
        hiMainActivityLogic = new HiMainActivityLogic(this, savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        hiMainActivityLogic.onSaveInstanceState(outState);
    }
}
