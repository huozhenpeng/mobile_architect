package com.example.hitabtop.common;

import androidx.annotation.NonNull;

/**
 * Date: 2021/2/22
 * Author: huozhenpeng
 */
public interface IHiTab<D> extends IHiTabLayout.OnTabSelectedListener<D> {
    void setHiTabInfo(@NonNull D data);
}
