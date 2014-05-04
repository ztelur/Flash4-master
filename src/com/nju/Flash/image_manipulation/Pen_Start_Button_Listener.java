package com.nju.Flash.image_manipulation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * 是对于
 * Created by randy on 14-3-1.
 */
public class Pen_Start_Button_Listener implements ImageView.OnTouchListener{
    private int startX;
    private int startY;
    private Canvas canvas=null;
    private Paint paint=null;

    public Pen_Start_Button_Listener() {
        paint=new Paint();

    }

    /**
     *
     * @param paint
     */
    public void setPaint(Paint paint) {
        this.paint=paint;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas=canvas;
    }

    /**
     *
     * @param width
     */
    public void setStrokeWidth(float width) {
        if(width>0){
        paint.setStrokeWidth(width);
        }else {

        }
    }

    /**
     *
     * @param color
     */
    public void setPaintColor( int color) {
        paint.setColor(color);
    }


   @Override
    public boolean onTouch(View view, MotionEvent motionEvent)  {
//       canvas=new Canvas()
       //从ImageView中获得图片
       //http://www.dewen.org/q/5760/android+Imageview+%E8%8E%B7%E5%8F%96bitmap
       ImageView view1=(ImageView)view;
       //view1.setDrawingCacheEnabled(true);
       try{
       Bitmap bitmap=view1.getDrawingCache();
       setCanvas(new Canvas(bitmap));


       //view1.setDrawingCacheEnabled(false);

       //默认的画笔大小
       paint.setStrokeWidth(5);


       switch (motionEvent.getAction()) {
           case MotionEvent.ACTION_DOWN: {
               //获得起始的x，y坐标
               startX=(int)motionEvent.getX();
               startY=(int)motionEvent.getY();

               break;
           }
           case MotionEvent.ACTION_MOVE: {
               int stopX=(int)motionEvent.getX();
               int stopY=(int)motionEvent.getX();

               canvas.drawLine(startX,startY,stopX,stopY,paint);

               //实时更新坐标
               startX=(int)motionEvent.getX();
               startY=(int)motionEvent.getY();
               view1.setImageBitmap(bitmap);
               break;

           }
       }
       }catch (Exception el) {
//           PrintStream ps= null;
//           try {
//               ps = new PrintStream(new FileOutputStream("/home/debug.txt"));
//               System.setOut(ps);
//               System.out.print("has a erro");

//           } catch (FileNotFoundException e) {
//               e.printStackTrace();
//           }
           System.err.println("has a erro?????????????????????????????????????????????");
           el.printStackTrace();
       }
        return true;
    }
}
