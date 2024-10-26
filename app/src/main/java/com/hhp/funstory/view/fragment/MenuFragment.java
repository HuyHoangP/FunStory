package com.hhp.funstory.view.fragment;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;

import com.hhp.funstory.CommonUtils;
import com.hhp.funstory.R;
import com.hhp.funstory.databinding.FragmentMenuBinding;
import com.hhp.funstory.model.Story;
import com.hhp.funstory.view.adapter.StoryAdapter;
import com.hhp.funstory.viewmodel.MenuFrgVM;

import java.io.InputStream;

public class MenuFragment extends BaseFragment<FragmentMenuBinding, MenuFrgVM> {
    public static final String TAG = MenuFragment.class.getName();
    public static final String KEY_TOPIC_NAME = "KEY_TOPIC_NAME";
    private StoryAdapter adapter;

    @Override
    protected Class<MenuFrgVM> initViewModel() {
        return MenuFrgVM.class;
    }

    @Override
    protected FragmentMenuBinding initViewBinding() {
        return FragmentMenuBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initPhotoTopic();
        initStory();
        binding.actionbar.ivMenu.setOnClickListener(v -> openMenu());
        binding.actionbar.tvTopic.setText(viewModel.getTopicName());
        showListStory();
        showLastReadStory();
//        binding.rvListStory.scrollToPosition();
    }

    private void showLastReadStory() {
        if(data!= null){
            int currentIndex = (int) data[0];
            Story currentStory = adapter.getListStory().get(currentIndex);
            adapter.setStory(currentStory);
            Toast.makeText(context, adapter.getStory().getName(), Toast.LENGTH_SHORT).show();
        }
    }

    private void openMenu() {
        binding.drawer.openDrawer(GravityCompat.START);
    }

    private void initStory() {
        String lastTopic = CommonUtils.getInstance().getPref(KEY_TOPIC_NAME);
       if(lastTopic != null){
            viewModel.setTopicName(lastTopic);
        }
        viewModel.readStoryFiles(context.getAssets());

    }

    private void initPhotoTopic() {
        AssetManager assetManager = context.getAssets();
        try {
            String[] listPhotoPath = assetManager.list("photo/");
            binding.menu.lnMenu.removeAllViews();

            for(String photoPath: listPhotoPath){
                View itemView = LayoutInflater.from(context).inflate(R.layout.item_topic, null);
                TextView tvName = itemView.findViewById(R.id.tv_topic);
                ImageView ivPhoto = itemView.findViewById(R.id.iv_topic);

                InputStream in = assetManager.open("photo/" + photoPath);
                Bitmap img = BitmapFactory.decodeStream(in);

                tvName.setText(photoPath.replace(".png", ""));
                ivPhoto.setImageBitmap(img);

                itemView.setTag(tvName.getText());
                itemView.setOnClickListener(this::openTopic);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
                params.topMargin = 10;

                binding.menu.lnMenu.addView(itemView, params);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void openTopic(View v) {
        binding.drawer.closeDrawers();
        viewModel.setTopicName(v.getTag().toString());
        binding.actionbar.tvTopic.setText(viewModel.getTopicName());
        viewModel.readStoryFiles(context.getAssets());
        showListStory();
        saveToDataStorage();
    }

    private void saveToDataStorage() {
        CommonUtils.getInstance().savePref(KEY_TOPIC_NAME, viewModel.getTopicName());
    }

    private void showListStory() {
        adapter = new StoryAdapter(context, viewModel.getListStory());
        adapter.getStoryLD().observe(this, story ->{
            if(story == null) return;
            openStoryDetail(story);
        });
        binding.rvListStory.setAdapter(adapter);
    }

    private void openStoryDetail(Story story) {
        callback.showFragment(DetailStoryFragment.TAG,
                new Object[] {viewModel.getListStory(), story}, true);
    }

    @Override
    protected void clickView(View v) {

    }
}
