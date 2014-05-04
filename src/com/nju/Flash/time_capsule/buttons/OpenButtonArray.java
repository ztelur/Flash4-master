package com.nju.Flash.time_capsule.buttons;

import android.view.LayoutInflater;
import com.nju.Flash.R;
import com.nju.Flash.time_capsule.TimeCapsuleActivity;

import java.util.ArrayList;

/**
 * For Flash
 *
 * @author 杨涛
 *         On 14-3-26 下午5:25 by IntelliJ IDEA
 */
public class OpenButtonArray{
    private static ArrayList<OpenButton> openButtons = new ArrayList<OpenButton>();
    private static LayoutInflater inflater = null;
    private static TimeCapsuleActivity activity = null;
    private static int selected = -1;
    private static OpenButtonArray openButtonArray = null;

    private OpenButton titleOpenButton = null;
    private OpenButton textOpenButton = null;
    private OpenButton timeOpenButton = null;
    private OpenButton recordOpenButton = null;

    private OpenButtonArray(TimeCapsuleActivity activity){
        OpenButtonArray.activity = activity;
        inflater = activity.getInflater();
        findButtons(activity);
        addButtons();
    }

    private void findButtons(TimeCapsuleActivity activity) {
        titleOpenButton = (TitleOpenButton) activity.findViewById(R.id.time_capsule_title_button);
        textOpenButton = (TextOpenButton) activity.findViewById(R.id.time_capsule_text_button);
        timeOpenButton = (TimeOpenButton) activity.findViewById(R.id.time_capsule_time_button);
        recordOpenButton = (RecordOpenButton) activity.findViewById(R.id.time_capsule_record_button);
    }

    private void addButtons() {
        openButtons.add(titleOpenButton);
        openButtons.add(textOpenButton);
        openButtons.add(timeOpenButton);
        openButtons.add(recordOpenButton);
    }

    public static void changeTo(int flag){
        if(selected!=-1)
            openButtons.get(selected).setSelected(false);
        openButtons.get(flag).setSelected(true);
        activity.changeToView(flag);
        selected = flag;
    }

    public static OpenButtonArray getInstance(TimeCapsuleActivity activity){
        if(openButtonArray==null||OpenButtonArray.activity!=activity)
            openButtonArray = new OpenButtonArray(activity);
        return openButtonArray;
    }
}
