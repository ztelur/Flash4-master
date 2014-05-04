package com.nju.Flash.image_manipulation.tone_adjustment;

import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * For Flash
 *
 * @author 杨涛
 *         On 14-3-15 下午3:43 by IntelliJ IDEA
 */
public class Tone {

    /**
     * 饱和度标识
     */
    public static final int FLAG_SATURATION = 0x0;

    /**
     * 亮度标识
     */
    public static final int FLAG_LUM = 0x1;

    /**
     * 色相标识
     */
    public static final int FLAG_HUE = 0x2;

    /**
     * 饱和度
     */
    private TextView mSaturation;
    private SeekBar mSaturationBar;

    /**
     * 色相
     */
    private TextView mHue;
    private SeekBar mHueBar;

    /**
     * 亮度
     */
    private TextView mLum;
    private SeekBar mLumBar;

    private float mDensity;
    private static final int TEXT_WIDTH = 50;

    private LinearLayout mParent;

    private ColorMatrix mLightnessMatrix;
    private ColorMatrix mSaturationMatrix;
    private ColorMatrix mHueMatrix;
    private ColorMatrix mAllMatrix;

    private Bitmap baseBitmap;

    /**
     * 亮度
     */
    private float mLumValue = 1F;

    /**
     * 饱和度
     */
    private float mSaturationValue = 0F;

    /**
     * 色相
     */
    private float mHueValue = 0F;

    /**
     * SeekBar的中间值
     */
    private static final int MIDDLE_VALUE = 127;

    /**
     * SeekBar的最大值
     */
    private static final int MAX_VALUE = 255;

    private ArrayList<SeekBar> mSeekBars = new ArrayList<SeekBar>();

    public Tone(Context context) {
        init(context);
    }

    private void init(Context context) {
        mDensity = context.getResources().getDisplayMetrics().density;

        mSaturation = new TextView(context);
        mSaturation.setText("饱和度");
        mHue = new TextView(context);
        mHue.setText("色相");
        mLum = new TextView(context);
        mLum.setText("亮度");

        mSaturationBar = new SeekBar(context);
        mHueBar = new SeekBar(context);
        mLumBar = new SeekBar(context);

        mSeekBars.add(mSaturationBar);
        mSeekBars.add(mHueBar);
        mSeekBars.add(mLumBar);

        for (int i = 0, size = mSeekBars.size(); i < size; i++) {
            SeekBar seekBar = mSeekBars.get(i);
            seekBar.setMax(MAX_VALUE);
            seekBar.setProgress(MIDDLE_VALUE);
            seekBar.setTag(i);
        }

        LinearLayout saturation = new LinearLayout(context);
        saturation.setOrientation(LinearLayout.HORIZONTAL);
        saturation.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout.LayoutParams txtLayoutparams = new LinearLayout.LayoutParams((int) (TEXT_WIDTH * mDensity), LinearLayout.LayoutParams.MATCH_PARENT);
        mSaturation.setGravity(Gravity.CENTER);
        saturation.addView(mSaturation, txtLayoutparams);

        LinearLayout.LayoutParams seekLayoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        saturation.addView(mSaturationBar, seekLayoutparams);

        LinearLayout hue = new LinearLayout(context);
        hue.setOrientation(LinearLayout.HORIZONTAL);
        hue.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        mHue.setGravity(Gravity.CENTER);
        hue.addView(mHue, txtLayoutparams);
        hue.addView(mHueBar, seekLayoutparams);

        LinearLayout lum = new LinearLayout(context);
        lum.setOrientation(LinearLayout.HORIZONTAL);
        lum.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        mLum.setGravity(Gravity.CENTER);
        lum.addView(mLum, txtLayoutparams);
        lum.addView(mLumBar, seekLayoutparams);

        mParent = new LinearLayout(context);
        mParent.setOrientation(LinearLayout.VERTICAL);
        mParent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mParent.addView(saturation);
        mParent.addView(hue);
        mParent.addView(lum);
    }

    public View getParentView() {
        return mParent;
    }

    /**
     * 设置饱和度值
     *
     * @param saturation
     */
    public void setSaturation(int saturation) {
        Log.i("Tone_Test", "2. saturation set to " + saturation);
        mSaturationValue = saturation * 1.0F / MIDDLE_VALUE;
    }

