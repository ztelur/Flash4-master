package com.nju.Flash.image_manipulation.function_fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.nju.Flash.R;
import com.nju.Flash.image_manipulation.Image_manipulation_main_activity;
import com.nju.Flash.image_manipulation.MyManipulationView2;
import com.nju.Flash.image_manipulation.OperationEnum;

/**
 * Created by randy on 14-3-12.
 */
public class Pen_Function_Fragment extends Fragment {
    private Button pen_Start_Button=null;
    private Button cutoff_Start_Button=null;
    private Image_manipulation_main_activity activity=null;
    private MyManipulationView2 handleView = null;
    private boolean isEraser = false;

    public Pen_Function_Fragment(Image_manipulation_main_activity activity) {
        this.activity = activity;
        this.handleView = activity.getView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        pen_Start_Button=(Button)findViewById(R.id.pen_start_button);
//
//        pen_Start_Button.setOnClickListener(this);
//        cutoff_Start_Button=(Button)findViewById(R.id.cut_off_start_button);
//        cutoff_Start_Button.setOnClickListener(this);
//        cutoff_Start_Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                实现点击一下menu展示左侧布局，再点击一下隐藏左侧布局的功能
//                if (slidingLayout.isLeftLayoutVisible()) {
//                    slidingLayout.scrollToRightLayout();
//                } else {
//                    slidingLayout.scrollToLeftLayout();
//                }
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pen_function_layout, container, false);
        pen_Start_Button = (Button) view.findViewById(R.id.pen_start_button);

        pen_Start_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pen_Function_Fragment.this.eraser();
            }
        });
        cutoff_Start_Button = (Button) view.findViewById(R.id.cut_off_start_button);
        cutoff_Start_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pen_Function_Fragment.this.selectShape();
            }
        });


        return view;
    }

    /*
    * 选择形状
    */
    private void selectShape() {
        final String[] mItems = {"直线", "矩形", "圆形", "三角形", "立方体", "圆柱体", "涂鸦"};

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("选择形状");

        builder.setItems(mItems, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //调用方法setDrawTool（）进行相应的实例化
                handleView.setDrawTool(OperationEnum.getEnum(which));  ///TODO:???????


                // 如果选择了图形，则将按钮eraserButton设置显示为“橡皮”
//                eraserButton.setText("橡皮");
                //TODO:?????
                //isEraser = false;

            }

        }).setIcon(R.drawable.ic_launcher);

        builder.create().show();
    }

    public void eraser() {
        if (isEraser) {
            //当前显示为“画笔"
            //调用view的setDrawTool()方法
            handleView.setDrawTool(OperationEnum.pen);
            //点击后设置按钮为“橡皮"
            pen_Start_Button.setText("画笔");
            isEraser = false;
        } else {
            //当前显示为"橡皮"
            handleView.setDrawTool(OperationEnum.eraser);
            pen_Start_Button.setText("橡皮");
            isEraser = true;
        }
    }
}
