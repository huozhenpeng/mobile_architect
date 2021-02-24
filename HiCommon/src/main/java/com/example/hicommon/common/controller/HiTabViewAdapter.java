package com.example.hicommon.common.controller;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hicommon.common.domain.TabBean;

import java.util.List;

/**
 * Date: 2021/2/23
 * Author: huozhenpeng
 */
public class HiTabViewAdapter {

    private List<? extends TabBean> mInfoList;
    private Fragment mCurrentFragment;
    private FragmentManager mFragmentManager;

    public HiTabViewAdapter(FragmentManager fragmentManager, List<? extends TabBean> infoList) {
        this.mFragmentManager = fragmentManager;
        this.mInfoList = infoList;
    }

    public void instantiateItem(View container, int position) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mCurrentFragment != null) {
            fragmentTransaction.hide(mCurrentFragment);
        }
        String name = container.getId() + ":" + position;
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            fragmentTransaction.show(fragment);
        } else {
            fragment = getItem(position);
            if (!fragment.isAdded()) {
                fragmentTransaction.add(container.getId(), fragment, name);
            } else {
                //应该不会出现
                fragmentTransaction.show(fragment);
            }
        }
        mCurrentFragment = fragment;
        fragmentTransaction.commitAllowingStateLoss();
    }

    private Fragment getItem(int position) {
        try {
            return mInfoList.get(position).fragment.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCount() {
        return mInfoList == null ? 0 : mInfoList.size();
    }
}
