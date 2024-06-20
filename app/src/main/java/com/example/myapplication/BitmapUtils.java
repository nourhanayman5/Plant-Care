package com.example.myapplication;

import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;

public class BitmapUtils {
    public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
