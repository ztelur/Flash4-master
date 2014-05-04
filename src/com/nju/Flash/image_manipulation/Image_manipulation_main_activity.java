package com.nju.Flash.image_manipulation;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.nju.Flash.R;
import com.nju.Flash.image_manipulation.MyLayout.SlidingLayout;
import com.nju.Flash.image_manipulation.function_fragment.*;


/**
 * Created with IntelliJ IDEA.
 * User: Thunder
 * Date: 14-2-26
 * Time: 上午10:25
 * To change this template use File | Settings | File Templates.
 */
public class Image_manipulation_main_activity
        extends Activity implements View.OnClickListener {

    //for test
    private MyManipulationView2 myView=null;
    //
    private FragmentManager fragmentManager=null;
    private Intent photoUriIntent=null;//启动此activity的Intent,内有photo的地址信息
	private LinearLayout LL_show=null;
    private Image photo=null;//要进行操作的图片
	private boolean isBig=false;//图片是否大于屏幕分辨率
    private boolean isEraser=false;//
    //下边是activity的各个组件
    private SlidingLayout slidingLayout=null;
    private ImageView showImageView=null;
    private Button pen_Start_Button=null;
    private Button cutoff_Start_Button=null;//
    private Button eraserButton=null;//橡皮或画笔
    private Button cleanAllButton=null;//清空
    private Button saveButton=null;//保存
    private Button showButton=null;//显示

    //////fragment
    private Fragment pen_Function_Fragment=null;
    private Fragment conversionFragment = null;
    private Fragment attri_Function_Fragment=null;
    //记录当前fragment的类型
    private Function_fragment current_Fragment = Function_fragment.pen_function;

    //slideLayout button

    /**
	 *
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_handle);
		photoUriIntent=getIntent();
        fragmentManager=getFragmentManager();
        init();
		



	}

    /**
     * 处理整个activity的图片和按钮的初始化问题
     */
    private void init(){

        initMyView();
        initSlideLayout();
        // initFragment();
        initSlideButton();
        initPhoto(photoUriIntent);
        initPenFunctionButton();
        initConversionButton();


    }
//    private void initFragment() {
//        FrameLayout frameLayout=
//    }

    /**
     *
     */
    private void initSlideLayout() {
        slidingLayout=(SlidingLayout)findViewById(R.id.slidingLayout);
        slidingLayout.setScrollEvent(myView);
    }
    private Button pen_Function_Button;
    private Button attribute_Function_Button;
    private Button conversionButton;
    private Button hahajingButton;
    private void initSlideButton() {
        pen_Function_Button = (Button) findViewById(R.id.pen_function_button);
        pen_Function_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Image_manipulation_main_activity.this.initPenFunctionButton();
            }
        });
        attribute_Function_Button = (Button) findViewById(R.id.attribute_function_button);
        attribute_Function_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Image_manipulation_main_activity.this.initAttributeFunctionButton();
            }
        });
        conversionButton = (Button) findViewById(R.id.conversion_button);
        conversionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Image_manipulation_main_activity.this.initConversionButton();
            }
        });
        hahajingButton=(Button)findViewById(R.id.hahajing_button);
        hahajingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Image_manipulation_main_activity.this.initHahajingButton();
            }
        });

    }


    /**
	 * 形成菜单栏
	 * @param menu
	 * @return
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.image_handle_activity_menu, menu);
		return true;
	}

	/**
	 * 处理菜单栏被选中的action
	 * @param item
	 * @return
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.image_handle_net_share: {
				//TODO:杨松来写
			}
			default:
				return super.onOptionsItemSelected(item);
		}

	}

	/**
	 *
	 * @param intent
	 */
	private void initPhoto(Intent intent) {

        photo.setActivity(this);
        if (intent.getFlags() == 1) {
            photo.createPhoto(intent.getData());
            myView.setImageUri(photo.getUri());
        } else if (intent.getFlags() == 2) {
            Toast.makeText(getApplicationContext(), "ddd", Toast.LENGTH_LONG).show();
            //Bundle extras =intent.getExtras();
            //将图片转化为Bitmap
            myView.setImage(intent);
//             = (Bitmap) extras.get("data");
        }

    }

    /**
     *
     */
    private void initMyView() {

        //设置myview的属性


        myView=new MyManipulationView2(getBaseContext());
//        myView.setBackground(drawable.bor);
        myView.setBackgroundColor(Color.TRANSPARENT);
        //将view加入布局中
        LL_show=(LinearLayout)findViewById(R.id.ll_show);
        LL_show.removeAllViews();
        LL_show.addView(myView);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.cut_off_start_button:// 画图形状的选择

                selectShape();
                break;
            case R.id.pen_start_button:

                eraser();

        }
    }
    public void eraser() {
        if(isEraser) {
            //当前显示为“画笔"
            //调用view的setDrawTool()方法
            myView.setDrawTool(OperationEnum.pen);
            //点击后设置按钮为“橡皮"
            pen_Start_Button.setText("画笔");
            isEraser=false;
        }else {
            //当前显示为"橡皮"
            myView.setDrawTool(OperationEnum.eraser);
            pen_Start_Button.setText("橡皮");
            isEraser=true;
        }
    }
    /*
	 * 选择形状
	 */
    public void selectShape() {
        final String[] mItems = { "直线", "矩形", "圆形", "三角形", "立方体", "圆柱体", "涂鸦" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Image_manipulation_main_activity.this);
        builder.setTitle("选择形状");

        builder.setItems(mItems, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //调用方法setDrawTool（）进行相应的实例化
                myView.setDrawTool(OperationEnum.getEnum(which));  ///TODO:???????
                Toast.makeText(getApplicationContext(),
                        "选择了: " + mItems[which], Toast.LENGTH_SHORT).show();

                // 如果选择了图形，则将按钮eraserButton设置显示为“橡皮”
//                eraserButton.setText("橡皮");
                    //TODO:?????
                isEraser = false;

            }

        }).setIcon(R.drawable.ic_launcher);

        builder.create().show();
    }
    public void setTabButton(Function_fragment function_fragment) {
        // 每次选中之前先清楚掉上次的选中状态
//        clearSelection();
        switch (function_fragment) {
            case pen_function: {
                initPenFunctionButton();
            }
            case attribute_function: {
                initAttributeFunctionButton();
            }
        }
    }
    private void initPenFunctionButton() {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Log.v("erro", "do it");
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        pen_Function_Fragment = new Pen_Function_Fragment(this);
        transaction.replace(R.id.content, pen_Function_Fragment);
        transaction.commit();
        // transaction.show(pen_Function_Fragment);
    }
