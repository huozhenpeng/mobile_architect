package com.example.hilog;

import com.example.hilog.formatter.HiLogFormatter;
import com.example.hilog.formatter.HiStackTraceFormatter;
import com.example.hilog.formatter.HiThreadFormatter;
import com.example.hilog.printer.HiLogPrinter;

import java.util.List;

/**
 * Date: 2021/2/1
 * Author: huozhenpeng
 */
public abstract class HiLogConfig {
    public static final int MAX_LEN = 512;
    public static final HiLogFormatter<StackTraceElement[]> STACK_TRACE_FORMATTER = new HiStackTraceFormatter();
    public static final HiLogFormatter<Thread> THREAD_FORMATTER = new HiThreadFormatter();
    public String getGlobalTag() {
        return "HiLog";
    }

    public boolean enable() {
        return false;
    }

    public interface JsonParse{
        String toJson(Object o);
    }

    public JsonParse injectJsonParser() {
        return null;
    }

    public boolean includeThread() {
        return false;
    }
    public int stackTraceDepth() {
        return 5;
    }
}
