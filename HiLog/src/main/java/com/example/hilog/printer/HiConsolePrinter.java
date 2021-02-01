package com.example.hilog.printer;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hilog.HiLogConfig;

import static com.example.hilog.HiLogConfig.MAX_LEN;

/**
 * Date: 2021/2/1
 * Author: huozhenpeng
 */
public class HiConsolePrinter implements HiLogPrinter{
    @Override
    public void print(@NonNull HiLogConfig config, int level, String tag, @NonNull String msg) {
        int len = msg.length();
        int count = len / MAX_LEN;
        if (count > 0) {
            int index = 0;
            for (int i = 0; i < count; i++) {
                Log.println(level, tag, msg.substring(index, index + MAX_LEN));
                index += MAX_LEN;
            }
            if (index != len) {
                Log.println(level, tag, msg.substring(index, len));
            }
        } else {
            Log.println(level, tag, msg);
        }

    }
}
