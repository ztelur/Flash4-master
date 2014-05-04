package com.nju.Flash.time_capsule.buttons;

import android.content.Context;
import android.util.AttributeSet;
import com.nju.Flash.R;
import com.nju.Flash.time_capsule.ContentOrder;

/**
 * For Flash
 *
 * @author 杨涛
 *         On 14-3-26 下午5:41 by IntelliJ IDEA
 */
class TitleOpenButton extends OpenButton{
    public TitleOpenButton(Context context) {
        super(context);
    }

    public TitleOpenButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleOpenButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setUnselectedButtonImage() {
        unselectedButtonImage = R.drawable.time_capsule_title_button;
    }

    @Override
    public void setSelectedButtonImage() {
        selectedButtonImage = R.drawable.time_capsule_title_selected_button;
    }

    @Override
    public void setFlag() {
        flag = ContentOrder.TITLE;
    }
}