    /**
     * 设置色相值
     *
     * @param hue
     */
    public void setHue(int hue) {
        Log.i("Tone_Test", "2. hue set to " + hue);
        mHueValue = hue * 1.0F / MIDDLE_VALUE;
    }

    /**
     * 设置亮度值
     *
     * @param lum
     */
    public void setLum(int lum) {
        Log.i("Tone_Test", "2. lum set to " + lum);
        mLumValue = (lum - MIDDLE_VALUE) * 1.0F / MIDDLE_VALUE * 180;
    }

    public ArrayList<SeekBar> getSeekBars() {
        return mSeekBars;
    }

    /**
     * @param flag 比特位0 表示是否改变色相，比位1表示是否改变饱和度,比特位2表示是否改变明亮度
     */
    Bitmap resultBitmap;

    public Bitmap handleImage(int flag) {
        Log.i("Tone_Test", "handle image function action, flag = " + flag + " mHueValue = " +
                mHueValue + " mSaturationValue = " + mSaturationValue + " mLumValue = " + mLumValue);
        // 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
        Canvas canvas = new Canvas(resultBitmap); // 得到画笔对象
        Paint paint = new Paint(); // 新建paint
        paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理
        if (null == mAllMatrix) {
            mAllMatrix = new ColorMatrix();
        }

        if (null == mLightnessMatrix) {
            mLightnessMatrix = new ColorMatrix(); // 用于颜色变换的矩阵，android位图颜色变化处理主要是靠该对象完成
        }

        if (null == mSaturationMatrix) {
            mSaturationMatrix = new ColorMatrix();
        }

        if (null == mHueMatrix) {
            mHueMatrix = new ColorMatrix();
        }

        switch (flag) {
            case FLAG_HUE: // 需要改变色相
                mHueMatrix.reset();
                mHueMatrix.setScale(mHueValue, mHueValue, mHueValue, 1); // 红、绿、蓝三分量按相同的比例,最后一个参数1表示透明度不做变化，此函数详细说明参考
                Log.i("Tone_Test", "3. FLAG_HUE  changing to " + mHueValue);
                // // android
                // doc
                break;
            case FLAG_SATURATION: // 需要改变饱和度
                // saturation 饱和度值，最小可设为0，此时对应的是灰度图(也就是俗话的“黑白图”)，
                // 为1表示饱和度不变，设置大于1，就显示过饱和
                mSaturationMatrix.reset();
                mSaturationMatrix.setSaturation(mSaturationValue);
                Log.i("Tone_Test", "3. FLAG_SATURATION  changing to " + mSaturationValue);
                break;
            case FLAG_LUM: // 亮度
                // hueColor就是色轮旋转的角度,正值表示顺时针旋转，负值表示逆时针旋转
                mLightnessMatrix.reset(); // 设为默认值
                mLightnessMatrix.setRotate(0, mLumValue); // 控制让红色区在色轮上旋转的角度
                mLightnessMatrix.setRotate(1, mLumValue); // 控制让绿红色区在色轮上旋转的角度
                mLightnessMatrix.setRotate(2, mLumValue); // 控制让蓝色区在色轮上旋转的角度
                Log.i("Tone_Test", "3. FLAG_LUM  changing to " + mLumValue);
                // 这里相当于改变的是全图的色相
                break;
        }
        mAllMatrix.reset();
        mAllMatrix.postConcat(mHueMatrix);
        mAllMatrix.postConcat(mSaturationMatrix); // 效果叠加
        mAllMatrix.postConcat(mLightnessMatrix); // 效果叠加

        paint.setColorFilter(new ColorMatrixColorFilter(mAllMatrix));// 设置颜色变换效果
        canvas.drawBitmap(baseBitmap, 0, 0, paint); // 将颜色变化后的图片输出到新创建的位图区
        // 返回新的位图，也即调色处理后的图片
        return resultBitmap;
    }

    public void initBaseBitmap(Bitmap bitmap) {
        baseBitmap = bitmap;
        resultBitmap = Bitmap.createBitmap(baseBitmap.getWidth(), baseBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
    }
}
