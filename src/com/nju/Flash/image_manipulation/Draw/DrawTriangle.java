package com.nju.Flash.image_manipulation.Draw;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;

/**画三角形
 *
 * 需要确定3个点：
 * 开始点、当前移动点，根据前两个点再设置一个点
 *
 * 由这3个点确定一个三角形
 * Created by randy on 14-3-3.
 */
public class DrawTriangle extends DrawBS {
    private Path path;
    private Point point1, point2, point3;
    private Rect point1Rect, point2Rect, point3Rect;

    public DrawTriangle() {
        point1 = new Point();
        point2 = new Point();
        point3 = new Point();

        point1Rect = null;
        point2Rect = null;
        point3Rect = null;

        path = new Path();
    }

    @Override
    public void onTouchUp(Point point) {
        super.onTouchUp(point);
    }

    @Override
    public void onTouchDown(Point point) {
        downPoint.set(point.x, point.y);

		/*
		 * 判断point3为中心的矩形point3Rect是否为null。
		 * 如果=null，则用户还没有开始画三角形；如果！=null，则用户已经画好了三角形
		 *
		 * 判断用户点击位置
		 */
        if (point1Rect != null) {
            if (point1Rect.contains(downPoint.x, downPoint.y)) {
                downState = 1;
            } else if (point2Rect.contains(downPoint.x, downPoint.y)) {
                downState = 2;
            } else if (point3Rect.contains(downPoint.x, downPoint.y)) {
                downState = 3;
            } else if (panduan(point1, point2, point3, downPoint)) {
                downState = 4;

            }else {
                downState = 0;
            }
        }
    }

    @Override
    public void onTouchMove(Point point) {
        switch (downState) {
            case 1:
                point1.set(point.x, point.y);
                setPath();
                moveState = 1;
                break;
            case 2:
                point2.set(point.x, point.y);
                setPath();
                moveState = 2;
                break;
            case 3:
                point3.set(point.x, point.y);
                setPath();
                moveState = 3;
                break;
            case 4:
			/*
			 * 如果点击三角形内，则开始移动三角形
			 *
			 * 我们以三角形的重心点coreTanriglePoint为中心点进行移动
			 *
			 */
                //计算三角形的重心
                Point coreTanriglePoint = new Point(
                        (point1.x + point2.x + point3.x) / 3,
                        (point1.y + point2.y + point3.y) / 3);
                //重心移到的距离
                int movedX = point.x - coreTanriglePoint.x;
                int movedY = point.y - coreTanriglePoint.y;
                //重新设定三角形的3个顶点
                point1.set(point1.x + movedX, point1.y + movedY);
                point2.set(point2.x + movedX, point2.y + movedY);
                point3.set(point3.x + movedX, point3.y + movedY);
                moveState = 4;
                setPath();
                break;
            default:
                //设置三角形的三个点
                point1.set(downPoint.x, downPoint.y);
                point2.set(point.x, point.y);
                //计算point1和point2的中间点
                Point cenPoint = new Point();
                cenPoint.x = (point2.x + point1.x) / 2;
                cenPoint.y = (point2.y + point1.y) / 2;
                //设置点point3
                point3.set(cenPoint.x+100, cenPoint.y-100);
                setPath();
                break;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }
    /*
	 * 判断用户所点击的点是否在三角形内
	 *
	 * 判断原理：
	 * 根据用户所点击的点和三角形的任意两个点所组成的3个三角形的面积
	 * 和原三角形的面积进行比较
	 *
	 * 如果相等，这在三角形内，
	 * 如果不相等，则在三角形外
	 */
    public static boolean panduan(Point a, Point b, Point c, Point p) {

        double abc = triangleArea(a, b, c);
        double abp = triangleArea(a, b, p);
        double acp = triangleArea(a, c, p);
        double bcp = triangleArea(b, c, p);

        if (abc == abp + acp + bcp) {
            return true;
        } else {
            return false;
        }
    }
    // 返回三个点组成三角形的面积
    private static double triangleArea(Point a, Point b, Point c) {

        double result = Math.abs((a.x * b.y + b.x * c.y + c.x * a.y - b.x * a.y
                - c.x * b.y - a.x * c.y) / 2.0D);
        return result;
    }


    public void setPath() {
        path = new Path();
        path.moveTo(point1.x, point1.y);
        path.lineTo(point2.x, point2.y);
        path.lineTo(point3.x, point3.y);
        path.close();

        point1Rect = new Rect(point1.x-20, point1.y-20, point1.x+20, point1.y+20);
        point2Rect = new Rect(point2.x-20, point2.y-20, point2.x+20, point2.y+20);
        point3Rect = new Rect(point3.x-20, point3.y-20, point3.x+20, point3.y+20);
    }
}
