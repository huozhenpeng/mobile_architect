package com.example.hicommon.common.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Date: 2021/2/23
 * Author: huozhenpeng
 * 其实就是装载Fragment的View
 * 这里主要是想要将Fragment的操作内聚，提供通用的一些api
 */
public class HiFragmentTabView extends FrameLayout {

    private HiTabViewAdapter mAdapter;
    private int mCurrentPosition;
    public HiFragmentTabView(@NonNull Context context) {
        super(context);
    }

    public HiFragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HiFragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAdapter(HiTabViewAdapter hiTabViewAdapter) {
        if (this.mAdapter != null || hiTabViewAdapter == null) {
            return;
        }
        this.mAdapter = hiTabViewAdapter;
        mCurrentPosition = -1;
    }

    /**
     *
     * @param position
     */
    public void setCurrentItem(int position) {
        if (position < 0 || position >= mAdapter.getCount()) {
            return;
        }
        if (mCurrentPosition != position) {
            mCurrentPosition = position;
            mAdapter.instantiateItem(this, position);
        }
    }
}
