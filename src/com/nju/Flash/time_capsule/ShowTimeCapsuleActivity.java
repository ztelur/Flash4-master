package com.nju.Flash.time_capsule;

import android.app.ActionBar;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.nju.Flash.R;
import com.nju.Flash.time_capsule.content.Photo;
import com.nju.Flash.time_capsule.content.Record;
import com.nju.Flash.time_capsule.exception.ContentErrorException;
import com.nju.Flash.time_capsule.exception.EmptyFileException;
import com.nju.Flash.time_capsule.exception.FileReadFailException;

import java.io.File;

/**
 * For SampleRecordForPhoto
 *
 * @author 杨涛
 *         On 14-3-3 下午6:50 by IntelliJ IDEA
 */
public class ShowTimeCapsuleActivity extends Activity {
    private static String photoFileName = null;
    private static Uri photoUri = null;
    private ImageView imageView = null;
    private TextView timeCapsuleView = null;
    private TextView title = null;
    private static String[] content = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContent();

        imageView = (ImageView) findViewById(R.id.timeCapsulePhotoView);
        timeCapsuleView = (TextView)findViewById(R.id.timeCapsuleText);
        title = (TextView)findViewById(R.id.time_capsule_view_title);

        imageView.setImageBitmap(Photo.thumb());
        timeCapsuleView.setText(content[IOHelper.TEXT_CONTENT]);
        title.setText(content[IOHelper.TITLE_CONTENT]);

        content[0] = Boolean.TRUE.toString();
        IOHelper.initializeIOHelper();
        try {
            IOHelper.write(content);
        } catch (ContentErrorException e) {
            e.printStackTrace();
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playOrPause();
            }
        });

        Photo.setActivity(this);
    }

    private void setContent() {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        ActionBar actionBar = this.getActionBar();
        actionBar.setCustomView(R.layout.time_capsule_titlebar2);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.show();
        setContentView(R.layout.time_cpasule_view_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static void initialize(String name){
        photoFileName = name;
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        filePath += "/flash/record/"+name;
        try {
            content = IOHelper.read(new File(filePath));
            photoUri = Uri.parse(content[IOHelper.PHOTO_URL_CONTENT]);
        } catch (FileReadFailException e) {
            e.printStackTrace();
        } catch (EmptyFileException e) {
            e.printStackTrace();
        }
        Photo.createPhoto(photoUri,name.substring(0,name.length()-4));
    }

    private void playOrPause(){
        Record record = Record.getInstance(this);
        if(record.mediaPlayer==null)
            record.prepareForPlay();
        if(record.isPlaying()) {
            record.pause();
        }else{
            record.play();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(Record.getInstance(this).isPlaying())
                Record.getInstance(this).stop();
            finish();
        }
        return false;
    }
}
