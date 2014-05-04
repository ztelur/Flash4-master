package com.nju.Flash.image_manipulation.Draw;

import android.graphics.Canvas;
import android.graphics.Point;

/**画圆
 *无论拖动也好，拉伸也好，其实都是重新画圆，
 * 只是不改变画圆需要的某些属性进行绘制，这样看起来就行是移动或拖动的
 * Created by randy on 14-3-3.
 */
public class DrawCircle extends DrawBS {
    private Point rDotsPoint;//圆心
    private int radius=0;//半径
    private Double dtance=0.0;//当前点到圆心的距离

    public DrawCircle() {
        this.rDotsPoint =new Point();
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawCircle(rDotsPoint.x,rDotsPoint.y,radius,paint);
    }

    @Override
    public void onTouchDown(Point point) {
        downPoint.set(point.x,point.y);

        if(radius!=0) {
            //计算当前点到圆心的距离
            dtance=Math.sqrt((downPoint.x-rDotsPoint.x)
                                            *(downPoint.x-rDotsPoint.y)
                                            +(downPoint.y-rDotsPoint.y)
                                            *(downPoint.y-rDotsPoint.y));
            //如果距离半径-20或+20之间，认为用户点击在圆上
            if(dtance>=radius-20&&dtance<=radius+20) {
                downState=1;
            } else if (dtance<radius) {    //<radius ,and user click in the circle
                downState=-1;
            } else if (dtance>radius) {
                downState=0;
            }

        }else {
            downState=0;
        }
    }

    @Override
    public void onTouchUp(Point point) {
        super.onTouchUp(point);
    }

    @Override
    public void onTouchMove(Point point) {
        switch (downState) {
            //如果在圆内，从新设置圆心
            case -1: {
                rDotsPoint.set(point.x,point.y);
                break;
            }
            //if onto the circle ,then reset the radius
            case 1: {
                radius = (int) Math.sqrt((point.x - rDotsPoint.x)
                        * (point.x - rDotsPoint.x)
                        + (point.y - rDotsPoint.y)
                        * (point.y - rDotsPoint.y));
                break;
            }
            //if out the circle ,and repaint the circle
            default: {
                rDotsPoint.set(downPoint.x, downPoint.y);
                radius = (int) Math.sqrt((point.x - rDotsPoint.x)
                        * (point.x - rDotsPoint.x)
                        + (point.y - rDotsPoint.y)
                        * (point.y - rDotsPoint.y));
                break;
            }

        }
    }
}
