package com.hhp.funstory.view.fragment;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;

import com.hhp.funstory.R;
import com.hhp.funstory.databinding.FragmentMainBinding;
import com.hhp.funstory.model.Story;
import com.hhp.funstory.viewmodel.MenuFrgVM;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainFragment extends BaseFragment<FragmentMainBinding, MenuFrgVM> {
    public static final String TAG = MainFragment.class.getName();

    @Override
    protected Class<MenuFrgVM> initViewModel() {
        return MenuFrgVM.class;
    };

    @Override
    protected FragmentMainBinding initViewBinding() {
        return FragmentMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initPhotoTopic();
        readStoryFiles();
        binding.btSave1.setOnClickListener(v -> saveImageToDataStorage());
        binding.includeActionbar.ivMenu.setOnClickListener(v -> openMenu());
    }

    private void openMenu() {
        binding.drawer.openDrawer(GravityCompat.START);
    }

    private void saveImageToDataStorage() {
        try{
            AssetManager assetManager = context.getAssets();
            InputStream in = assetManager.open("photo/Con gái.png");
            String dataPath = Environment.getDataDirectory().getPath() + "/data/" + context.getPackageName();
            String photoPath = dataPath + "/image.png";
            FileOutputStream out = new FileOutputStream(photoPath);

            byte[] buff = new byte[1024];
            int length = in.read(buff);
            while (length>0){
                out.write(buff, 0 ,length);
                length = in.read(buff);
            }
            out.close();
            in.close();
            Toast.makeText(context, "YEAHHHHHHHH", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void readStoryFiles() {
        AssetManager assetManager = context.getAssets();
        ArrayList<Story> listStory = new ArrayList<>();
        try{
            InputStream in = assetManager.open("story/Con gái.txt");
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

        binding.tvName.setText(listStory.get(0).getName());
        binding.tvContent.setText(listStory.get(0).getContent());
    }

    private void initPhotoTopic() {
        AssetManager assetManager = context.getAssets();
        try {
            String[] listPhotoPath = assetManager.list("photo/");
            binding.includeDrawer.lnMenu.removeAllViews();

            for(String photoPath: listPhotoPath){
                View itemView = LayoutInflater.from(context).inflate(R.layout.item_topic, null);
                TextView tvName = itemView.findViewById(R.id.tv_topic);
                ImageView ivPhoto = itemView.findViewById(R.id.iv_topic);

                InputStream in = assetManager.open("photo/" + photoPath);
                Bitmap img = BitmapFactory.decodeStream(in);

                tvName.setText(photoPath.replace(".png", ""));
                ivPhoto.setImageBitmap(img);

                binding.includeDrawer.lnMenu.addView(itemView);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void clickView(View v) {

    }
}
