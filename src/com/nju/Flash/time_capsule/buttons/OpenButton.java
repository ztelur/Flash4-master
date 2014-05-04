package com.nju.Flash.time_capsule.buttons;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * For Flash
 *
 * @author 杨涛
 *         On 14-3-26 下午5:14 by IntelliJ IDEA
 */
abstract class OpenButton extends ImageView{
    protected int unselectedButtonImage;
    protected int selectedButtonImage;
    protected int flag;
    protected boolean isSelected =false;
    protected Listener listener = new Listener();

    public OpenButton(Context context) {
        super(context);
        initializeButtonImageAndView();
    }

    public OpenButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeButtonImageAndView();
    }

    public OpenButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeButtonImageAndView();
    }

    private void initializeButtonImageAndView() {
        setUnselectedButtonImage();
        setSelectedButtonImage();
        setFlag();
        setOnClickListener(listener);
    }

    public void setSelected(boolean isSelected){
        setImageResource(isSelected? selectedButtonImage : unselectedButtonImage);
        this.isSelected =isSelected;
    }

    abstract void setUnselectedButtonImage();
    abstract void setSelectedButtonImage();
    abstract void setFlag();

    class Listener implements OnClickListener{
        @Override
        public void onClick(View v) {
            OpenButtonArray.changeTo(flag);
        }
    }
}
