package com.example.hilog.formatter;

/**
 * Date: 2021/2/1
 * Author: huozhenpeng
 */
public class HiStackTraceFormatter implements HiLogFormatter<StackTraceElement[]> {
    @Override
    public String format(StackTraceElement[] stackTraces) {
        StringBuilder stringBuilder = new StringBuilder();
        if (stackTraces == null || stackTraces.length == 0) {
            return null;
        } else if (stackTraces.length == 1) {
            return "\t- " + stackTraces[0].toString();
        } else {
            for (int i = 0; i < stackTraces.length; i++) {
                if (i == 0) {
                    stringBuilder.append("stackTrace: \n");
                }
                if (i != stackTraces.length - 1) {
                    stringBuilder.append("\t├");
                    stringBuilder.append(stackTraces[i].toString());
                    stringBuilder.append("\n");
                } else {
                    stringBuilder.append("\t┕");
                    stringBuilder.append(stackTraces[i].toString());
                }
            }
        }
        return null;
    }
}
