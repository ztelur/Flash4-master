package com.nju.Flash.image_manipulation.function_fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.nju.Flash.R;
import com.nju.Flash.image_manipulation.Image_manipulation_main_activity;
import com.nju.Flash.image_manipulation.MyManipulationView2;
import com.nju.Flash.image_manipulation.filter.*;

/**
 * Created by randy on 14-5-4.
 */
public class FilterFragment extends Fragment {
    private Image_manipulation_main_activity activity=null;
    private MyManipulationView2 handleView=null;
    public FilterFragment(Image_manipulation_main_activity activity) {
        this.activity = activity;
        handleView=activity.getView();

    }

    private Button hahajingButton;
    private Button biggerFilterButton;
    private Button magicMirorButton;
    private Button swirlButton;
    private Button waterButton;
    private HahajingFilter hahajingFilter;
    private BiggerFilter biggerFilter;
    private MagicMirrorFilter magicMirrorFilter;
    private SwirlFilter swirlFilter;
    private WaterFilter waterFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.filterfragmentlayout,container,false);
        hahajingButton=(Button)view.findViewById(R.id.hahajing_button);
        hahajingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hahajingFilter=new HahajingFilter();
                Bitmap originbitmap=handleView.getFloorBitmap();
                //获得图片的中心点
                int width=originbitmap.getWidth();
                int height=originbitmap.getHeight();


                Bitmap resultBitmap=hahajingFilter.filter(originbitmap,width/2,height/2);

                handleView.setImageInBitmap(resultBitmap);
                handleView.refresh();


            }
        });
        biggerFilterButton=(Button)view.findViewById(R.id.bigger_Button);
        biggerFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biggerFilter=new BiggerFilter();
                Bitmap originbitmap=handleView.getFloorBitmap();
                //获得图片的中心点
                int width=originbitmap.getWidth();
                int height=originbitmap.getHeight();

                Bitmap resultBitmap=biggerFilter.filter(originbitmap,width/2,height/2);
                handleView.setImageInBitmap(resultBitmap);
                handleView.refresh();
            }
        });
        magicMirorButton=(Button)view.findViewById(R.id.magicMirror_Button);
        magicMirorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                magicMirrorFilter=new MagicMirrorFilter();
                Bitmap originbitmap=handleView.getFloorBitmap();
                //获得图片的中心点
                int width=originbitmap.getWidth();
                int height=originbitmap.getHeight();

                Bitmap resultBitmap=magicMirrorFilter.filter(originbitmap,width/2,height/2);
                handleView.setImageInBitmap(resultBitmap);
                handleView.refresh();
            }
        });
        swirlButton=(Button)view.findViewById(R.id.swirl_Button);
        swirlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swirlFilter=new SwirlFilter();
                Bitmap originbitmap=handleView.getFloorBitmap();
                //获得图片的中心点
                int width=originbitmap.getWidth();
                int height=originbitmap.getHeight();

                Bitmap resultBitmap=swirlFilter.filter(originbitmap);
                handleView.setImageInBitmap(resultBitmap);
                handleView.refresh();
            }
        });
        waterButton=(Button)view.findViewById(R.id.water_Button);
        waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waterFilter=new WaterFilter();
                Bitmap originbitmap=handleView.getFloorBitmap();
                //获得图片的中心点
                int width=originbitmap.getWidth();
                int height=originbitmap.getHeight();

                Bitmap resultBitmap=waterFilter.filter(originbitmap);

                handleView.setImageInBitmap(resultBitmap);
                handleView.refresh();
            }
        });

        return view;
    }

}
