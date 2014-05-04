package com.nju.Flash.time_capsule.content;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.nju.Flash.R;
import com.nju.Flash.time_capsule.TimeCapsuleActivity;
import com.nju.Flash.time_capsule.animation.ContentAnimation;

import java.util.ArrayList;

/**
 * For Flash
 *
 * @author 杨涛
 *         On 14-3-26 下午7:12 by IntelliJ IDEA
 */
public class ContentManager {
    private static ContentManager contentManager = null;
    private ArrayList<Content> contents = new ArrayList<Content>();
    private static TimeCapsuleActivity activity = null;
    private static LayoutInflater inflater = null;
    private LinearLayout contentLayout = null;
    private int flagBefore = -1;

    private Content titleContent = null;
    private Content textContent = null;
    private Content timeContent = null;
    private Content recordContent = null;

    private ContentManager(TimeCapsuleActivity activity) {
        ContentManager.activity = activity;
        inflater = activity.getInflater();
        contentLayout = (LinearLayout) activity.findViewById(R.id.time_capsule_detail_content);
        initializeContents();
        addContents();
    }

    private void initializeContents() {
        titleContent = new TitleContent(activity);
        textContent = new TextContent(activity);
        timeContent = new TimeContent(activity);
        recordContent = new RecordContent(activity);
    }

    private void addContents() {
        contents.add(titleContent);
        contents.add(textContent);
        contents.add(timeContent);
        contents.add(recordContent);
    }

    public void changeToContent(int flag) {
        /*contentLayout.removeAllViews();
        contentLayout.addView(contents.get(flag).contentView);*/
        System.out.println("Change before :: flagBefore = "+flagBefore+" ;flag = "+flag);
        if(flagBefore==-1){
            contentLayout.removeAllViews();
            contentLayout.addView(contents.get(flag).contentView);
            changeAnimation(ContentAnimation.SHOW_FROM_BOTTOM_TO_TOP);
            flagBefore = flag;
        } else if (flag==flagBefore){
            changeAnimation(ContentAnimation.HIDE_FROM_TOP_TO_BOTTOM);
            contentLayout.removeAllViews();
            flagBefore=-1;
        } else if (flag>flagBefore){
            changeAnimation(ContentAnimation.HIDE_FROM_RIGHT_TO_LEFT);
            contentLayout.removeAllViews();
            contentLayout.addView(contents.get(flag).contentView);
            changeAnimation(ContentAnimation.SHOW_FROM_RIGHT_TO_LEFT);
            flagBefore = flag;
        } else if(flag<flagBefore){
            changeAnimation(ContentAnimation.HIDE_FROM_LEFT_TO_RIGHT);
            contentLayout.removeAllViews();
            contentLayout.addView(contents.get(flag).contentView);
            changeAnimation(ContentAnimation.SHOW_FROM_LEFT_TO_RIGHT);
            flagBefore = flag;
        }
        System.out.println("Change after :: flagBefore = "+flagBefore+" ;flag = "+flag);
    }

    public Content getContent(int flag) {
        return contents.get(flag);
    }

    public static ContentManager getInstance(TimeCapsuleActivity activity) {
        if (contentManager == null || ContentManager.activity != activity)
            contentManager = new ContentManager(activity);
        return contentManager;
    }

    private void changeAnimation(int flag) {
        switch (flag) {
            case ContentAnimation.SHOW_FROM_RIGHT_TO_LEFT:
                contentLayout.getChildAt(0).startAnimation(ContentAnimation.showFromRightToLeft);
                break;
            case ContentAnimation.SHOW_FROM_LEFT_TO_RIGHT:
                contentLayout.getChildAt(0).startAnimation(ContentAnimation.showFromLeftToRight);
                break;
            case ContentAnimation.HIDE_FROM_RIGHT_TO_LEFT:
                contentLayout.getChildAt(0).startAnimation(ContentAnimation.hideFromRightToLeft);
                break;
            case ContentAnimation.HIDE_FROM_LEFT_TO_RIGHT:
                contentLayout.getChildAt(0).startAnimation(ContentAnimation.hideFromLeftToRight);
                break;
            case ContentAnimation.HIDE_FROM_TOP_TO_BOTTOM:
                contentLayout.startAnimation(ContentAnimation.hideFromTopToBottom);
                contentLayout.setVisibility(View.GONE);
                break;
            case ContentAnimation.SHOW_FROM_BOTTOM_TO_TOP:
                contentLayout.startAnimation(ContentAnimation.showFromBottomToTop);
                contentLayout.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
