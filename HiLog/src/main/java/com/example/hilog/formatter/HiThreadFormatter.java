package com.example.hilog.formatter;

/**
 * Date: 2021/2/1
 * Author: huozhenpeng
 */
public class HiThreadFormatter implements HiLogFormatter<Thread> {
    @Override
    public String format(Thread data) {
        return "Thread:" + data.getName();
    }
}
