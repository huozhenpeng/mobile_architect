package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hilog.HiLog;
import com.example.hilog.HiLogManager;
import com.example.hilog.printer.HiViewPrinter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HiViewPrinter hiViewPrinter = new HiViewPrinter(this);
        hiViewPrinter.getHiViewPrinterHelper().showFloatingView();
        HiLogManager.getInstance().addPrinter(hiViewPrinter);
    }

    public void log(View view) {
        HiLog.e("日志", "name");
    }

    public void tab(View view) {
        Intent intent = new Intent(this, TabActivity.class);
        startActivity(intent);
    }
}