package com.carbranders.carbranders;

import android.os.Environment;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Jacques on 31/01/2016.
 */
public class Config {

    public static final String DATA_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String DATA_DIRECTORY = "carbranders";
    public static final String DATA_FILENAME = "data.bin";

    public static final String IDENTITY_FILENAME  = DATA_ROOT + File.separator + DATA_DIRECTORY + File.separator + "identity.txt";
    public static final String FULL_PATH_DATA_FILENAME = DATA_ROOT + File.separator + DATA_DIRECTORY + File.separator + DATA_FILENAME;

    public static final String WEB_ROOT = "http://daniel.enseirb.free.fr/carbranders/reception.php";
    public static final long DATASIZE = 100000;
    public static final String WEB_ROOT2 = "http://daniel.enseirb.free.fr/carbranders/reception_blb.php";
    public static final String WEB_ROOT3 = "http://daniel.enseirb.free.fr/carbranders/android2database.php";

    public static final int FULL_SCALE_CAMERA = 1;
    public static final int IMAGE_WIDTH = 800;
    public static final int IMAGE_HEIGHT = 600;
    public static final int IMAGE_COMPRESSION = 50;
    public static final String IMAGE_FS_FILENAME = DATA_ROOT + File.separator + DATA_DIRECTORY + File.separator + "img_fs.jpg";
    public static final String IMAGE_LS_FILENAME = DATA_ROOT + File.separator + DATA_DIRECTORY + File.separator + "img_fs2.jpg";

    public static final long GEOLOC_MIN_TIME = 25000;
    public static final float GEOLOC_MIN_DIST = 0;

    public static final long TIMER_DELAY = 3000;
    public static final long TIMER_PERIOD = 30000;


    public static final String IMGFILENAME = "img.jpg";

    static class IDENTITY implements Serializable {
        String first_name, name, address, id, campain, phone, email;
    }

    static class PARAM implements Serializable
    {
        String root, rep, identity_filename, data_filename;
        int geoloc_minTime;
        float geoloc_minDistance;
    }

    public static PARAM param_init()
    {
        PARAM param = new PARAM();

        param.root = "/sdcard";
        param.rep = "carbranders";
        // param.identity_filename = "identity.txt";
        param.data_filename = "data.bin";

        param.geoloc_minTime = 1000;
        param.geoloc_minDistance = 0.0f;

        return param;
    }

    public static IDENTITY identity_init()
    {
        IDENTITY identity = new IDENTITY();
        identity.address = "";

        return identity;
    }

    }
