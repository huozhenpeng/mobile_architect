package com.example.hitabtop.top;

import android.graphics.Bitmap;

import com.example.hicommon.common.domain.TabBean;

/**
 * Date: 2021/2/22
 * Author: huozhenpeng
 * 单个tab所需的数据实体对象
 */
public class HiTabTopInfo<Color> extends TabBean {
    public enum TabType {
        BITMAP, TXT
    }

    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectedBitmap;

    public Color defaultColor;
    public Color tintColor;

    public TabType tabType;

    public HiTabTopInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.tabType = TabType.BITMAP;
    }

    public HiTabTopInfo(String name, Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.TXT;
    }
}
