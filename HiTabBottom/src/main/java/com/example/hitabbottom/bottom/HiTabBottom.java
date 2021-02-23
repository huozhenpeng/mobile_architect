package com.example.hitabbottom.bottom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.hitabbottom.R;
import com.example.hitabbottom.common.IHiTab;

/**
 * Date: 2021/2/22
 * Author: huozhenpeng
 */
public class HiTabBottom extends RelativeLayout implements IHiTab<HiTabBottomInfo<?>> {

    private HiTabBottomInfo hiTabBottomInfo;
    private ImageView tabImageView;
    private TextView tabIconView;
    private TextView tabNameView;

    public HiTabBottom(Context context) {
        this(context, null);
    }

    public HiTabBottom(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HiTabBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.hi_tab_bottom, this);
        tabImageView = findViewById(R.id.iv_image);
        tabIconView = findViewById(R.id.tv_icon);
        tabNameView = findViewById(R.id.tv_name);
    }

    public HiTabBottomInfo<?> getHiTabBottomInfo() {
        return hiTabBottomInfo;
    }

    @Override
    public void setHiTabInfo(@NonNull HiTabBottomInfo<?> data) {
        this.hiTabBottomInfo = data;
        inflateInfo(false, true);
    }

    private void inflateInfo(boolean selected, boolean init) {
        if (hiTabBottomInfo.tabType == HiTabBottomInfo.TabType.ICON) {
          if (init) {
              tabImageView.setVisibility(View.GONE);
              tabIconView.setVisibility(View.VISIBLE);
              Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), hiTabBottomInfo.iconFont);
              tabIconView.setTypeface(typeface);
              if (!TextUtils.isEmpty(hiTabBottomInfo.name)) {
                  tabNameView.setText(hiTabBottomInfo.name);
              }
          }

          if (selected) {
              tabIconView.setText(TextUtils.isEmpty(hiTabBottomInfo.selectedIconName) ? hiTabBottomInfo.defaultIconName : hiTabBottomInfo.selectedIconName);
              tabIconView.setTextColor(getTextColor(hiTabBottomInfo.tintColor));
              tabNameView.setTextColor(getTextColor(hiTabBottomInfo.tintColor));
          } else {
              tabIconView.setText(hiTabBottomInfo.defaultIconName);
              tabIconView.setTextColor(getTextColor(hiTabBottomInfo.defaultColor));
              tabNameView.setTextColor(getTextColor(hiTabBottomInfo.defaultColor));
          }
        } else {
            if (init) {
                tabImageView.setVisibility(View.VISIBLE);
                tabNameView.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(hiTabBottomInfo.name)) {
                    tabNameView.setText(hiTabBottomInfo.name);
                }
            }
            if (selected) {
                tabImageView.setImageBitmap(hiTabBottomInfo.selectedBitmap);
            } else {
                tabImageView.setImageBitmap(hiTabBottomInfo.defaultBitmap);
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

    @Override
    public void resetTabHeight(int height) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = height;
        setLayoutParams(layoutParams);

        //突出显示，隐藏底部name
        tabNameView.setVisibility(View.GONE);
    }

    /**
     *
     * @param index
     * @param prevInfo 点击之前上一个被选中的tab
     * @param nextInfo 点击之后下一个被选中的tab
     */
    @Override
    public void onTabSelectedChange(int index, @NonNull HiTabBottomInfo<?> prevInfo, @NonNull HiTabBottomInfo<?> nextInfo) {
        //当前tab未被选择并且将要选择的tab也不是当前tab  或者  重复点击了
        if ((prevInfo != hiTabBottomInfo && nextInfo != hiTabBottomInfo) || prevInfo == nextInfo) {
            return;
        }
        //反选
        if (prevInfo == hiTabBottomInfo) {
            inflateInfo(false, false);
        } else {
            //选中
            inflateInfo(true, false);
        }

    }
}
