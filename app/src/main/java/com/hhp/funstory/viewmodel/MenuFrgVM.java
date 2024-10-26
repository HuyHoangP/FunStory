package com.hhp.funstory.viewmodel;

import android.content.res.AssetManager;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hhp.funstory.CommonUtils;
import com.hhp.funstory.model.Story;
import com.hhp.funstory.view.fragment.MenuFragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MenuFrgVM extends ViewModel {

    private static final String TAG = MenuFrgVM.class.getName();
    private final MutableLiveData<String> name = new MutableLiveData<>();
    private final MutableLiveData<String> content = new MutableLiveData<>();
    private String topicName = "Con gái";
    private ArrayList<Story> listStory;

    public MutableLiveData<String> getContent() {
        return content;
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }

    public ArrayList<Story> getListStory() {
        return listStory;
    }

    public void readStoryFiles(AssetManager assetManager) {
        listStory = new ArrayList<>();
        try{
            InputStream in = assetManager.open("story/"+ topicName + ".txt");
            InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8);

            BufferedReader reader = new BufferedReader(isr);
            String name = null;
            StringBuilder content = new StringBuilder();
            String line = reader.readLine();
            while(line != null){
                if(name == null){
                    name = line;
                } else if(line.contains("','0');")){
                    Story story = new Story(name, content.toString());
                    listStory.add(story);
                    name = null;
                    content = new StringBuilder();
                }  else if (!line.isEmpty()) {
                    content.append(line).append("\n");
                }
                line = reader.readLine();
            }
            reader.close();
            isr.close();
            in.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
