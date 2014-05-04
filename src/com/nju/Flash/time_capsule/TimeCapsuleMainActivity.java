package com.nju.Flash.time_capsule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.nju.Flash.R;
import com.nju.Flash.time_capsule.timeLine.TimeLine;

import java.sql.Time;

/**
 * Created by randy on 14-4-30.
 */
public class TimeCapsuleMainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_capsule_main_activity);
        initButton();
    }
    private Button createTimeCapusleButton=null;
    private Button checkTimeLineButton=null;
    private void initButton() {
        createTimeCapusleButton=(Button)findViewById(R.id.create_Time_Capsule_Button);
        createTimeCapusleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(TimeCapsuleMainActivity.this,TimeCapsuleActivity.class);
                startActivity(intent);
            }
        });
        checkTimeLineButton=(Button)findViewById(R.id.check_Time_Line_Button);
        checkTimeLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(TimeCapsuleMainActivity.this, TimeLine.class);
                startActivity(intent);
            }
        });
    }

}
