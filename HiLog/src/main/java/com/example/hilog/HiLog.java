package com.example.hilog;


import androidx.annotation.NonNull;

import com.example.hilog.printer.HiLogPrinter;
import com.example.hilog.utils.HiStaceTraceUtils;

import java.util.List;

/**
 * Date: 2021/2/1
 * Author: huozhenpeng
 */
public class HiLog {
    private static final String HI_LOG_PACKAGE;
    static {
        String className = HiLog.class.getName();
        HI_LOG_PACKAGE = className.substring(0, className.lastIndexOf('.') + 1);
//        HI_LOG_PACKAGE = "";
    }
    public static void v(Object... contents) {
        log(HiLogType.V, contents);
    }
    public static void e(Object... contents) {
        log(HiLogType.E, contents);
    }
    public static void a(Object... contents) {
        log(HiLogType.A, contents);
    }
    public static void d(Object... contents) {
        log(HiLogType.D, contents);
    }
    public static void i(Object... contents) {
        log(HiLogType.I, contents);
    }
    public static void vt(String tag, Object... contents) {
        log(HiLogType.V, tag, contents);
    }
    public static void dt(String tag, Object... contents) {
        log(HiLogType.D, tag, contents);
    }
    public static void et(String tag, Object... contents) {
        log(HiLogType.E, tag, contents);
    }
    public static void at(String tag, Object... contents) {
        log(HiLogType.A, tag, contents);
    }
    public static void it(String tag, Object... contents) {
        log(HiLogType.I, tag, contents);
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
            String stackTrace = HiLogConfig.STACK_TRACE_FORMATTER.format(HiStaceTraceUtils.getCroppedRealStackTrack(new Throwable().getStackTrace(), HI_LOG_PACKAGE, config.stackTraceDepth()));
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
