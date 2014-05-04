package com.nju.Flash.time_capsule.content;

import android.view.View;
import com.nju.Flash.time_capsule.TimeCapsuleActivity;

/**
 * For Flash
 *
 * @author 杨涛
 *         On 14-3-26 下午7:05 by IntelliJ IDEA
 */
public abstract class Content {
    protected int viewID;
    protected View contentView;
    protected TimeCapsuleActivity activity;

    Content(TimeCapsuleActivity activity) {
        this.activity = activity;
        setViewID();
        contentView = activity.getInflater().inflate(viewID, null);
        initializeComponent();
    }

    protected abstract void initializeComponent();

    public abstract String getText();

    public abstract void setText(String text);

    protected abstract void setViewID();
}
