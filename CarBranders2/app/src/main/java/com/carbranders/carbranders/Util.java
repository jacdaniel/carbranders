package com.carbranders.carbranders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Jacques on 31/01/2016.
 */
public class Util {

    public static void bmp_compress(String filename_in, int width, int height, int quality, String filename_out) throws IOException {
        Bitmap bitmap = null;
        File file = new File(filename_in);

        bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
        Bitmap b2 = bitmap.createScaledBitmap(bitmap, width, height, false);
        FileOutputStream fos = null;
        fos = new FileOutputStream(filename_out);
        b2.compress(Bitmap.CompressFormat.JPEG, quality, fos);
        int i = bitmap.getDensity();
        int j = b2.getDensity();
        //int k = bitmap.getByteCount();
        if ( i + j == 0 )
            fos.flush();
        fos.close();
    }



}
