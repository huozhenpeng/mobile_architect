package com.example.hilog.printer.helper;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hicommon.utils.HiUtils;

/**
 * Date: 2021/2/7
 * Author: huozhenpeng
 */
public class HiViewPrinterHelper {
    private FrameLayout mRootView;
    private RecyclerView mRecyclerView;
    private View mFloatingView;
    private View mLogView;
    private boolean isOpen;
    private static final String TAG_LOG_VIEW = "TAG_LOG_VIEW";
    private static final String TAG_FLOATING_VIEW = "TAG_FLOATING_VIEW";
    public HiViewPrinterHelper(FrameLayout rootView, RecyclerView recyclerView) {
        this.mRootView = rootView;
        this.mRecyclerView = recyclerView;
    }

    /**
     * 右侧底部的按钮
     */
    public void showFloatingView() {
        if (mRootView.findViewWithTag(TAG_FLOATING_VIEW) != null) {
            return;
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.END;
        params.bottomMargin = HiUtils.dip2px(mRecyclerView.getContext(), 100);
        genFloatingView();
        mFloatingView.setTag(TAG_FLOATING_VIEW);
        mFloatingView.setBackgroundColor(Color.BLACK);
        mFloatingView.setAlpha(0.6f);
        mRootView.addView(mFloatingView, params);
    }

    private View genFloatingView() {
        if (mFloatingView != null) {
            return mFloatingView;
        }
        TextView textView = new TextView(mRootView.getContext());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    showLogView();
                }

            }
        });
        textView.setText("HiLog");
        return mFloatingView = textView;
    }

    public void showLogView() {
        if (mRootView.findViewWithTag(TAG_LOG_VIEW) != null) {
            return;
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, HiUtils.dip2px(mRecyclerView.getContext(), 180));
        params.gravity = Gravity.BOTTOM;
        genLogView();
        mLogView.setTag(TAG_LOG_VIEW);
        mLogView.setBackgroundColor(Color.BLACK);
        mRootView.addView(mLogView, params);
        isOpen = true;
    }

    private View genLogView() {
        if (mLogView != null) {
            return mLogView;
        }
        FrameLayout logView = new FrameLayout(mRootView.getContext());
        logView.setBackgroundColor(Color.BLACK);
        //添加recyclerview
        FrameLayout.LayoutParams recyclerParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        logView.addView(mRecyclerView, recyclerParams);

        //添加关闭view
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.END;
        TextView closeView = new TextView(mRootView.getContext());
        closeView.setText("close");
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeLogView();
            }
        });
        logView.addView(closeView, params);
        return mLogView = logView;
    }

    protected void closeLogView() {
        isOpen = false;
        mRootView.removeView(mLogView);
    }

}
