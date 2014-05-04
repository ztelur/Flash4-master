package com.nju.Flash.time_capsule.timeLine;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;
import com.nju.Flash.time_capsule.buttons.*;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.nju.Flash.R;
import com.nju.Flash.time_capsule.IOHelper;
import com.nju.Flash.time_capsule.content.Photo;
import com.nju.Flash.time_capsule.exception.EmptyFileException;
import com.nju.Flash.time_capsule.exception.FileReadFailException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * Created by randy on 14-4-28.
 */
public class TimeFragment extends Fragment {
    private String filePath=null;
    private HashMap<String,String> fileList=null;

    public TimeFragment(String filePath) {
        if(!filePath.isEmpty()) {
            this.filePath = filePath;
            fileList = IOHelper.readFileList(filePath);
        }else{
            throw new NullPointerException("TimeFragment contruction filepath is null");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.timefragment_layout, container, false);
         timeShow =(TextView)view.findViewById(R.id.timeFragment_time);
         soundButton=(ImageButton)view.findViewById(R.id.timeFragment_sound);
         textShow=(TextView)view.findViewById(R.id.timeFragment_text);
         imageShow=(ImageView)view.findViewById(R.id.timeFragment_image);
        initComponet();
        return view;
    }
    private void initComponet() {
        //initFileRead();
        initSound();
        initImage();
        initText();
        initTime();
    }
    private void initFileRead() {

    }
    private ImageView imageShow =null;

    /**
     * 从fileList中获得photo键值,读取图片
     */
    private void initImage() {
        Resources res=getResources();

        if(fileList.containsKey("photo")) {
            String photoName = fileList.get("photo");
            Bitmap bitmap=thumb(filePath+photoName);
            System.out.print("============="+bitmap.getHeight()+bitmap.getWidth());
            File photo=new File(filePath+photoName);
//            Uri photoUri=Uri.fromFile(photo);
//            initializePhoto(photoUri);
            imageShow.setImageBitmap(bitmap);
        }else {   //如果无图片则使用代替的图片
            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.app_icon);
            imageShow.setImageBitmap(bmp);
            throw new NullPointerException("No key of photo");
        }

    }
    private void initializePhoto(Uri uri) {
        Photo.setFragment(this);
        Photo.createPhoto(uri);
    }
    private TextView textShow=null;
    private void initText() {
//        textShow.s
        String[] text={} ;//默认的text
        if (!fileList.containsKey(".txt")) {

        } else {
            String textName = fileList.get(".txt");

            try {
                File textFile = new File(filePath + textName);

                text = IOHelper.read(textFile);
            }catch (FileReadFailException e) {
                e.printStackTrace();
            }
            catch (EmptyFileException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
        if(text.length!=0) {
            textShow.setText((text.toString()));
        }else {
            textShow.setText("No text");
        }
    }
    private TextView timeShow =null; //TODO:可以使用ViewColorck

    private void initTime() {
        timeShow.setText(getTimeCapsuleTime());
    }
    private ImageButton soundButton=null;

    private void initSound() {
        Resources res=getResources();
        Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.mic_2);
        soundButton.setImageBitmap(bmp);
        Toast.makeText(getActivity(),"initSound",Toast.LENGTH_LONG).show();

    }
    private String getTimeCapsuleTime() {
        Log.i("test","call the getTimeTime");
        return "2013.2.3";
    }
    private void getTimeCapsuleSound() {

    }
    private String getTimeCapsuleText() {
        return "you are right";
    }
    private void getTimeCapusleImage() {


    }
    /*
    ////TODO:
    暂时使用
//    ???????*/
    public  Bitmap thumb(String photoPath) {
        int screenWidth,screenHeight;
        DisplayMetrics dm =this.getResources().getDisplayMetrics();
        screenWidth = /*px2dip*/(dm.widthPixels);
        screenHeight = /*px2dip*/(dm.heightPixels);
        BitmapFactory.Options options = getSize(photoPath);
        options.inSampleSize = options.outWidth / screenWidth;
        options.inPurgeable = true;
        options.inInputShareable = true;
        int height = options.outHeight * screenWidth / options.outWidth;
        options.outWidth = screenWidth;
        options.outHeight = height;
        options.inJustDecodeBounds = false;
        System.out.println("Width = "+options.outWidth+" ;Height = "+options.outHeight);
        return BitmapFactory.decodeFile(photoPath, options);
    }

    private static BitmapFactory.Options getSize(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(path, options);//这里返回的bmp是null
        return options;
    }

}
