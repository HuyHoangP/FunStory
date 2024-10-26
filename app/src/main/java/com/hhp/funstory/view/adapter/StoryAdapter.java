package com.hhp.funstory.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.hhp.funstory.R;
import com.hhp.funstory.model.Story;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryHolder> {
    private final Context context;
    private final List<Story> listStory;

    private Story story;
    private final MutableLiveData<Story> storyLD = new MutableLiveData<>();

    public StoryAdapter(Context context, List<Story> listStory) {
        this.context = context;
        this.listStory = listStory;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public Story getStory() {
        return story;
    }

    public List<Story> getListStory() {
        return listStory;
    }

    public MutableLiveData<Story> getStoryLD() {
        return storyLD;
    }

    @NonNull
    @Override
    public StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_story, parent, false);
        return new StoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryHolder holder, int position) {
        Story story = listStory.get(position);
        holder.tvStory.setText(story.getName());
        holder.tvStory.setTag(story);
        holder.lnStoryName.setBackgroundResource(story.isSelected() ? R.color.gray_light : R.color.white);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return listStory.size();
    }

    public class StoryHolder extends RecyclerView.ViewHolder {
        TextView tvStory;
        LinearLayout lnStoryName;

        public StoryHolder(@NonNull View itemView) {
            super(itemView);
            tvStory = itemView.findViewById(R.id.tv_story);
            lnStoryName = itemView.findViewById(R.id.ln_story_name);
            itemView.setOnClickListener(v -> {
                v.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
                story = (Story) tvStory.getTag();
                storyLD.postValue(story);
            });
            setSelectedStory();
        }

        private void setSelectedStory() {
            if(story != null){
                story.setSelected(true);
            }
            if (storyLD.getValue() != null) {
                storyLD.getValue().setSelected(false);
            }

//            notifyItemRangeChanged(0, listStory.size());
        }
    }
}
