package com.example.hitabbottom.bottom;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

import com.example.hicommon.common.domain.TabBean;

/**
 * Date: 2021/2/22
 * Author: huozhenpeng
 * 单个tab所需的数据实体对象
 */
public class HiTabBottomInfo<Color> extends TabBean {
    public enum TabType {
        BITMAP, ICON
    }

    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectedBitmap;
    public String iconFont;

    public String defaultIconName;
    public String selectedIconName;
    public Color defaultColor;
    public Color tintColor;

    public TabType tabType;

    public HiTabBottomInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.tabType = TabType.BITMAP;
    }

    public HiTabBottomInfo(String name, String iconFont, String defaultIconName, String selectedIconName, Color defaultColor, Color tintColor) {
        this.name = name;
        this.iconFont = iconFont;
        this.defaultIconName = defaultIconName;
        this.selectedIconName = selectedIconName;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.ICON;
    }
}
