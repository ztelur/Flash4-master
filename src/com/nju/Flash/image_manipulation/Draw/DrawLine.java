package com.nju.Flash.image_manipulation.Draw;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

/** 画线段
 *
 * 伸长、缩短、移动其实都是重新画线
 * Created by randy on 14-3-3.
 */
public class DrawLine extends DrawBS {
    private Point centerPoint=null;//中间点
    private Point IPoint1;
    private Point IPoint2;//起点，终点
    private Rect IPoint1Rect,IPoint2Rect;//以起点和终点为中心的矩形

    public DrawLine() {
        centerPoint=new Point();
        IPoint1=new Point();
        IPoint2=new Point();
        IPoint1Rect=new Rect();
        IPoint2Rect=new Rect();
    }

    @Override
    public void onDraw(Canvas canvas) {
        // 画直线
        canvas.drawLine(IPoint1.x, IPoint1.y, IPoint2.x, IPoint2.y, paint);
    }

    @Override
    public void onTouchUp(Point point) {
        //重新设定直线起点和终点为中心的矩形
        IPoint1Rect.set(IPoint1.x - 25, IPoint1.y - 25, IPoint1.x + 25,
                IPoint1.y + 25);
        IPoint2Rect.set(IPoint2.x - 25, IPoint2.y - 25, IPoint2.x + 25,
                IPoint2.y + 25);
    }

    @Override
    public void onTouchMove(Point point) {
        switch (downState) {
            //如果拖动直线起点，则直线的终点不变
            case 1:
                IPoint1.set(point.x, point.y);
                moveState = 1;
                break;
            //如果拖动直线终点，则直线的起点不变
            case 2:
                IPoint2.set(point.x, point.y);
                moveState = 2;
                break;
            //如果点住直线进行拖动，则根据移动的距离重新设定直线的起点与终点
            case 3:
                //计算直线的中间点
                centerPoint.x = (IPoint2.x + IPoint1.x) / 2;
                centerPoint.y = (IPoint2.y + IPoint1.y) / 2;
                //移动距离
                int movedX = point.x - centerPoint.x;
                int movedY = point.y - centerPoint.y;


                IPoint1.x = IPoint1.x + movedX;
                IPoint1.y = IPoint1.y + movedY;
                IPoint2.x = IPoint2.x + movedX;
                IPoint2.y = IPoint2.y + movedY;
                moveState = 3;
                break;
            //如果不在直线上，则重新画直线
            default:
                IPoint1.set(downPoint.x, downPoint.y);
                IPoint2.set(point.x, point.y);
                break;
        }
    }

    @Override
    public void onTouchDown(Point point) {
        downPoint.set(point.x, point.y);

        //如果点击起点
        if (IPoint1Rect.contains(point.x, point.y)) {
            Log.i("onTouchDown", "downState = 1");
            downState = 1;
            //如果点击终点
        } else if (IPoint2Rect.contains(point.x, point.y)) {
            Log.i("onTouchDown", "downState = 2");
            downState = 2;
            //如果在直线上
        } else if (panduan(point)) {
            Log.i("onTouchDown", "downState = 3");
            downState = 3;
            //在直线外
        } else {
            Log.i("onTouchDown", "downState = 0");
            downState = 0;
        }
    }
    /*
     * 判断当前所点击的点是否在直线上
     *
     * 根据用户所点击的点到线段两个端点的距离之和
     * 与线段的距离进行比较 来判断
     */
    public boolean panduan(Point point) {

        double lDis = Math.sqrt((IPoint1.x - IPoint2.x)
                * (IPoint1.x - IPoint2.x) + (IPoint1.y - IPoint2.y)
                * (IPoint1.y - IPoint2.y));

        double lDis1 = Math.sqrt((point.x - IPoint1.x) * (point.x -IPoint1.x)
                + (point.y - IPoint1.y) * (point.y - IPoint1.y));
        double lDis2 = Math.sqrt((point.x - IPoint2.x) * (point.x - IPoint2.x)
                + (point.y - IPoint2.y) * (point.y - IPoint2.y));


        if (lDis1 + lDis2 >= lDis + 0.00f && lDis1 + lDis2 <= lDis + 5.00f) {
            return true;
        } else {
            return false;
        }
    }
}
