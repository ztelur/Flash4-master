package com.nju.Flash.time_capsule.content;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import com.nju.Flash.R;
import com.nju.Flash.time_capsule.Strings;
import com.nju.Flash.time_capsule.TimeCapsuleActivity;

/**
 * For Flash
 *
 * @author 杨涛
 *         On 14-3-26 下午7:34 by IntelliJ IDEA
 */
public class TitleContent extends Content {
    private EditText titleEditText;
    TitleContent(TimeCapsuleActivity activity) {
        super(activity);
    }

    @Override
    protected void initializeComponent() {
        titleEditText = (EditText)contentView.findViewById(R.id.time_capsule_title_text);
        titleEditText.setText(Strings.INPUT_TITLE);
        titleEditText.setTextColor(Color.GRAY);
        titleEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && getText().equals(Strings.INPUT_TITLE)) {
                    titleEditText.setTextColor(Color.BLACK);
                    titleEditText.setText("");
                }
            }
        });
    }

    @Override
    public String getText() {
        return titleEditText.getText().toString();
    }

    @Override
    public void setText(String text) {
        titleEditText.setTextColor(Color.BLACK);
        titleEditText.setText(text);
    }

    @Override
    protected void setViewID() {
        viewID = R.layout.time_capsule_title_content;
    }
}
