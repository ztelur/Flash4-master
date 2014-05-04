package com.nju.Flash.image_manipulation.function_fragment;

import android.view.View;
import com.nju.Flash.R;
import com.nju.Flash.image_manipulation.Image_manipulation_main_activity;

/**
 * Created by randy on 14-3-12.
 */
public class FragmentButtonListener implements View.OnClickListener {
    Image_manipulation_main_activity activity=null;

    public FragmentButtonListener(Image_manipulation_main_activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pen_function_button: {
                activity.setTabButton(Function_fragment.pen_function);
            }
        }
    }
}
