package com.example.hilog.utils;

import android.text.TextUtils;

/**
 * Date: 2021/2/2
 * Author: huozhenpeng
 */
public class HiStaceTraceUtils {
    /**
     * 裁剪堆栈
     * @param stackTraceElements
     * @param maxDepth
     * @return
     */
    private static StackTraceElement[] cropStackTrace(StackTraceElement[] stackTraceElements, int maxDepth) {
        int realDepth = stackTraceElements.length;
        if (maxDepth > 0) {
            realDepth = Math.min(maxDepth, realDepth);
        }
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(stackTraceElements, 0, realStack, 0, realDepth);
        return realStack;
    }

    /**
     * 获取忽略了指定包名的堆栈
     * 比如说日志包名
     * @param stackTraceElements
     * @param ignorePackage
     * @return
     */
    private static StackTraceElement[] getRealStaceTrace(StackTraceElement[] stackTraceElements, String ignorePackage) {
        int ignoreDepth = 0;
        int allDepth = stackTraceElements.length;
        String className;
        //应该是只能忽略最后的那个堆栈信息，这个方法写的有点怪
        for (int i = allDepth - 1; i >= 0; i--) {
            className = stackTraceElements[i].getClassName();
            if (!TextUtils.isEmpty(ignorePackage) && className.startsWith(ignorePackage)) {
                ignoreDepth = i + 1;
                break;
            }
        }
        int realDepth = allDepth - ignoreDepth;
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(stackTraceElements, ignoreDepth, realStack, 0, realDepth);
        return realStack;
    }

    public static StackTraceElement[] getCroppedRealStackTrack(StackTraceElement[] stackTraceElements, String ignorePackage, int maxDepth) {
        return cropStackTrace(getRealStaceTrace(stackTraceElements, ignorePackage), maxDepth);
    }
}
