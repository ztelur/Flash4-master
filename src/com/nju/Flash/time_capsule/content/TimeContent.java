package com.nju.Flash.time_capsule.content;

import android.view.View;
import android.widget.TextView;
import com.nju.Flash.R;
import com.nju.Flash.time_capsule.TimeCapsuleActivity;

/**
 * For Flash
 *
 * @author 杨涛
 *         On 14-3-26 下午7:38 by IntelliJ IDEA
 */
public class TimeContent extends Content {
    private TextView timeTextView;
    public TimeContent(TimeCapsuleActivity activity) {
        super(activity);
    }

    @Override
    protected void initializeComponent() {
        timeTextView = (TextView) contentView.findViewById(R.id.time_capsule_time_text);
        timeTextView.setClickable(true);
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.timePickerDialog();
            }
        });
    }

    @Override
    public String getText() {
        return timeTextView.getText().toString();
    }

    @Override
    public void setText(String text) {
        timeTextView.setText(text);
    }

    @Override
    protected void setViewID() {
        viewID = R.layout.time_capsule_time_content;
    }
}
