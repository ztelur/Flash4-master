package com.nju.Flash.image_manipulation.Draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

public class DrawPath extends DrawBS {

	private Path path = new Path();
	private float mX, mY;

	public DrawPath() {
		// TODO Auto-generated constructor stub
		
	}
	
	//���ѡ����Ƥ������Ҫ������¸�ֵ
	public DrawPath(int i) {

		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setColor(Color.BLUE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.SQUARE);
		paint.setStrokeWidth(15);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

	}
	

	public void onTouchDown(Point point) {
		path.moveTo(point.x, point.y);
		mX = point.x;
		mY = point.y;
	}

	public void onTouchMove(Point point) {
		float dx = Math.abs(point.x - mX);
		float dy = Math.abs(point.y - mY);
		if (dx > 0 || dy > 0) {
			path.quadTo(mX, mY, (point.x + mX) / 2, (point.y + mY) / 2);
			mX = point.x;
			mY = point.y;
		} else if (dx == 0 || dy == 0) {
			path.quadTo(mX, mY, (point.x + 1 + mX) / 2, (point.y + 1 + mY) / 2);
			mX = point.x + 1;
			mY = point.y + 1;
		}
	}

	
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawPath(path, paint);
	}

}
