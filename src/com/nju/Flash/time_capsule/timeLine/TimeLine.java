package com.nju.Flash.time_capsule.timeLine;

import android.app.*;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import com.nju.Flash.R;
import com.nju.Flash.image_manipulation.function_fragment.Pen_Function_Fragment;
import com.nju.Flash.time_capsule.IOHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by randy on 14-4-27.
 */
public class TimeLine extends Activity implements View.OnTouchListener{
    private String filePath=null;
    private HashMap<String,String> filePathList=null;
    //获得所有资源文件的路径
//    private List<TimeRecord> recordList=new ArrayList<TimeRecord>();
    private void setMyContent() {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        ActionBar actionBar = this.getActionBar();
        //TODO: 要换成自己的
        actionBar.setCustomView(R.layout.time_line_titlebar);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.show();
        setContentView(R.layout.time_line_layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager=getFragmentManager();
        initRecordList();
        setFragment();
        setMyContent();
    }
    private void initRecordList() {
        //从内存中读出所有的时间胶囊，获得图片,文字，音频
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        filePath += "/flash/";
        String[] filelist= IOHelper.getPathFileList(filePath);
        if(filelist.length!=0) {
            for(int i=0;i<filelist.length;i++) {
                Log.i("test",filePath.length()+"");
                //传个Fragment........
                //TODO:.........
                testFragment=new TimeFragment(filePath+filelist[i]+"/");//初始化testFragment

            }
        }



    }
    private FragmentManager fragmentManager=null;
    private Fragment testFragment=null;
    private void setFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Log.v("erro", "do it");
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况

        transaction.replace(R.id.fragment1,testFragment);
        Toast.makeText(getApplicationContext(),"setFragment",Toast.LENGTH_LONG).show();
        transaction.commit();
    }
    public TimeLine() {

    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        return false;
    }
}
