package com.nju.Flash.app_main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.nju.Flash.R;
import com.nju.Flash.image_manipulation.ImageOperationTypeActivity;
import com.nju.Flash.image_manipulation.Image_manipulation_main_activity;
import com.nju.Flash.time_capsule.TimeCapsuleActivity;
import com.nju.Flash.time_capsule.TimeCapsuleMainActivity;
import com.nju.Flash.time_capsule.TimeCapsuleService;

public class FlashLaunch extends Activity {
	private static final  int select_pic_from_sys_request_code=1;
	private static final  int get_pic_from_sys_camera_code=2;
	private boolean isBig=false;//图片是否大于屏幕分辨率

	private Button image_handle_button=null;
	private Button time_capsule_button=null;

    //时间胶囊服务
    public static Intent timeServiceIntent;
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
        timeServiceIntent = new Intent(this, TimeCapsuleService.class);
        startService(timeServiceIntent);

		//处理图片的按钮
		image_handle_button=(Button)findViewById(R.id.launch_image_handle_button);
		image_handle_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				Intent intent = new Intent(Intent.ACTION_PICK, null);
//				intent.setDataAndType(
//						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//						"image/*");
//				startActivityForResult(intent, select_pic_from_sys_request_code);
                Intent intent = new Intent();
                intent.setClass(FlashLaunch.this, ImageOperationTypeActivity.class);
                startActivity(intent);
            }

		});

		//时间胶囊的相关按钮
		time_capsule_button=(Button)findViewById(R.id.launch_time_capsule_button);
		time_capsule_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent();
				intent.setClass(FlashLaunch.this, TimeCapsuleMainActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * 处理由照相或者从相册中选择图片的返回界面
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
			if (resultCode!=RESULT_OK) {
				Toast.makeText(getApplicationContext(),"No photo",Toast.LENGTH_LONG).show();
			return;
		}
		switch (requestCode) {
			case select_pic_from_sys_request_code: {        //由相册返回
				Intent intent=new Intent();
				intent.setData(data.getData());
				intent.setClass(FlashLaunch.this,Image_manipulation_main_activity.class);
				startActivity(intent);
				finish();////////////TODO:暂时是不能返回的。。。？？？？？？

			}
			case get_pic_from_sys_camera_code: {          //由相机返回

			}

		}
	}

	/**
	 * 对于大于屏幕分辨率的照片进行缩放
	 * @param bitmap    改变的图形
	 * @param width      屏幕的宽
	 * @param height	 屏幕的高
	 * @return      Bitmap
	 */
	private Bitmap resizeBitmap (Bitmap bitmap,float width,float height) {
		int w=bitmap.getWidth();
		int h=bitmap.getHeight();

		Matrix matrix=new Matrix();
		float scaleWidth=((float)width/w);
		float scaleHeight=((float)height/h);
		matrix.postScale(scaleWidth,scaleHeight);
		Bitmap bitmap1=Bitmap.createBitmap(bitmap,0,0,w,h,matrix,true);
		return bitmap1;
	}
}
