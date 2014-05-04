package com.nju.Flash.image_manipulation.Draw;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;

/**画圆柱
 *
 * android自己没有画圆柱的方法，所以需要自己想个办法实现。
 * 思路：
 * 1、先画一个椭圆，
 * 2、根据第一个椭圆画第二个椭圆。这两个椭圆上下平行
 * 3、将两个椭圆的左边和右边连接起来。这样看起来就行圆柱了
 * Created by randy on 14-3-3.
 */
public class DrawColumn extends DrawBS {
    //声明椭圆1,2的4个坐标点
    private Point ovalPoint1,ovalPoint2,ovalPoint3,ovalPoint4;
    private Point leftPoint_oval1,rightPoint_oval1,leftPoint_oval2,rightPoint_oval2;
    private RectF rectf1,rectf2;

    public DrawColumn() {
        //实例化
        leftPoint_oval1=new Point();
        rightPoint_oval1=new Point();
        leftPoint_oval2=new Point();
        rightPoint_oval2=new Point();
        ovalPoint1=new Point();
        ovalPoint2=new Point();
        ovalPoint3=new Point();
        ovalPoint4=new Point();
        rectf1=new RectF();
        rectf2=new RectF();

    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawOval(rectf1,paint);
        canvas.drawOval(rectf2,paint);
        canvas.drawLine(leftPoint_oval1.x,leftPoint_oval1.y,
                                     leftPoint_oval2.x,leftPoint_oval2.y,paint);
        canvas.drawLine(rightPoint_oval1.x,rightPoint_oval1.y,
                                      rightPoint_oval2.x,rightPoint_oval2.y,paint);
    }

    @Override
    public void onTouchDown(Point point) {
        downPoint.set(point.x,point.y);

    }

    @Override
    public void onTouchUp(Point point) {
        super.onTouchUp(point);
    }

    @Override
    public void onTouchMove(Point point) {
        /*
		 * 画椭圆与画矩形类似
		 * 先确定画矩形1需要的两个点坐标点oval1Point1、oval1Point2
		 */
        ovalPoint1.set(downPoint.x,downPoint.y);
        ovalPoint2.set(point.x,point.y);

        /*
        * 椭圆1 左边和右边的的坐标点LeftPoint_oval1、RightPoint_oval1
                */
        //纵坐标
        int y1=ovalPoint1.y+(ovalPoint2.y-ovalPoint1.y)/2;
        leftPoint_oval1=new Point(ovalPoint1.x,y1);
        rightPoint_oval1=new Point(ovalPoint2.x,y1);

        //计算一个距离
        int distance=(int) Math.abs(Math.sqrt((ovalPoint2.x - ovalPoint1.x)
                * (ovalPoint2.x - ovalPoint1.x) + (ovalPoint2.y - ovalPoint1.y)
                * (ovalPoint2.y - ovalPoint1.y)));

        /*  根据椭圆1画椭圆2
                椭圆1与椭圆2上下平行，横坐标不变，改变纵坐标
                确定画椭圆2需要两个点，oval2Point1,oval2Point2
          */
        ovalPoint3.set(ovalPoint1.x,ovalPoint1.y+distance);
        ovalPoint4.set(ovalPoint2.x,ovalPoint2.y+distance);

        /* 椭圆左边和右边的坐标点
            leftPoint_oval1,RightPoint_oval1

         */
        //纵坐标
        int y2 = ovalPoint3.y + (ovalPoint4.y - ovalPoint3.y) / 2;
        leftPoint_oval1 = new Point(ovalPoint1.x, y2);
        rightPoint_oval2 = new Point(ovalPoint2.x, y2);

        rectf1.set(ovalPoint1.x, ovalPoint1.y, ovalPoint2.x, ovalPoint2.y);
        rectf2.set(ovalPoint3.x, ovalPoint3.y, ovalPoint4.x, ovalPoint4.y);
     }

}
