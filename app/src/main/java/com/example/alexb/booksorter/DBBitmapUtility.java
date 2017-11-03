package com.example.alexb.booksorter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

/**
 * Created by alexb on 01/07/2017.
 */

public class DBBitmapUtility {


    //convert from bitmap to bytearray

    public static byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);

        return stream.toByteArray();

    }

    //convert from byte array to bitmap

    public static Bitmap getImage(byte[] image){
        return BitmapFactory.decodeByteArray(image,0,image.length);
    }
}
