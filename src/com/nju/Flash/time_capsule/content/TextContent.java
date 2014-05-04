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
 *         On 14-3-26 下午7:38 by IntelliJ IDEA
 */
public class TextContent extends Content {
    private EditText textEditText;
    public TextContent(TimeCapsuleActivity activity) {
        super(activity);
    }

    @Override
    protected void initializeComponent() {
        textEditText = (EditText) contentView.findViewById(R.id.time_capsule_text_text);
        textEditText.setText(Strings.INPUT_TEXT);
        textEditText.setTextColor(Color.GRAY);
        textEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && getText().equals(Strings.INPUT_TEXT)) {
                    textEditText.setTextColor(Color.BLACK);
                    textEditText.setText("");
                }
            }
        });
    }

    @Override
    public String getText() {
        return textEditText.getText().toString();
    }

    @Override
    public void setText(String text) {
        textEditText.setTextColor(Color.BLACK);
        textEditText.setText(text);
    }

    @Override
    protected void setViewID() {
        viewID = R.layout.time_capsule_text_content;
    }
}
