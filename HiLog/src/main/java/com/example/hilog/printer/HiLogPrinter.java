package com.example.hilog.printer;

import androidx.annotation.NonNull;

import com.example.hilog.HiLogConfig;

/**
 * Date: 2021/2/1
 * Author: huozhenpeng
 */
public interface HiLogPrinter {
    void print(@NonNull HiLogConfig config, int level, String tag, @NonNull String msg);
}
