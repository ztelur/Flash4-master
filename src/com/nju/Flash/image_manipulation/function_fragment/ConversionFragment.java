package com.nju.Flash.image_manipulation.function_fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.nju.Flash.R;
import com.nju.Flash.image_manipulation.Image_manipulation_main_activity;
import com.nju.Flash.image_manipulation.MyManipulationView2;
import com.nju.Flash.image_manipulation.image_util.ImageUtil;

/**
 * For Flash
 *
 * @author 杨涛
 *         On 14-3-22 下午3:58 by IntelliJ IDEA
 */
public class ConversionFragment extends Fragment {
    private Button circular_bead_button = null;
    private Button inverted_image_button = null;
    private Button rotate_button = null;
    private Button overturn_left_right_button = null;
    private Button overturn_top_bottom_button = null;
    private Button soften_button = null;
    private Button sharpen_image_ameliorate_button = null;
    private Button film_button = null;
    private Button back_to_origin_button = null;
    private Image_manipulation_main_activity activity = null;
    private MyManipulationView2 handleView = null;
    private Bitmap originBitmap = null;
    private View view;

    private LinearLayout detail_layout = null;
    private boolean isCircular = false;
    private boolean isRotate = false;

    private View circular_bead_view = null;
    private View rotate_view = null;

    public ConversionFragment(Image_manipulation_main_activity activity) {
        this.activity = activity;
        this.handleView = activity.getView();
        originBitmap = handleView.getFloorBitmap();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.conversion_layout, container, false);
        if (view != null) {
            circular_bead_button = (Button) view.findViewById(R.id.circular_bead_button);
            inverted_image_button = (Button) view.findViewById(R.id.inverted_image_button);
            rotate_button = (Button) view.findViewById(R.id.rotate_button);
            overturn_left_right_button = (Button) view.findViewById(R.id.overturn_left_right_button);
            overturn_top_bottom_button = (Button) view.findViewById(R.id.overturn_top_bottom_button);
            soften_button = (Button) view.findViewById(R.id.soften_button);
            sharpen_image_ameliorate_button = (Button) view.findViewById(R.id.sharpen_image_ameliorate_button);
            film_button = (Button) view.findViewById(R.id.film_button);
            back_to_origin_button = (Button) view.findViewById(R.id.back_to_origin_button);
        }

        detail_layout = ((LinearLayout) getView().findViewById(R.id.detail_operation));

        //圆角
        circular_bead_view = inflater.inflate(R.layout.circular_bead_layout, null);
        Button circular_bead_ok_button = (Button) circular_bead_view.findViewById(R.id.ok_button);
        circular_bead_ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCircularBeadView();
            }
        });
        SeekBar circular_bead_seekBar = (SeekBar) circular_bead_view.findViewById(R.id.seek_bar);
        circular_bead_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            Bitmap temp = null;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                handleView.setFloorBitmap(ImageUtil.getRoundedCornerBitmap(temp, (float) progress));
                refreshView();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                temp = handleView.getFloorBitmap();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                temp = handleView.getFloorBitmap();
            }
        });
        circular_bead_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCircular) {
                    removeCircularBeadView();
                } else {
                    showCircularBeadView();
                }
            }
        });

        //倒影
        inverted_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleView.setFloorBitmap(ImageUtil.createReflectionImageWithOrigin(handleView.getFloorBitmap()));
                refreshView();
            }
        });

        //旋转
        rotate_view = inflater.inflate(R.layout.rotate_layout, null);
        Button rotate_ok_button = (Button) rotate_view.findViewById(R.id.ok_button);
        rotate_ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeRotateView();
            }
        });
        SeekBar rotate_seekBar = (SeekBar) rotate_view.findViewById(R.id.seek_bar);
        rotate_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            Bitmap temp = null;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                handleView.setFloorBitmap(ImageUtil.postRotateBitamp(temp, (float) progress));
                refreshView();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                temp = handleView.getFloorBitmap();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                temp = handleView.getFloorBitmap();
            }
        });
        rotate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRotate) {
                    removeRotateView();
                } else {
                    showRotateView();
                }
            }
        });

        //左右翻转
        overturn_left_right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleView.setFloorBitmap(ImageUtil.reverseBitmap(handleView.getFloorBitmap(), 0));
                refreshView();
            }
        });

        //上下翻转
        overturn_top_bottom_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleView.setFloorBitmap(ImageUtil.reverseBitmap(handleView.getFloorBitmap(), 1));
                refreshView();
            }
        });

        //柔化
        soften_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleView.setFloorBitmap(ImageUtil.blurImageAmeliorate(handleView.getFloorBitmap()));
                refreshView();
            }
        });

        //锐化（拉普拉斯变换）
        sharpen_image_ameliorate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleView.setFloorBitmap(ImageUtil.sharpenImageAmeliorate(handleView.getFloorBitmap()));
                refreshView();
            }
        });

        //底片
        film_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleView.setFloorBitmap(ImageUtil.film(handleView.getFloorBitmap()));
                refreshView();
            }
        });

        //原图
        back_to_origin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleView.setFloorBitmap(originBitmap);
                refreshView();
            }
        });

        return view;
    }

    private void showRotateView() {
        removeAllDetailView();
        isRotate = true;
        detail_layout.addView(rotate_view);
    }

    private void removeRotateView() {
        isRotate = false;
        detail_layout.removeView(rotate_view);
    }

    private void showCircularBeadView() {
        isCircular = true;
        detail_layout.addView(circular_bead_view);
    }

    private void removeCircularBeadView() {
        removeAllDetailView();
        isCircular = false;
        detail_layout.removeView(circular_bead_view);
    }

    private void removeAllDetailView() {
        detail_layout.removeAllViews();
        detail_layout.removeAllViewsInLayout();
    }

    public void refreshView() {
        handleView.refresh();
    }

    public View getView() {
        return view;
    }
}