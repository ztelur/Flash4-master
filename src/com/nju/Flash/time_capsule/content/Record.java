package com.nju.Flash.time_capsule.content;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * For SampleRecordForPhoto
 *
 * @author 杨涛
 *         On 14-3-2 下午2:09 by IntelliJ IDEA
 */
public class Record {
    private static Record record;
    private static String dirPath;
    private static Activity activity;
    private static final String suffix = ".amr";
    public static boolean isRecord = false;

    private String fileName;
    private String filePath;
    public MediaPlayer mediaPlayer;

    static {
        dirPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        dirPath += "/flash/record/";
        File fileDir = new File(dirPath);
        fileDir.mkdirs();
    }

    private Record(){
        fileName = Photo.getName();
        filePath = dirPath + fileName + suffix;
    }

    public static Record getInstance(Activity activity){
        Record.activity = activity;
        if(record==null)
            record = new Record();
        return record;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void prepareForPlay(){
        mediaPlayer = MediaPlayer.create(Record.activity, Uri.parse(filePath));
    }

    public void play(){
        prepareForPlay();
        mediaPlayer.start();
    }

    public void pause(){
        mediaPlayer.pause();
    }

    public void stop(){
        mediaPlayer.stop();
    }

    public boolean isPlaying(){
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }
}
