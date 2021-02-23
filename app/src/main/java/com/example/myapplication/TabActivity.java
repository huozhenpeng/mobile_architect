package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hicommon.utils.HiUtils;
import com.example.hitabbottom.bottom.HiTabBottom;
import com.example.hitabbottom.bottom.HiTabBottomInfo;
import com.example.hitabbottom.bottom.HiTabBottomLayout;
import com.example.hitabbottom.common.IHiTabLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Date: 2021/2/23
 * Author: huozhenpeng
 */
public class TabActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        List<HiTabBottomInfo<?>> bottomInfoList = new ArrayList<>();
        HiTabBottom tabItem = findViewById(R.id.tab_bottom);
        HiTabBottomInfo<String> homeInfo = new HiTabBottomInfo<String>(
                "首页",
                "fonts/iconfont.ttf",
                getString(R.string.if_home),
                null,
                "#ff656667",
                "#ffd44949"
        );
        tabItem.setHiTabInfo(homeInfo);

        HiTabBottomInfo<String> infoRecommend = new HiTabBottomInfo<String>(
                "收藏",
                "fonts/iconfont.ttf",
                getString(R.string.if_favorite),
                null,
                "#ff656667",
                "#ffd44949"
        );

//        HiTabBottomInfo<String> infoCategory = HiTabBottomInfo<String>(
//            "分类",
//            "fonts/iconfont.ttf",
//            getString(R.string.if_category),
//            null,
//            "#ff656667",
//            "#ffd44949"
//        )
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fire, null);

        HiTabBottomInfo<String> infoCategory = new HiTabBottomInfo<String>(
                "分类",
                bitmap,
                bitmap
        );
        HiTabBottomInfo<String> infoChat = new HiTabBottomInfo<String>(
                "推荐",
                "fonts/iconfont.ttf",
                getString(R.string.if_recommend),
                null,
                "#ff656667",
                "#ffd44949"
        );
        HiTabBottomInfo<String> infoProfile = new HiTabBottomInfo<String>(
                "我的",
                "fonts/iconfont.ttf",
                getString(R.string.if_profile),
                null,
                "#ff656667",
                "#ffd44949"
        );
        bottomInfoList.add(homeInfo);
        bottomInfoList.add(infoRecommend);
        bottomInfoList.add(infoCategory);
        bottomInfoList.add(infoChat);
        bottomInfoList.add(infoProfile);
        HiTabBottomLayout hiTabBottomLayout = findViewById(R.id.hi_tab_layout);
        hiTabBottomLayout.inflateInfo(bottomInfoList);

        hiTabBottomLayout.addTabSelectedChangeListener(new IHiTabLayout.OnTabSelectedListener<HiTabBottomInfo<?>>() {
            @Override
            public void onTabSelectedChange(int index, @NonNull HiTabBottomInfo<?> prevInfo, @NonNull HiTabBottomInfo<?> nextInfo) {
                Toast.makeText(TabActivity.this, nextInfo.name, Toast.LENGTH_SHORT).show();
            }
        });
        hiTabBottomLayout.defaultSelected(homeInfo);
        //改变某个tab的高度
        HiTabBottom tabBottom = hiTabBottomLayout.findTab(bottomInfoList.get(2));
        tabBottom.resetTabHeight(HiUtils.dip2px(getApplicationContext(), 66));
    }
}
