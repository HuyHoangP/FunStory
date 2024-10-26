package com.hhp.funstory.view.fragment;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.hhp.funstory.databinding.FragmentDetailStoryBinding;
import com.hhp.funstory.model.Story;
import com.hhp.funstory.view.adapter.DetailStoryAdapter;
import com.hhp.funstory.viewmodel.DetailStoryFrgVM;

import java.util.List;

public class DetailStoryFragment extends BaseFragment<FragmentDetailStoryBinding, DetailStoryFrgVM> {
    public static final String TAG = DetailStoryFragment.class.getName();

    @Override
    protected Class<DetailStoryFrgVM> initViewModel() {
        return DetailStoryFrgVM.class;
    }

    @Override
    protected FragmentDetailStoryBinding initViewBinding() {
        return FragmentDetailStoryBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        binding.actionbar.ivMenu.setVisibility(View.GONE);
        binding.actionbar.ivBack.setVisibility(View.VISIBLE);
        binding.actionbar.trIndex.setVisibility(View.VISIBLE);

        binding.actionbar.ivBack.setOnClickListener(v ->
                callback.showFragment(MenuFragment.TAG, new Object[]{viewModel.getCurrentIndex()}, false)
        );

        List<Story> listStory = (List<Story>) data[0];
        String topicName = ((Story) data[1]).getName();
        Story selectedStory = (Story) data[1];
        int selectedIndex = listStory.indexOf(selectedStory);

        DetailStoryAdapter adapter = new DetailStoryAdapter(context, listStory);
        binding.vpStory.setAdapter(adapter);
        binding.vpStory.setCurrentItem(selectedIndex);
        binding.actionbar.tvTopic.setText(topicName);
        binding.actionbar.tvIndex.setText(String.format("%s/%s", selectedIndex + 1, listStory.size()));

        binding.vpStory.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                binding.actionbar.tvIndex.setText(String.format("%s/%s", position + 1, listStory.size()));
                viewModel.setCurrentIndex(position);
            }
        });

    }
}
