package com.nju.Flash.image_manipulation.function_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.nju.Flash.R;
import com.nju.Flash.image_manipulation.Image;
import com.nju.Flash.image_manipulation.Image_manipulation_main_activity;
import com.nju.Flash.image_manipulation.MyManipulationView2;
import com.nju.Flash.image_manipulation.tone_adjustment.Tone;

import java.util.ArrayList;

/**
 * Created by randy on 14-3-12.
 */
public class Attribute_Function_Fragment extends Fragment {
    //    private View messageLayout=null;
    private Image_manipulation_main_activity activity = null;
    private MyManipulationView2 myView = null;
    private Tone mToneLayer;
    private Image photo = null;

    public Attribute_Function_Fragment(Image_manipulation_main_activity activity) {
        this.photo = activity.getPhoto();
        this.myView = activity.getView();
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.attribute_function_layout, container, false);


        mToneLayer = new Tone(activity);
        myView.setToneAndPhoto(mToneLayer, photo.getUri());
        ((LinearLayout) messageLayout.findViewById(R.id.tone_view)).addView(mToneLayer.getParentView());

        ArrayList<SeekBar> seekBars = mToneLayer.getSeekBars();
        for (int i = 0, size = seekBars.size(); i < size; i++) {
            seekBars.get(i).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    int flag = (Integer) seekBar.getTag();
                    switch (flag) {
                        case Tone.FLAG_SATURATION:
                            mToneLayer.setSaturation(i);
                            Log.i("Tone_Test", "1. FLAG_SATURATION set to " + i);
                            break;
                        case Tone.FLAG_LUM:
                            mToneLayer.setLum(i);
                            Log.i("Tone_Test", "1. FLAG_LUM set to " + i);
                            break;
                        case Tone.FLAG_HUE:
                            mToneLayer.setHue(i);
                            Log.i("Tone_Test", "1. FLAG_HUE set to " + i);
                            break;
                    }

                    myView.toneChange(flag);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }
        return messageLayout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}
