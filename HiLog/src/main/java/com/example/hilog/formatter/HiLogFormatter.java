package com.example.hilog.formatter;

/**
 * Date: 2021/2/1
 * Author: huozhenpeng
 */
public interface HiLogFormatter<T> {
    String format(T data);
}
