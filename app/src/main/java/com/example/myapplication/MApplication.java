package com.example.myapplication;

import android.app.Application;

import com.example.hilog.HiLog;
import com.example.hilog.HiLogConfig;
import com.example.hilog.HiLogManager;
import com.example.hilog.printer.HiConsolePrinter;
import com.google.gson.Gson;

/**
 * Date: 2021/2/1
 * Author: huozhenpeng
 */
public class MApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HiLogManager.init(new HiLogConfig() {
            @Override
            public String getGlobalTag() {
                return "MApplication";
            }

            @Override
            public boolean enable() {
                return true;
            }

            @Override
            public JsonParse injectJsonParser() {
                return new JsonParse() {
                    @Override
                    public String toJson(Object o) {
                        return new Gson().toJson(o);
                    }
                };
            }
        }, new HiConsolePrinter());
    }
}
