package com.example.hilog;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hilog.printer.HiLogPrinter;

import java.util.Arrays;
import java.util.List;

/**
 * Date: 2021/2/1
 * Author: huozhenpeng
 */
public class HiLog {
    public static void v(Object... contents) {
        log(HiLogType.V, contents);
    }
    public static void vt(String tag, Object... contents) {
        log(HiLogType.V, tag, contents);
    }
    private static void log(@HiLogType.TYPE int type, Object... contents) {
        log(HiLogManager.getInstance().getConfig(), type, HiLogManager.getInstance().getConfig().getGlobalTag(), contents);
    }
    private static void log(@HiLogType.TYPE int type, @NonNull String tag, Object... contents) {
        log(HiLogManager.getInstance().getConfig(), type, tag, contents);
    }
    private static void log(@NonNull HiLogConfig config, @HiLogType.TYPE int type, @NonNull String tag, Object... contents) {
        if (!config.enable()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (config.includeThread()) {
            String threadInfo = HiLogConfig.THREAD_FORMATTER.format(Thread.currentThread());
            stringBuilder.append(threadInfo).append("\n");
        }
        if (config.stackTraceDepth() > 0) {
            String stackTrace = HiLogConfig.STACK_TRACE_FORMATTER.format(new Throwable().getStackTrace());
            stringBuilder.append(stackTrace).append("\n");
        }
        String body = parseBody(contents, config);
        stringBuilder.append(body);
        List<HiLogPrinter> printerList = HiLogManager.getInstance().getPrinters();
        if (printerList == null) {
            return;
        }
        for (HiLogPrinter printer : printerList) {
            printer.print(config, type, tag, stringBuilder.toString());
        }

    }

    private static String parseBody(@NonNull Object[] contents, @NonNull HiLogConfig config) {
        if (config.injectJsonParser() != null) {
            return config.injectJsonParser().toJson(contents);
        }
        StringBuilder sb = new StringBuilder();
        for (Object o : contents) {
            sb.append(o.toString()).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
