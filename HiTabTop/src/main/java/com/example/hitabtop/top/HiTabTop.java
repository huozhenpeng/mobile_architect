package com.example.hitabtop.top;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.hitabtop.R;
import com.example.hitabtop.common.IHiTab;

/**
 * Date: 2021/2/24
 * Author: huozhenpeng
 */
public class HiTabTop extends RelativeLayout implements IHiTab<HiTabTopInfo<?>> {
    private HiTabTopInfo<?> hiTabTopInfo;
    private ImageView tabImageView;
    private TextView tabNameView;
    private View indicator;
    public HiTabTop(Context context) {
        this(context, null);
    }

    public HiTabTop(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HiTabTop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.hi_tab_top, this);
        tabImageView = findViewById(R.id.iv_image);
        tabNameView = findViewById(R.id.tv_name);
        indicator = findViewById(R.id.tab_top_indicator);
    }

    @Override
    public void setHiTabInfo(@NonNull HiTabTopInfo<?> data) {
        this.hiTabTopInfo = data;
        inflateInfo(false, true);
    }

    @Override
    public void onTabSelectedChange(int index, @NonNull HiTabTopInfo<?> prevInfo, @NonNull HiTabTopInfo<?> nextInfo) {
        //当前tab未被选择并且将要选择的tab也不是当前tab  或者  重复点击了
        if ((prevInfo != hiTabTopInfo && nextInfo != hiTabTopInfo) || prevInfo == nextInfo) {
            return;
        }
        //反选
        if (prevInfo == hiTabTopInfo) {
            inflateInfo(false, false);
        } else {
            //选中
            inflateInfo(true, false);
        }
    }

    private void inflateInfo(boolean selected, boolean init) {
        if (hiTabTopInfo.tabType == HiTabTopInfo.TabType.TXT) {
            if (init) {
                tabImageView.setVisibility(View.GONE);
                tabNameView.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(hiTabTopInfo.name)) {
                    tabNameView.setText(hiTabTopInfo.name);
                }
            }

            if (selected) {
                indicator.setVisibility(View.VISIBLE);
                tabNameView.setTextColor(getTextColor(hiTabTopInfo.tintColor));
            } else {
                indicator.setVisibility(View.INVISIBLE);
                tabNameView.setTextColor(getTextColor(hiTabTopInfo.defaultColor));
            }
        } else {
            if (init) {
                tabImageView.setVisibility(View.VISIBLE);
                tabNameView.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(hiTabTopInfo.name)) {
                    tabNameView.setText(hiTabTopInfo.name);
                }
            }
            if (selected) {
                indicator.setVisibility(View.VISIBLE);
                tabImageView.setImageBitmap(hiTabTopInfo.selectedBitmap);
            } else {
                indicator.setVisibility(View.INVISIBLE);
                tabImageView.setImageBitmap(hiTabTopInfo.defaultBitmap);
            }
        }
    }

    private int getTextColor(Object tintColor) {
        if (tintColor instanceof String) {
            return Color.parseColor((String) tintColor);
        } else {
            return (int) tintColor;
        }
    }

    public HiTabTopInfo<?> getHiTabInfo() {
        return hiTabTopInfo;
    }
}
