package com.hhp.funstory.view.activity;

import android.view.View;

import com.hhp.funstory.databinding.ActivityMainBinding;
import com.hhp.funstory.view.fragment.MainFragment;
import com.hhp.funstory.view.fragment.MenuFragment;
import com.hhp.funstory.view.fragment.SplashFragment;
import com.hhp.funstory.viewmodel.MainActVM;

public class MainActivity extends BaseAct<ActivityMainBinding, MainActVM> {


    @Override
    public void callBack(String key, Object data) {

    }

    @Override
    protected Class<MainActVM> initViewModel() {
        return MainActVM.class;
    }

    @Override
    protected ActivityMainBinding initViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        showFragment(SplashFragment.TAG, null, false);
    }

    @Override
    protected void clickView(View v) {

    }
}