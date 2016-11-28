
/*
 * Date: 14-7-17
 * Project: Access-Control-V2
 */
package com.csdn.kline.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: msdx (645079761@qq.com)
 * Time: 14-7-17 下午5:46
 */
public abstract class BaseFragment extends Fragment  implements View.OnClickListener{
    protected boolean isVisible;

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    /**
     * 事件的点击
     *
     * @param v
     */
    public abstract void widgetClick(View v);
    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  initView(inflater);


        return view;
    }

    public abstract View initView(LayoutInflater inflater);
}