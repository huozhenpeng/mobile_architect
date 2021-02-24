package com.example.hitabtop.top;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.hicommon.utils.HiUtils;
import com.example.hitabtop.common.IHiTabLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Date: 2021/2/24
 * Author: huozhenpeng
 */
public class HiTabTopLayout extends HorizontalScrollView implements IHiTabLayout<HiTabTop, HiTabTopInfo<?>> {
    //自动滚动的数量，一般是2个
    //点击某个tab时，如果后面的tab没有展示，则自动滑动展示
    private int autoNum = 2;
    private List<HiTabTopInfo<?>> infoList;
    private int tabWidth;
    private LinearLayout rootView;
    private HiTabTopInfo<?> selectedInfo;
    private List<OnTabSelectedListener<HiTabTopInfo<?>>> tabSelectedChangeListeners = new ArrayList<>();
    public HiTabTopLayout(Context context) {
        this(context, null);
    }

    public HiTabTopLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HiTabTopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //添加一个LinearLayout用来装载Tab
        addRootView();
        // 隐藏水平滚动条
        setVerticalScrollBarEnabled(false);
    }
    private void addRootView() {
        rootView = new LinearLayout(getContext());
        rootView.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(rootView, layoutParams);
    }

    @Override
    public HiTabTop findTab(@NonNull HiTabTopInfo<?> data) {
        for (int i = 0; i < rootView.getChildCount(); i++) {
            View child = rootView.getChildAt(i);
            if (child instanceof HiTabTop) {
                HiTabTop tab = (HiTabTop) child;
                if (tab.getHiTabInfo() == data) {
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<HiTabTopInfo<?>> listener) {
        tabSelectedChangeListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NonNull HiTabTopInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    @Override
    public void inflateInfo(@NonNull List<HiTabTopInfo<?>> infoList) {
        if (infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        selectedInfo = null;

        Iterator<OnTabSelectedListener<HiTabTopInfo<?>>> iterator = tabSelectedChangeListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof HiTabTop) {
                iterator.remove();
            }
        }
        for (int i = 0; i < infoList.size(); i++) {
            final HiTabTopInfo<?> info = infoList.get(i);
            HiTabTop tab = new HiTabTop(getContext());
            tabSelectedChangeListeners.add(tab);
            tab.setHiTabInfo(info);
            rootView.addView(tab);
            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(info);
                }
            });
        }
    }

    private void onSelected(HiTabTopInfo<?> nextInfo) {
        for (OnTabSelectedListener<HiTabTopInfo<?>> listener : tabSelectedChangeListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
        autoScroll(nextInfo);
    }
    /**
     * 获取单个tab需要滑动的距离
     * @param index tab的index
     * @param clickRight 点击屏幕右侧，向左滑动
     * getLocalVisibleRect:
     *                完全可见时: rect是自己的宽高
     *                部分可见时: rect是可见部分的宽高
     *                完全不可见时: rect是在屏幕的位置 同getGlobalVisibleRect
     */
    private int getScrollWidth(int index, boolean clickRight) {
        HiTabTop target = findTab(infoList.get(index));
        if (target == null) {
            return 0;
        }
        Rect rect = new Rect();
        target.getLocalVisibleRect(rect);
        if (clickRight) {
            if (rect.right > tabWidth) {
                return tabWidth;
            } else {
                return tabWidth - rect.right;
            }
        } else {
            if (rect.left <= -tabWidth) {
                return tabWidth;
            } else {
                return rect.left;
            }
        }
    }

    /**
     * 获取本次点击总共需要滑动的距离
     * 每个tab需要滑动的距离加起来
     * @param index
     * @param range
     * @return
     */
    private int rangeScrollWidth(int index, int range) {
        int scrollWidth = 0;
        for (int i = 0; i <= Math.abs(range); i++) {
            //下一个位置
            int next;
            if (range < 0) {
                //向左滑动下一个位置索引
                next = range + i + index;
            } else {
                //向右滑动下一个位置索引
                next = range - i + index;
            }
            if (next >= 0 && next < infoList.size()) {
                if (range < 0) {
                    //计算向左滑动可滚动的距离
                    scrollWidth -= getScrollWidth(next, false);
                } else {
                    //计算向右滑动可滚动的距离
                    scrollWidth += getScrollWidth(next, true);
                }
            }
        }
        return scrollWidth;
    }

    /**
     * 自动滚动
     * @param nextInfo
     */
    private void autoScroll(HiTabTopInfo<?> nextInfo) {
        HiTabTop hiTabTop = findTab(nextInfo);
        if (hiTabTop == null) {
            return;
        }
        int index = infoList.indexOf(nextInfo);
        //获取当前tab在父容器中的位置
        float x = hiTabTop.getX();
        if (tabWidth == 0) {
            tabWidth = hiTabTop.getWidth();
        }

        int scrollWidth;
        if (x + tabWidth / 2f > getWidth() / 2f) {
            scrollWidth = rangeScrollWidth(index, autoNum);
        } else {
            scrollWidth = rangeScrollWidth(index, -autoNum);
        }
        //x轴滚动
        scrollTo(getScrollX() + scrollWidth, 0);
    }
}
