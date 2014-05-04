package com.nju.Flash.image_manipulation.Draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by randy on 14-3-3.
 */
public class DrawBS {
    public int downState;
    public int moveState;
    public Point downPoint=new Point();
    public Point movePoint=new Point();

    public Point eventPoint=new Point();
    public Paint paint;//声明画笔

    public DrawBS() {
        //设置画笔
        paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);//设置非充填
        paint.setStrokeWidth(5);//笔宽5像素

        paint.setColor(Color.RED);//红色
        paint.setAntiAlias(true); //锯齿不显示

    }
    /**
     *
     * @param canvas
     */
    public void onDraw(Canvas canvas)
    {

    }

    /**
     *
     * @param point
     */
    public void onTouchDown(Point   point)
    {

    }

    /**
     *
     * @param point
     */
    public void onTouchUp(Point point) {

    }

    /**
     *
     * @param point
     */
    public void onTouchMove(Point point) {

    }

}
