package com.nju.Flash.time_capsule;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.nju.Flash.R;
import com.nju.Flash.app_main.FlashLaunch;
import com.nju.Flash.time_capsule.buttons.OpenButtonArray;
import com.nju.Flash.time_capsule.content.*;
import com.nju.Flash.time_capsule.exception.ContentErrorException;
import com.nju.Flash.time_capsule.exception.EmptyFileException;
import com.nju.Flash.time_capsule.exception.FileReadFailException;

import java.io.File;

public class TimeCapsuleActivity extends Activity {
    private LayoutInflater inflater;

    private OpenButtonArray openButtonArray = null;
    private ContentManager contentManager = null;
    private ImageView photoImageView = null;
    private TitleBar titleBar = null;
    private Content titleContent = null;
    private Content textContent = null;
    private Content timeContent = null;
    private Content recordContent = null;


    private Dialog exitDialog;
    private Dialog timePickerDialog;
    ///TODO: for test
    boolean test=true;

    private int year = -1;
    private int month = -1;
    private int day = -1;
    private int hourOfDay = -1;
    private int minute = -1;

    private static Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimeCapsuleActivity.display = getWindowManager().getDefaultDisplay();

        setContent();

        startSystemAlbum();
        if(test) {
            year=2014;
            month=5;
            day=3;
            hourOfDay=11;
            minute=12;
        }
    }

    private void setContent() {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        ActionBar actionBar = this.getActionBar();
        actionBar.setCustomView(R.layout.time_capsule_titlebar);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.show();
        setContentView(R.layout.time_capsule_set_up_activity);
    }

    private void startSystemAlbum() {
        Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && null != data) {
            initializePhoto(data.getData());
            initializeComponent();//初始化界面部件
            check();//检查照片是否已存在相关时间胶囊
        } else {
            makeToast("未选择图片");
        }
    }

    private void initializeComponent() {
        inflater = (LayoutInflater) TimeCapsuleActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

        openButtonArray = OpenButtonArray.getInstance(this);
        contentManager = ContentManager.getInstance(this);
        titleBar = TitleBar.getInstance(this);
        photoImageView = (ImageView) findViewById(R.id.time_capsule_image_view);

        titleContent = contentManager.getContent(ContentOrder.TITLE);
        textContent = contentManager.getContent(ContentOrder.TEXT);
        timeContent = contentManager.getContent(ContentOrder.TIME);
        recordContent = contentManager.getContent(ContentOrder.RECORD);

        showPhoto();
    }

    private void showPhoto() {
        try {
            photoImageView.setImageBitmap(Photo.thumb());
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "No photoView", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void check() {
        File recordFile = new File(Record.getInstance(this).getFilePath());
        if (recordFile.exists()) {
            IOHelper.initializeIOHelper();
            try {
                String[] content = IOHelper.read();
                titleContent.setText(content[IOHelper.TITLE_CONTENT]);
                textContent.setText(content[IOHelper.TEXT_CONTENT]);
                timeContent.setText(content[IOHelper.TIME_CONTENT] + Strings.CLICK_TO_CHANGE);
                timeSplit(content[IOHelper.TIME_CONTENT]);
                Record.isRecord = true;
            } catch (FileReadFailException e) {
                makeToast(Strings.FILE_READ_FAILED);
            } catch (EmptyFileException e) {
            }
        }
    }

    private void timeSplit(String time) {
        int temp = time.indexOf(Strings.YEAR);
        year = Integer.parseInt(time.substring(0, temp));
        int pre = temp;
        temp = time.indexOf(Strings.MONTH);
        month = Integer.parseInt(time.substring(pre + 1, temp));
        pre = temp;
        temp = time.indexOf(Strings.DAY);
        day = Integer.parseInt(time.substring(pre + 1, temp));
        pre = temp;
        temp = time.indexOf(Strings.HOUR);
        hourOfDay = Integer.parseInt(time.substring(pre + 1, temp));
        pre = temp;
        temp = time.indexOf(Strings.MINUTE);
        minute = Integer.parseInt(time.substring(pre + 1, temp));
    }

    private void setTime(int hourOfDay, int minute) {
        timeContent.setText(timeContent.getText() + hourOfDay + "点" + minute + "分(点击修改)");
        this.hourOfDay = hourOfDay;
        this.minute = minute;
    }

    private void setDate(int year, int monthOfYear, int dayOfMonth) {
        timeContent.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
        this.year = year;
        this.month = monthOfYear + 1;
        this.day = dayOfMonth;
    }

    public void saveListener() {
        IOHelper.initializeIOHelper();
        String time = year + Strings.YEAR + month + Strings.MONTH + day + Strings.DAY + hourOfDay + Strings.HOUR + minute + Strings.MINUTE;
        String title = titleContent.getText();
        String text = textContent.getText();
        if (title == null || title.equals("") || title.equals(Strings.INPUT_TITLE)) {
            makeToast(Strings.NO_TITLE);
        } else if (text == null || text.equals("") || text.equals(Strings.INPUT_TEXT)) {
            makeToast(Strings.NO_TEXT);
        } else if (year == -1 || month == -1 || day == -1 || hourOfDay == -1 || minute == -1) {
            makeToast(Strings.NO_TIME);
        } else if (!Record.isRecord) {
            makeToast(Strings.NO_RECORD);
        } else {
            try {
                String[] content = {Boolean.FALSE.toString(), title, text, Photo.getUri().toString(), time};
                IOHelper.write(content);
                makeToast("保存成功");
                stopService(FlashLaunch.timeServiceIntent);
                startService(FlashLaunch.timeServiceIntent);
                this.finish();
            } catch (ContentErrorException e) {
                makeToast("文件写入失败，我劝你还是放弃吧！");
            }
        }
    }

    private void initializePhoto(Uri uri) {
        Photo.setActivity(this);
        Photo.createPhoto(uri);
    }

    public void timePickerDialog() {
        timePickerDialog = new Dialog(this, R.style.FullHeightDialog);
        timePickerDialog.setContentView(R.layout.time_capsule_time_picker_dialog);
        timePickerDialog.setCanceledOnTouchOutside(false);

        final DatePicker datePicker = (DatePicker) timePickerDialog.findViewById(R.id.datePicker);
        final TimePicker timePicker = (TimePicker) timePickerDialog.findViewById(R.id.timePicker);
        Button button = (Button) timePickerDialog.findViewById(R.id.ok_button);

        datePicker.setScrollContainer(true);
        timePicker.setScrollContainer(true);
        timePicker.setIs24HourView(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                setTime(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                timePickerDialog.dismiss();
            }
        });

        timePickerDialog.show();
    }

    public void exitDialog() {
        exitDialog = new Dialog(this, R.style.FullHeightDialog);
        exitDialog.setContentView(R.layout.time_capsule_exit_dialog);

        Button OKButton = (Button) exitDialog.findViewById(R.id.ok_button);
        Button cancelButton = (Button) exitDialog.findViewById(R.id.cancel_button);

        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog.dismiss();
                if (Record.getInstance((TimeCapsuleActivity.this)).isPlaying())
                    Record.getInstance(TimeCapsuleActivity.this).stop();
                TimeCapsuleActivity.this.finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog.dismiss();
            }
        });
        exitDialog.show();
    }

    public static Display getDisplay() {
        return TimeCapsuleActivity.display;
    }

    private void makeToast(String text) {
        Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void changeToView(int flag) {
        contentManager.changeToContent(flag);
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitDialog();
        }
        return false;
    }
}
