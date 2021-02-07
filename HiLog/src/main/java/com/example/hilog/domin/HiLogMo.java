package com.example.hilog.domin;

import com.example.hilog.HiLogType;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Date: 2021/2/7
 * Author: huozhenpeng
 */
public class HiLogMo {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    public long timeMills;
    public int level;
    public String tag;
    public String log;

    public HiLogMo(long timeMills, int level, String tag, String log) {
        this.timeMills = timeMills;
        this.level = level;
        this.tag = tag;
        this.log = log;
    }

    public String format(long timeMills) {
        return simpleDateFormat.format(timeMills);
    }

    public String getFlattened() {
        return format(timeMills) + '|' + level + '|' + tag + '|';
    }

    public String getFlattenedLog() {
        return getFlattened() + "\n" + log;
    }

}
