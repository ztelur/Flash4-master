package com.nju.Flash.time_capsule.buttons;

import android.content.Context;
import android.util.AttributeSet;
import com.nju.Flash.R;
import com.nju.Flash.time_capsule.ContentOrder;

/**
 * For Flash
 *
 * @author 杨涛
 *         On 14-3-26 下午6:08 by IntelliJ IDEA
 */
class RecordOpenButton extends OpenButton{
    public RecordOpenButton(Context context) {
        super(context);
    }

    public RecordOpenButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecordOpenButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setUnselectedButtonImage() {
        unselectedButtonImage = R.drawable.time_capsule_record_button;
    }

    @Override
    public void setSelectedButtonImage() {
        selectedButtonImage = R.drawable.time_capsule_record_selected_button;
    }

    @Override
    public void setFlag() {
        flag = ContentOrder.RECORD;
    }
}
