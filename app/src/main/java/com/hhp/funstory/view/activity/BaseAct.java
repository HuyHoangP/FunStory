package com.hhp.funstory.view.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.hhp.funstory.R;
import com.hhp.funstory.view.OnMainCallback;
import com.hhp.funstory.view.fragment.BaseFragment;

import java.lang.reflect.Constructor;

public abstract class BaseAct <T extends ViewBinding, V extends ViewModel> extends AppCompatActivity implements View.OnClickListener, OnMainCallback {
    protected T binding;
    protected V viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = initViewBinding();
        viewModel = new ViewModelProvider(this).get(initViewModel());
        setContentView(binding.getRoot());
        initView();
    }

    protected abstract Class<V> initViewModel();

    protected abstract T initViewBinding();
    protected abstract void initView();

    @Override
    public final void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_in));
        clickView(v);
    }

    protected void clickView(View v) {

    }

    @Override
    public void showFragment(String tag, Object[] data, Boolean isBack) {
        try{
            Class<?> clazz = Class.forName(tag);
            Constructor<?> constructor = clazz.getConstructor();
            BaseFragment<?,?> fragment = (BaseFragment<?,?>) constructor.newInstance();

            fragment.setCallback(this);
            fragment.setData(data);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fr_id, fragment, tag);
            if(isBack) fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
