package com.carbranders.carbranders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Jacques on 31/01/2016.
 */
public class IdentityActivity extends Activity {

    Config.IDENTITY identity;
    // Config.PARAM param;
    // String full_path_identity_filename;
    // MainActivity pparent;
    // TextView parent_t_id, t_camp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identity_activity);
        Intent intent = getIntent();
        identity = (Config.IDENTITY)intent.getSerializableExtra("IDENTITY");
        // param = (Config.PARAM)intent.getSerializableExtra("PARAM");
        field_init(identity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        */
        Config.IDENTITY id;
        id = field_get();
        try {
            file_write(Config.IDENTITY_FILENAME, id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // parent_t_id.setText(id.id);
        super.onBackPressed();
    }

    void field_init(Config.IDENTITY identity)
    {
        EditText t;

        t = (EditText) findViewById(R.id.id_id); t.setText(identity.id);
        t = (EditText) findViewById(R.id.id_camp); t.setText(identity.campain);
    }

    Config.IDENTITY field_get()
    {
        Config.IDENTITY id = new Config.IDENTITY();
        EditText t;

        t = (EditText) findViewById(R.id.id_id); id.id = t.getText().toString();
        t = (EditText) findViewById(R.id.id_camp); id.campain = t.getText().toString();
        return id;
    }

    static Config.IDENTITY file_read(String filename)
    {
        Config.IDENTITY identity = new Config.IDENTITY();
        FileReader reader = null;

        File pfile = new File(filename);
        if ( !pfile.exists() )
            return identity;

        try {
            reader = new FileReader(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = null;

        try {
            line = bufferedReader.readLine();
            identity.id = line;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            line = bufferedReader.readLine();
            identity.campain = line;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return identity;
    }


    void file_write(String filename, Config.IDENTITY identity) throws IOException {
        FileWriter writer = new FileWriter(filename, false);

        if ( writer == null ) return;
        writer.write(identity.id);
        writer.write("\r\n");   // write new line
        writer.write(identity.campain);
        writer.write("\r\n");   // write new line
        writer.close();
    }


}
