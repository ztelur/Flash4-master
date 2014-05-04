package com.nju.Flash.time_capsule.content;

import android.view.View;
import android.widget.Button;
import com.nju.Flash.R;
import com.nju.Flash.time_capsule.TimeCapsuleActivity;
import com.nju.Flash.time_capsule.buttons.RecordButton;

/**
 * For Flash
 *
 * @author 杨涛
 *         On 14-3-26 下午7:39 by IntelliJ IDEA
 */
public class RecordContent extends Content {
    private RecordButton recordStartButton;
    private Button recordPlayButton;
    private Record record;
    public RecordContent(TimeCapsuleActivity activity) {
        super(activity);
    }

    @Override
    protected void initializeComponent() {
        record = Record.getInstance(activity);
        recordStartButton = (RecordButton) contentView.findViewById(R.id.time_capsule_record_start_button);
        recordPlayButton = (Button) contentView.findViewById(R.id.time_capsule_record_play_button);
        recordPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (record.isPlaying())
                    record.stop();
                else
                    record.play();
            }
        });
        String path = record.getFilePath();
        recordStartButton.setSavePath(path);
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public void setText(String text) {

    }

    @Override
    protected void setViewID() {
        viewID = R.layout.time_capsule_record_content;
    }
}
