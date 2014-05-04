package com.nju.Flash.image_manipulation;

/**
 * Created by randy on 14-2-28.
 */
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class Image {
    private static Image photo;
    private static Uri photoUri;
    private static String name;
    private static Activity activity;

    private Image(Uri uri) {
        Image.photoUri = uri;
        Image.name = getPhotoName();
    }

    public  String getPhotoName() {
        String[] filePathColumns = { MediaStore.Images.Media.DATA };
        Cursor c = activity.getContentResolver().query(photoUri, filePathColumns, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePathColumns[0]);
        String picturePath = c.getString(columnIndex);
        String pathSplit[] = picturePath.split("/");
        String fileName = pathSplit[pathSplit.length-1];
        fileName = fileName.substring(0,fileName.lastIndexOf("."));
        return fileName;
    }
    public String getPhotoPath() {
        String[] filePathColumns = { MediaStore.Images.Media.DATA };
        Cursor c = activity.getContentResolver().query(photoUri, filePathColumns, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePathColumns[0]);
        String picturePath = c.getString(columnIndex);
        return picturePath;
    }

    public static void createPhoto(Uri uri) {
        photo = new Image(uri);
    }

    public static Image getInstance() {
        if (photo == null) {
            createPhoto(Uri.parse("/res/drawable/noPhoto.jpg"));
        }
        return photo;
    }

    public static Uri getUri() {
        return photoUri;
    }

    public static void setActivity(Activity activity) {
        Image.activity = activity;
    }

    public static String getName(){
        return name;
    }
}