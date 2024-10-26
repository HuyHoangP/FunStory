package com.hhp.funstory.view.fragment;

import android.os.Handler;
import android.view.View;

import com.hhp.funstory.databinding.FragmentSplashBinding;
import com.hhp.funstory.viewmodel.CommonVM;

public class SplashFragment extends BaseFragment<FragmentSplashBinding, CommonVM> {
    public static final String TAG = SplashFragment.class.getName();

    @Override
    protected Class<CommonVM> initViewModel() {
        return CommonVM.class;
    };

    @Override
    protected FragmentSplashBinding initViewBinding() {
        return FragmentSplashBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        new Handler().postDelayed(() -> {
            callback.showFragment(MenuFragment.TAG , null, false);
        }, 2000);
    }

    @Override
    protected void clickView(View v) {

    }
}
