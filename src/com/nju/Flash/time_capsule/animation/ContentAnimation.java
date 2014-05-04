package com.nju.Flash.time_capsule.animation;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * For Flash
 *
 * @author 杨涛
 *         On 14-4-3 下午2:33 by IntelliJ IDEA
 */
public class ContentAnimation {
    public static final int SHOW_FROM_RIGHT_TO_LEFT = 0;
    public static final int SHOW_FROM_LEFT_TO_RIGHT = 1;
    public static final int HIDE_FROM_RIGHT_TO_LEFT = 2;
    public static final int HIDE_FROM_LEFT_TO_RIGHT = 3;
    public static final int HIDE_FROM_TOP_TO_BOTTOM = 4;
    public static final int SHOW_FROM_BOTTOM_TO_TOP = 5;

    public static TranslateAnimation showFromRightToLeft;
    public static TranslateAnimation showFromLeftToRight;
    public static TranslateAnimation hideFromRightToLeft;
    public static TranslateAnimation hideFromLeftToRight;
    public static Animation showFromBottomToTop;
    public static Animation hideFromTopToBottom;

    private static final long TRANSLATION_ANIMATION_DURATION = 300;
    private static final long SCALE_ANIMATION_DURATION = 200;

    static {
        showFromRightToLeft = newTranslateAnimation(1.0f, 0.0f, 0.0f, 0.0f);
        showFromLeftToRight = newTranslateAnimation(-1.0f, 0.0f, 0.0f, 0.0f);
        hideFromRightToLeft = newTranslateAnimation(0.0f, -1.0f, 0.0f, 0.0f);
        hideFromLeftToRight = newTranslateAnimation(0.0f, 1.0f, 0.0f, 0.0f);
        showFromBottomToTop =newScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
        hideFromTopToBottom =newScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f);
    }

    private static TranslateAnimation newTranslateAnimation(float fromX, float toX, float fromY, float toY) {
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, fromX,
                Animation.RELATIVE_TO_SELF, toX,
                Animation.RELATIVE_TO_SELF, fromY,
                Animation.RELATIVE_TO_SELF, toY);
        animation.setDuration(TRANSLATION_ANIMATION_DURATION);
        return animation;
    }

    private static ScaleAnimation newScaleAnimation(float fromX, float toX, float fromY, float toY){
        ScaleAnimation animation = new ScaleAnimation(
                fromX,toX,fromY,toY,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 1.0f
        );
        animation.setDuration(SCALE_ANIMATION_DURATION);
        return animation;
    }
}
