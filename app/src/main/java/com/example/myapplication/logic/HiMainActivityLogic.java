package com.example.myapplication.logic;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import com.example.hicommon.common.controller.HiFragmentTabView;
import com.example.hicommon.common.controller.HiTabViewAdapter;
import com.example.hitabbottom.bottom.HiTabBottomInfo;
import com.example.hitabbottom.bottom.HiTabBottomLayout;
import com.example.hitabbottom.common.IHiTabLayout;
import com.example.myapplication.R;
import com.example.myapplication.fragment.CategoryFragment;
import com.example.myapplication.fragment.FavoriteFragment;
import com.example.myapplication.fragment.HomePageFragment;
import com.example.myapplication.fragment.ProfileFragment;
import com.example.myapplication.fragment.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2021/2/23
 * Author: huozhenpeng
 */
public class HiMainActivityLogic {
    private int currentIndex;
    /**
     * Activity需要提供的能力
     */
    public interface ActivityProvider {
        <T extends View> T findViewById(@IdRes int id);

        Resources getResources();

        FragmentManager getSupportFragmentManager();

        String getString(@StringRes int resId);
    }
    private ActivityProvider activityProvider;
    private HiTabBottomLayout hiTabBottomLayout;
    private List<HiTabBottomInfo<?>> infoList;
    private HiFragmentTabView fragmentTabView;
    private final static String SAVED_CURRENT_ID = "SAVED_CURRENT_ID";
    public HiMainActivityLogic(ActivityProvider activityProvider, @Nullable Bundle saveInstanceState) {
        this.activityProvider = activityProvider;
        //fix 不保留活动导致的Fragment重叠问题
        //todo 感觉没有从跟不上解决问题，这种方式是自己和自己重叠
        if (saveInstanceState != null) {
            currentIndex = saveInstanceState.getInt(SAVED_CURRENT_ID);
        }
        initTabBottom();
    }
    public void onSaveInstanceState(Bundle outState) {
        //保存当前的fragment索引
        outState.putInt(SAVED_CURRENT_ID, currentIndex);
    }

    private void initTabBottom() {

        hiTabBottomLayout = activityProvider.findViewById(R.id.hi_tab_bottom_layout);
        hiTabBottomLayout.setTabAlpha(0.85f);
        infoList = new ArrayList<>();
        int defaultColor = activityProvider.getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = activityProvider.getResources().getColor(R.color.tabBottomTintColor);
        HiTabBottomInfo<Integer> homeInfo = new HiTabBottomInfo<>(
                "首页",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_home),
                null,
                defaultColor,
                tintColor
        );
        homeInfo.fragment = HomePageFragment.class;
        HiTabBottomInfo<Integer> infoFavorite = new HiTabBottomInfo<>(
                "收藏",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_favorite),
                null,
                defaultColor,
                tintColor
        );
        infoFavorite.fragment = FavoriteFragment.class;
        HiTabBottomInfo<Integer> infoCategory = new HiTabBottomInfo<>(
                "分类",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_category),
                null,
                defaultColor,
                tintColor
        );
        infoCategory.fragment = CategoryFragment.class;
        HiTabBottomInfo<Integer> infoRecommend = new HiTabBottomInfo<>(
                "推荐",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_recommend),
                null,
                defaultColor,
                tintColor
        );
        infoRecommend.fragment = RecommendFragment.class;
        HiTabBottomInfo<Integer> infoProfile = new HiTabBottomInfo<>(
                "我的",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_profile),
                null,
                defaultColor,
                tintColor
        );
        infoProfile.fragment = ProfileFragment.class;
        infoList.add(homeInfo);
        infoList.add(infoFavorite);
        infoList.add(infoCategory);
        infoList.add(infoRecommend);
        infoList.add(infoProfile);
        hiTabBottomLayout.inflateInfo(infoList);

        initFragmentTabView();

        hiTabBottomLayout.addTabSelectedChangeListener(new IHiTabLayout.OnTabSelectedListener<HiTabBottomInfo<?>>() {
            @Override
            public void onTabSelectedChange(int index, @NonNull HiTabBottomInfo<?> prevInfo, @NonNull HiTabBottomInfo<?> nextInfo) {
                fragmentTabView.setCurrentItem(index);
                HiMainActivityLogic.this.currentIndex = index;
            }
        });
        hiTabBottomLayout.defaultSelected(infoList.get(currentIndex));
    }

    private void initFragmentTabView() {
        HiTabViewAdapter hiTabViewAdapter = new HiTabViewAdapter(activityProvider.getSupportFragmentManager(), infoList);
        fragmentTabView = activityProvider.findViewById(R.id.hi_fragment_tab_view);
        fragmentTabView.setAdapter(hiTabViewAdapter);
    }
}
