package com.example.hitabbottom.bottom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hicommon.utils.HiUtils;
import com.example.hicommon.utils.HiViewUtil;
import com.example.hitabbottom.R;
import com.example.hitabbottom.common.IHiTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2021/2/23
 * Author: huozhenpeng
 */
public class HiTabBottomLayout extends FrameLayout implements IHiTabLayout<HiTabBottom, HiTabBottomInfo<?>> {
    //TabBottom高度
    private static float tabBottomHeight = 50;
    //底部透明度
    private float bottomAlpha = 1f;
    //TabBottom底部线条高度
    private float bottomLineHeight = 0.5f;
    //TabBottom底部线条颜色
    private String bottomLineColor = "#dfe0e1";

    //TabBottom数据
    private List<HiTabBottomInfo<?>> infoList;

    //当前被选中的TabBottomInfo
    private HiTabBottomInfo<?> selectedInfo;

    private static final String TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM";

    private List<OnTabSelectedListener<HiTabBottomInfo<?>>> tabSelectedChangeListeners = new ArrayList<>();

    public HiTabBottomLayout(@NonNull Context context) {
        this(context, null);
    }

    public HiTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HiTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    public HiTabBottom findTab(@NonNull HiTabBottomInfo<?> data) {
        ViewGroup viewGroup = findViewWithTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof HiTabBottom) {
                HiTabBottom hiTabBottom = (HiTabBottom) child;
                if (hiTabBottom.getHiTabBottomInfo() == data) {
                    return hiTabBottom;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<HiTabBottomInfo<?>> listener) {

    }

    @Override
    public void defaultSelected(@NonNull HiTabBottomInfo<?> defaultInfo) {

    }

    @Override
    public void inflateInfo(@NonNull List<HiTabBottomInfo<?>> infoList) {
        if (infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        //第0个元素是中间的内容布局
        for (int i = getChildCount() - 1; i > 0; i--) {
            removeViewAt(i);
        }

        //添加底部导航栏背景色
        addBackGround();

        //清除listener
        tabSelectedChangeListeners.clear();

        //每个tab高度、宽度
        int tabHeight = HiUtils.dip2px(getContext(), tabBottomHeight);
        int tabWidth = HiUtils.getScreenWidth(getContext()) / infoList.size();

        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < infoList.size(); i++) {
            HiTabBottomInfo<?> hiTabBottomInfo = infoList.get(i);
            LayoutParams layoutParams = new LayoutParams(tabWidth, tabHeight);
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.leftMargin = i * tabWidth;

            HiTabBottom hiTabBottom = new HiTabBottom(getContext());
            tabSelectedChangeListeners.add(hiTabBottom);

            hiTabBottom.setHiTabInfo(hiTabBottomInfo);
            frameLayout.addView(hiTabBottom, layoutParams);

            hiTabBottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(hiTabBottomInfo);
                }
            });
        }

        //添加底部线
        addBottomLine();

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM;
        addView(frameLayout, layoutParams);

        //修复内容区域底部padding，让内容能滑动到tab之上
        fixContentView();
    }

    private void onSelected(HiTabBottomInfo<?> nextInfo) {
        //通知所有
        for (OnTabSelectedListener<HiTabBottomInfo<?>> listener : tabSelectedChangeListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
    }


    private void fixContentView() {
        if (!(getChildAt(0) instanceof ViewGroup)) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) getChildAt(0);
        ViewGroup targetView = HiViewUtil.findTypeView(viewGroup, RecyclerView.class);
        if (targetView == null) {
            targetView = HiViewUtil.findTypeView(viewGroup, ScrollView.class);
        }
        if (targetView == null) {
            targetView = HiViewUtil.findTypeView(viewGroup, AbsListView.class);
        }
        if (targetView == null) {
            targetView = HiViewUtil.findTypeView(viewGroup, NestedScrollView.class);
        }

        if (targetView != null) {
            targetView.setPadding(0, 0, 0, HiUtils.dip2px(getContext(), tabBottomHeight));
            //允许子view超出显示
            targetView.setClipChildren(false);
        }

    }

    private void addBottomLine() {
        View bottomLine = new View(getContext());
        bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor));
        bottomLine.setAlpha(bottomAlpha);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, HiUtils.dip2px(getContext(), bottomLineHeight));
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.bottomMargin = HiUtils.dip2px(getContext(), tabBottomHeight - bottomLineHeight);
        addView(bottomLine, layoutParams);
    }

    private void addBackGround() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.hi_bottom_layout_bg, null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, HiUtils.dip2px(getContext(), tabBottomHeight));
        params.gravity = Gravity.BOTTOM;
        addView(view, params);
        view.setAlpha(bottomAlpha);
    }
}