//        pen_Start_Button=(Button)findViewById(R.id.pen_start_button);
//
//        pen_Start_Button.setOnClickListener(this);
//        cutoff_Start_Button=(Button)findViewById(R.id.cut_off_start_button);
////        cutoff_Start_Button.setOnClickListener(this);
//        cutoff_Start_Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //实现点击一下menu展示左侧布局，再点击一下隐藏左侧布局的功能
//                if (slidingLayout.isLeftLayoutVisible()) {
//                    slidingLayout.scrollToRightLayout();
//                } else {
//                    slidingLayout.scrollToLeftLayout();
//                }
//            }
//        });


    private void initAttributeFunctionButton() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment attrubuteFragment = new Attribute_Function_Fragment(this);
        transaction.replace(R.id.content, attrubuteFragment);
        transaction.commit();
    }

    /**
     * @return
     */
    public MyManipulationView2 getView() {
        return myView;
    }

    /**
     *
     * @return
     */
    public Image getPhoto() {
        return photo;
    }
    private FilterFragment hahajingFragment;
    private void initHahajingButton() {
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        hahajingFragment=new FilterFragment(this);
        transaction.replace(R.id.content,hahajingFragment);
        transaction.commit();
    }
    private void initConversionButton() {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        conversionFragment = new ConversionFragment(this);
        transaction.replace(R.id.content, conversionFragment);
        transaction.commit();
    }
}
