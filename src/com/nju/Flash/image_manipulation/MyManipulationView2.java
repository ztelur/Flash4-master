package com.nju.Flash.image_manipulation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.nju.Flash.image_manipulation.Draw.*;
import com.nju.Flash.image_manipulation.tone_adjustment.Tone;

import java.io.*;


public class MyManipulationView2 extends View {

	private DrawBS drawBS = null;
	private Point evevtPoint;
	private Bitmap floorBitmap, surfaceBitmap;// �ײ�����bitmap
	private Canvas floorCanvas, surfaceCanvas;// bitmap��Ӧ��canvas

	private boolean isEraser = false;

	Bitmap newbm;

	@SuppressLint("ParserError")
	public MyManipulationView2(Context context) {
		super(context);

        //初始化drawBS
		drawBS = new DrawPath();
		evevtPoint = new Point();

        //将view加入布局中
        floorBitmap = Bitmap.createBitmap(480, 700, Bitmap.Config.ARGB_8888);
		floorCanvas = new Canvas(floorBitmap);


        //表面的bitmap，置于底层的bitmap之上，要设置成透明色
		surfaceBitmap = Bitmap.createBitmap(480, 700, Bitmap.Config.ARGB_8888);
		surfaceCanvas = new Canvas(surfaceBitmap);
		surfaceCanvas.drawColor(Color.TRANSPARENT);

	}

	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

        //将底层的bitmap绘制到主画布上
		
		canvas.drawBitmap(floorBitmap, 0, 0, null);

        //判断选择的图形是否是橡皮
		if (isEraser) {
            //如果是橡皮，让画笔在底层的bitmap上进行操作
            /*
			 * 传递底层Canvas参数。 调用相应的画图工具类方法,在底层bitmap上使用floorCanvas进行绘图
			 */
			drawBS.onDraw(floorCanvas);
			canvas.drawBitmap(floorBitmap, 0, 0, null);

		} else {
            // 如果不是橡皮，则让画笔在表层bitmap上进行操作，
			/*
			 * 传递表层Canvas参数。 调用相应画图工具类方法,在表层bitmap上使用surfaceCanvas进行绘图
			 */
			drawBS.onDraw(surfaceCanvas);
			canvas.drawBitmap(surfaceBitmap, 0, 0, null);
		}

	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
        //获得坐标
        evevtPoint.set((int) event.getX(), (int) event.getY());

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			drawBS.onTouchDown(evevtPoint);
			break;

		case MotionEvent.ACTION_MOVE:
			drawBS.onTouchMove(evevtPoint);
			  /*
			 * 拖动过程中不停的将bitmap的颜色设置为透明（清空表层bitmap）
			 * 否则整个拖动过程的轨迹都会画出来
			 */
			surfaceBitmap.eraseColor(Color.TRANSPARENT);
			invalidate();
			break;

		case MotionEvent.ACTION_UP:
			drawBS.onTouchUp(evevtPoint);
			break;
		default:
			break;
		}
		return true;
	}

    //选择图形，实例化相应的类
	public void setDrawTool(OperationEnum i) {
		switch (i) {
		case a:
			drawBS = new DrawLine();
			break;
		case b:
			drawBS = new DrawRectangle();
			break;
		case c:
			drawBS = new DrawCircle();
			break;
		case d:
			drawBS = new DrawTriangle();
			break;
		case e:
			drawBS = new DrawCube();
			break;
		case pen:
			drawBS = new DrawColumn();
			break;
		case eraser:
            // 如果需要橡皮。则实例化重新设置画笔的构造方法
			drawBS = new DrawPath(10);// ��Ƥ
			break;
		default:
			drawBS = new DrawPath();
			break;
		}

        // 如果选择橡皮，isEraser = true
		if (i == OperationEnum.eraser) {
			isEraser = true;
		} else {
			isEraser = false;
		}
        // 如果重新选择了图形，则需要将表层bitmap上的图像绘制到底层bitmap上进行保存
		floorCanvas.drawBitmap(surfaceBitmap, 0, 0, null);
	}

    /**
     *  将图片存入内存卡
     * @param draw_name
     * @param  alpha
     */
	public void savePicture(String draw_name, int alpha) {
		FileOutputStream fos = null;
		String type = null;

		if (alpha == 0) {//不透明
			type = ".jpg";
		} else {
			type = ".png";
		}
        //处理文件夹
		try {
			String strPath = new String("/sdcard/HBImg/");
			File fPath = new File(strPath);
			if (!fPath.exists()) {
				fPath.mkdir();
			}
            //处理文件
			File f = new File(strPath + draw_name.trim() + type);
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			fos = new FileOutputStream(f);
			Bitmap b = null;
			destroyDrawingCache();
			setDrawingCacheEnabled(true);

			buildDrawingCache();
			b = getDrawingCache();

			if (b != null) {
				b.compress(Bitmap.CompressFormat.PNG, 100, fos);
				if (!b.isRecycled())
					b.recycle();
				b = null;
				System.gc();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					Log.e("send picture to dbserver", "�ر��ϴ�ͼƬ�������ʧ�ܣ�");
				}
			}
		}
	}

    /**
     *
     * @param draw_name
     */
	public void editPicture(String draw_name) {
        //新建一个bitmap来临时保存读取的文件
		Bitmap tempBitmap = Bitmap.createBitmap(floorBitmap.getWidth(),
				floorBitmap.getHeight(), Bitmap.Config.ARGB_8888);
		if (!tempBitmap.isRecycled()) {
			tempBitmap.recycle();
			tempBitmap = null;
			System.gc();
		}

		tempBitmap = getLoacalBitmap(draw_name);
        //将tempBitmap绘制到floorBitmap
		floorCanvas.drawBitmap(tempBitmap, 0, 0, null);
		invalidate();
	}

	private Bitmap getLoacalBitmap(String draw_name) {
		try {
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inSampleSize = 1;
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true; //获取图片资源
			FileInputStream fis = new FileInputStream(
					"/sdcard/HBImg/" + draw_name);
			return BitmapFactory.decodeStream(fis, null, opt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
    public void setImageUri(Uri uri) {
        try {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), uri);
        floorBitmap=bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setImageInBitmap(Bitmap bitmap) {
        floorBitmap=bitmap;
        invalidate();
    }

    public void setImage(Intent intent) {
//        Bundle extras=intent.getExtras();
        try {
            Toast.makeText(getContext(), "dddd", Toast.LENGTH_LONG).show();
            //if()
            floorBitmap = (Bitmap) intent.getParcelableExtra("image");
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    Tone mToneLayer;

    public void toneChange(int flag) {
        floorBitmap = mToneLayer.handleImage(flag);
        refresh();
    }

    public void setToneAndPhoto(Tone tone, Uri photoUri) {
        mToneLayer = tone;
        try {
            mToneLayer.initBaseBitmap(floorBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getFloorBitmap(){
        return floorBitmap;
    }

    public void setFloorBitmap(Bitmap bitmap){
        floorBitmap = bitmap;
    }

    public void refresh(){
        invalidate();
    }

}
