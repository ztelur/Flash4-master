package com.nju.Flash.time_capsule;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import com.nju.Flash.R;
import com.nju.Flash.time_capsule.exception.EmptyFileException;
import com.nju.Flash.time_capsule.exception.FileReadFailException;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * For SampleRecordForPhoto
 *
 * @author 杨涛
 *         On 14-3-3 上午8:17 by IntelliJ IDEA
 */
public class TimeCapsuleService extends Service {
    private static int count = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getTimeList();
        return super.onStartCommand(intent, flags, startId);
    }

    private void getTimeList() {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        dirPath += "/flash/record/";
        File file = new File(dirPath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            String[] temp;
            Timer timer;
            if (files != null && files.length != 0)
                for (File f : files)
                    if (f.getName().endsWith(".txt") && f.isFile())
                        try {
                            temp = IOHelper.read(f);
                            if (!Boolean.valueOf(temp[IOHelper.IS_OPENED_CONTENT])) {
                                timer = new Timer(temp[IOHelper.TITLE_CONTENT]);
                                timer.schedule(task(temp[IOHelper.TITLE_CONTENT], temp[IOHelper.TEXT_CONTENT], f.getName()), timeSplit(temp[IOHelper.TIME_CONTENT]));
                            }
                        } catch (FileReadFailException e) {
                            e.printStackTrace();
                        } catch (EmptyFileException e) {
                            e.printStackTrace();
                        }
        }
    }

    private Date timeSplit(String time) {
        Date date = new Date();
        int temp = time.indexOf(Strings.YEAR);
        date.setYear(Integer.parseInt(time.substring(0, temp)) - 1900);
        int pre = temp;
        temp = time.indexOf(Strings.MONTH);
        date.setMonth(Integer.parseInt(time.substring(pre + 1, temp)) - 1);
        pre = temp;
        temp = time.indexOf(Strings.DAY);
        date.setDate(Integer.parseInt(time.substring(pre + 1, temp)));
        pre = temp;
        temp = time.indexOf(Strings.HOUR);
        date.setHours(Integer.parseInt(time.substring(pre + 1, temp)));
        pre = temp;
        temp = time.indexOf(Strings.MINUTE);
        date.setMinutes(Integer.parseInt(time.substring(pre + 1, temp)));
        date.setSeconds(0);
        return date;
    }

    private TimerTask task(final String name, final String text, final String fileName) {
        return new TimerTask() {
            @Override
            public void run() {
                notification(name, text, fileName);
            }
        };
    }

    private void notification(String name, String text, String fileName) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.time_capsule, Strings.TIME_CAPSULE, System.currentTimeMillis());

        //定义下拉通知栏时要展现的内容信息
        Context context = getApplicationContext();
        CharSequence contentTitle = Strings.YOURS + name + Strings.READY_TO_GO;
        Intent notificationIntent = new Intent(this, ShowTimeCapsuleActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, text,  contentIntent);

        ShowTimeCapsuleActivity.initialize(fileName);
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_LIGHTS;

        //用mNotificationManager的notify方法通知用户生成标题栏消息通知
        mNotificationManager.notify(count++, notification);
    }
}
