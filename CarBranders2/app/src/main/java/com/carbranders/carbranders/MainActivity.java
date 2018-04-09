package com.carbranders.carbranders;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int start0 = 1;
    Config.IDENTITY identity;
    boolean flag_refresh_identity = true;
    private Bitmap mCameraBitmap = null;
    GeoLoc geoloc = null;
    boolean bool_startstop = false;
    Timer timer = null;
    MyTimerTask myTimerTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }




    @Override
    public void onResume()
    {
        if ( start0 == 1 )
        {
            main_init();
            start0 = 0;
        }

        if ( flag_refresh_identity == true )
        {
            identity = IdentityActivity.file_read(Config.IDENTITY_FILENAME);
            TextView t_id = (TextView) findViewById(R.id.id_main),
                    t_camp = (TextView) findViewById(R.id.campain_main);
            t_id.setText(identity.id);
            t_camp.setText(identity.campain);
            flag_refresh_identity = false;
            bool_startstop = false;

            startstop_display(bool_startstop);

            // ImageView iv = (ImageView) findViewById(R.id.iv_startstop);
            // startstop_display(ImageView iv, boolean val)
        }

        super.onResume();
    }

    void main_init() {
        String dir_name = Config.DATA_ROOT + File.separator + Config.DATA_DIRECTORY;
        File dir = new File(dir_name);
        if (dir.exists() == false) {
            dir.mkdir();
        }
        else {

        }
    }

    void startstop_display(boolean val)
    {
        // TextView t = (TextView) findViewById(R.id.startstop);
        // ImageView image = (ImageView) findViewById(R.id.id_main_img);
        ImageView iv = (ImageView) findViewById(R.id.iv_startstop);
        if ( val == false )
        {
            iv.setImageResource(R.mipmap.red_light);
        }
        else {
            iv.setImageResource(R.mipmap.green_light);
            /*
            if ( timer == null) {
                timer = new Timer();
                myTimerTask = new MyTimerTask();
                timer.schedule(myTimerTask, 3000, 10000);
            }
            */
        }

    }

    public void identity_call()
    {
        flag_refresh_identity = true;
        Intent intent = new Intent(MainActivity.this, IdentityActivity.class);
        intent.putExtra("IDENTITY", identity);
        // intent.putExtra("PARAM", param);
        startActivity(intent);
    }

    public void camera_call(int full_scale)
    {
        if ( full_scale == 0 )
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 1777);
        else {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            File file = new File(Config.IMAGE_FS_FILENAME);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(intent, 1777);
        }
    }

    public void picture_send(Bitmap mbitmap)
    {
        // ProgressDialog progressDialog;
        // progressDialog = new ProgressDialog(this);
        // progressDialog.setMessage("Chargement en cours");
        // progressDialog.show();

        ProgressDialog mProgress;
        mProgress = ProgressDialog
                .show(this, "attend",
                        "prog", true, true,
                        new DialogInterface.OnCancelListener() {
                            public void onCancel(DialogInterface pd) {
                                handleOnBackButton();
                            }

                            private void handleOnBackButton() {
                            }
                        });

        if ( Config.FULL_SCALE_CAMERA == 0 ) {
            File file = new File("/sdcard/carbranders/img.jpg");
            if (mbitmap != null) {
                FileOutputStream outStream = null;
                try {
                    outStream = new FileOutputStream(file);
                    if (!mbitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)) {
                        Toast.makeText(this, "Unable to save image to file.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Saved image to: " + file.getPath(), Toast.LENGTH_LONG).show();

                    }
                    outStream.close();
                } catch (Exception e) {
                    Toast.makeText(this, "Unable to save image to file.", Toast.LENGTH_LONG).show();
                }
            }
            // Web_protocol.Web_send("/sdcard/carbranders/img.jpg", "img.jpg");
            // Web_protocol.php_run("10", "img.jpg", "img");
            // byte[] data = fileio.read_array_binary_file("/sdcard/carbranders/img.jpg");
            // Web_protocol.http_binaty_send(identity.id, "img", data);
        }
        else {
            /*
            Bitmap bitmap = null;
            File file = new File(Environment.getExternalStorageDirectory() + "/image_fs.jpg");
            try {
                bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(Environment.getExternalStorageDirectory() + "/image_fs2.jpg");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 5, fos);

            try {
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bitmap.recycle();
            */
            try {
                Util.bmp_compress(Config.IMAGE_FS_FILENAME, Config.IMAGE_WIDTH, Config.IMAGE_HEIGHT, Config.IMAGE_COMPRESSION, Config.IMAGE_LS_FILENAME);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] data = fileio.read_array_binary_file(Config.IMAGE_LS_FILENAME);
            Web_protocol.http_binaty_send(identity.id, "img", data);
            try {
                Web_protocol.send_web2(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // progressDialog.dismiss();
        start_alert("Transfert", "Image transférée", "Retour");
        fileio.file_delete(Config.IMAGE_LS_FILENAME);
        fileio.file_delete(Config.IMAGE_FS_FILENAME);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1777) {
            if (resultCode == RESULT_OK) {
                if ( Config.FULL_SCALE_CAMERA == 0 ) {
                    if (mCameraBitmap != null) {
                        mCameraBitmap.recycle();
                        mCameraBitmap = null;
                    }
                    Bundle extras = data.getExtras();
                    mCameraBitmap = (Bitmap) extras.get("data");
                } else {

                }
                picture_send(mCameraBitmap);
            }
        }
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.identity) {
            flag_refresh_identity = true;
            identity_call();
            return true;
        }

        else if ( id == R.id.picture)
        {
            camera_call(Config.FULL_SCALE_CAMERA);
        }

        if ( id == R.id.pos_send ) {
            ConnectivityManager connMgr =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
            if (activeInfo != null && activeInfo.isConnected()) {
                boolean wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
                boolean mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
                if (wifiConnected || mobileConnected) {
                    Toast.makeText(this, "Envoie.", Toast.LENGTH_LONG).show();
                    byte[] data = fileio.read_array_binary_file(Config.FULL_PATH_DATA_FILENAME);
                    // byte[] data = fileio.read_array_binary_file(Config.IMAGE_LS_FILENAME);
                    Web_protocol.http_binaty_send(identity.id, "gps", data);
                    Toast.makeText(this, "Terminé.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(this, "Pas de réseaux Impossible de transférer les données.", Toast.LENGTH_LONG).show();
                }
            }

        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //return true;
            // byte[] data2 = new byte[1000];
            // byte[] data = fileio.read_array_binary_file(Config.IMAGE_LS_FILENAME);
            // Web_protocol.http_binaty_send("101", "img", data);
            // geoloc = new GeoLoc();
            // LocationManager lm = geoloc_init(geoloc);
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
/*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void start_alert(String title, String msg, String button_label)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setNegativeButton(button_label,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    public LocationManager geoloc_init(GeoLoc geoloc)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            return null;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, Config.GEOLOC_MIN_TIME, Config.GEOLOC_MIN_DIST, geoloc);
        return lm;
    }

    public void startstopOnClick(View view) {
        if ( bool_startstop == false ) {
            if ( is_gps_active() == true ) {
                if ( geoloc == null )
                {
                    geoloc = new GeoLoc();
                    LocationManager lm = geoloc_init(geoloc);
                }
                if ( timer == null )
                {
                    if ( timer == null) {
                        timer = new Timer();
                        myTimerTask = new MyTimerTask();
                        timer.schedule(myTimerTask, Config.TIMER_DELAY, Config.TIMER_PERIOD);
                    }
                }
                bool_startstop = true;
            }
            else
                start_alert("GPS inactif", "Veuillez d'abord activer l'option de géolocalisation de votre appareil", "Retour");
        }
        else
            bool_startstop = false;
        startstop_display(bool_startstop);
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            double[] val = new double[3];
            // if ( bool_startstop == 1 && geoloc.position_updated() )
            if ( bool_startstop == true )
            {
                geoloc.position_get(val);
                fileio.write_array_double_binary_file(Config.FULL_PATH_DATA_FILENAME, val);
                /*
                if ( val[1]!= -1.0 && val[2] != -1.0 )
                    fileio.write_array_double_binary_file(param.root + "/" + param.rep + "/" + param.data_filename, val);
                    // fileio.write_array_double_textfile(param.root + "/" + param.rep + "/" + param.data_filename, val);
                // fileio.write_array_string_textfile(param.root + "/" + param.rep + "/" + param.data_filename, s);
                // t.setText(s);
                */
            }
        }
    }


    private boolean is_gps_active()
    {
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if ( manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) return true;
        else return false;
    }

}
