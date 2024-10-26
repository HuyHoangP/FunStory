package com.hhp.funstory.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.hhp.funstory.R;
import com.hhp.funstory.model.Story;

import java.util.List;

public class DetailStoryAdapter extends PagerAdapter {
    private Context context;
    private List<Story> listStory;

    public DetailStoryAdapter(Context context, List<Story> listStory) {
        this.context = context;
        this.listStory = listStory;
    }

    @Override
    public int getCount() {
        return listStory.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup viewPager, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_story, viewPager, false);
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvContent = view.findViewById(R.id.tv_content);

        Story data = listStory.get(position);
        tvName.setText(data.getName());
        tvContent.setText(data.getContent());

        viewPager.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup viewPager, int position, @NonNull Object object) {
        viewPager.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View oldView, @NonNull Object newView) {
        return oldView.equals(newView);
    }
}
