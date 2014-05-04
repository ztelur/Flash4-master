package com.nju.Flash.time_capsule.content;

import android.view.View;
import android.widget.ImageView;
import com.nju.Flash.R;
import com.nju.Flash.time_capsule.TimeCapsuleActivity;

/**
 * For Flash
 *
 * @author 杨涛
 *         On 14-3-26 下午8:27 by IntelliJ IDEA
 */
public class TitleBar {
    private static TitleBar titleBar = null;
    private ImageView saveButton = null;
    private ImageView cancelButton = null;
    private static TimeCapsuleActivity activity = null;

    private TitleBar(TimeCapsuleActivity activity){
        TitleBar.activity = activity;
        initializeButtons();
    }

    public static TitleBar getInstance(TimeCapsuleActivity activity){
        if(titleBar==null||TitleBar.activity!=activity)
            titleBar = new TitleBar(activity);
        return titleBar;
    }

    private void initializeButtons() {
        saveButton = (ImageView) activity.findViewById(R.id.save_button);
        cancelButton = (ImageView) activity.findViewById(R.id.back_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.exitDialog();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.requestFocus();
                activity.saveListener();
            }
        });
    }
}
