package com.nju.Flash.image_manipulation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.nju.Flash.R;

/**
 * Created by randy on 14-3-17.
 */
public class ImageOperationTypeActivity extends Activity {
    //照片对象
    private Bitmap myBitmap;

    private final static int select_pic_from_sys_request_code = 1;
    private final static int get_pic_from_sys_camera_code = 2;

    private Button openImageFromGalleryButton = null;
    private Button takenByCameraButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_operation_type_activity_layout);
        init();
    }

    private void init() {
        openImageFromGalleryButton = (Button) findViewById(R.id.openImageFromGalleryButton);
        openImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, select_pic_from_sys_request_code);

            }
        });

        //TODO:从照相机获得
        takenByCameraButton = (Button) findViewById(R.id.takenByCameraButton);
        takenByCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                //调用系统拍照功能
                Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(getImageByCamera, get_pic_from_sys_camera_code);
            }

        });
    }

    /**
     * 处理由照相或者从相册中选择图片的返回界面
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Toast.makeText(getApplicationContext(), "No photo", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode) {
            case select_pic_from_sys_request_code: {        //由相册返回
                Intent intent = new Intent();
                intent.setFlags(select_pic_from_sys_request_code);
                intent.setData(data.getData());
                intent.setClass(ImageOperationTypeActivity.this, Image_manipulation_main_activity.class);
                startActivity(intent);
                finish();////////////TODO:暂时是不能返回的。。。？？？？？？

            }
            case get_pic_from_sys_camera_code: {          //由相机返回
                try {
                    super.onActivityResult(requestCode, resultCode, data);
                    //获取拍照图片
                    Bundle extras = data.getExtras();
                    //将图片转化为Bitmap
                    myBitmap = (Bitmap) extras.get("data");
                    Intent intent = new Intent();
//                    intent.put
                    intent.putExtra("image", myBitmap);
                    intent.setFlags(get_pic_from_sys_camera_code);
                    // intent.putExtra("image",extras);
                    intent.setClass(ImageOperationTypeActivity.this, Image_manipulation_main_activity.class);
                    startActivity(intent);


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
    }
}
