package com.carbranders.carbranders;

import android.os.StrictMode;
import android.util.Base64;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacques on 01/02/2016.
 */
public class Web_protocol {

    void data_clear(String filename) {

    }

    static void data_send(String filename, byte[] data) {
        // String str = data.toString();
        String str = null;
        str = Base64.encodeToString(data, Base64.DEFAULT);
/*
        try {
            str = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
*/
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(Config.WEB_ROOT);
        List<NameValuePair> donnees = new ArrayList<NameValuePair>(1);
        donnees.add(new BasicNameValuePair("filename", filename));
        donnees.add(new BasicNameValuePair("message", str));
        try {
            post.setEntity(new UrlEncodedFormEntity(donnees));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        try {
            client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    /*
    static void file_send(String filename_in, String filename_out)
    {
        File f = new File(filename_in);
        FileReader fr = null;
        long length = f.length(), nloops, n, size;
        String ss;

        nloops = length/Config.DATASIZE+1;

        try {
            fr = new FileReader (f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        for (n=0; n<nloops; n++)
        {
            size = length - n*Config.DATASIZE;
            size = Math.min(size, Config.DATASIZE);
            char []data = new char[(int)size];

            try {
                fr.read(data);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if ( nloops == 1 )
                ss = filename_out;
            else
                ss = "datax"+n+".jpg";
            data_send(ss, data);
        }
    }
    */

    /*
    static void Web_send(String filename_in, String filename_out)
    {
        // String filename = "/sdcard/carbranders/data.txt";
        File f = new File(filename_in);
        FileReader fr = null;
        long length = f.length();
        char []data = new char[(int)length];
        try {
            fr = new FileReader (f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        try {
            fr.read(data);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        data_send(filename_out, data);
    }
*/

    static void Web_send(String filename_in, String filename_out) {
        byte[] data;
        data = fileio.read_array_binary_file(filename_in);
        data_send(filename_out, data);
    }


    static void php_run(String id, String filename, String type) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(Config.WEB_ROOT2);
        List<NameValuePair> donnees = new ArrayList<NameValuePair>(1);
        donnees.add(new BasicNameValuePair("id", id));
        donnees.add(new BasicNameValuePair("filename", filename));
        donnees.add(new BasicNameValuePair("type", type));
        try {
            post.setEntity(new UrlEncodedFormEntity(donnees));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        try {
            client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    static void http_binaty_send(String no, String type, byte[] data) {
        if ( data == null ) return;
        String data_str = Base64.encodeToString(data, Base64.DEFAULT);
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(Config.WEB_ROOT3);
        List<NameValuePair> donnees = new ArrayList<NameValuePair>(32);
        donnees.add(new BasicNameValuePair("no", no));
        donnees.add(new BasicNameValuePair("type", type));
        donnees.add(new BasicNameValuePair("blob", data_str));
        try {
            post.setEntity(new UrlEncodedFormEntity(donnees));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            org.apache.http.HttpResponse a = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }


    static void send_web2(byte[] data2) throws IOException {
        URL url = new URL(Config.WEB_ROOT3);
        String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode("001", "UTF-8");
        data += URLEncoder.encode("no", "UTF-8") + "=" + URLEncoder.encode("fff", "UTF-8");
        data += URLEncoder.encode("blob", "UTF-8") + "=" + URLEncoder.encode("Name", "UTF-8");
        // Send POST data request
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        java.io.OutputStream a = conn.getOutputStream();
        OutputStreamWriter wr = new OutputStreamWriter(a);
        wr.write(data);
        wr.flush();

    }
}