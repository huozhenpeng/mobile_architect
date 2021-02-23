package com.example.hitabbottom.common;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/**
 * Date: 2021/2/22
 * Author: huozhenpeng
 */
public interface IHiTab<D> extends IHiTabLayout.OnTabSelectedListener<D> {
    void setHiTabInfo(@NonNull D data);
    void resetTabHeight(@Px int height);
}
