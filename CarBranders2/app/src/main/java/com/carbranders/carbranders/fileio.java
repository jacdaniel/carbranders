package com.carbranders.carbranders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

/**
 * Created by Jacques on 31/01/2016.
 */
public class fileio {

    static byte[] doubleArray2byteArray(double[] doubleArray) {
        byte byteArray[] = new byte[doubleArray.length * 8];

        ByteBuffer byteBuf = ByteBuffer.wrap(byteArray);
        DoubleBuffer doubleBuf = byteBuf.asDoubleBuffer();
        doubleBuf.put(doubleArray);
        return byteArray;
    }

    public static void erase(String filename)
    {
        File p = new File(filename);
        p.delete();
    }

    public static void write_array_double_textfile(String filename, double[] var)
    {
        int size = var.length;

        FileWriter writer = null;
        try {
            writer = new FileWriter(filename, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0; i<size; i++) {
            try {
                writer.write(String.valueOf(var[i]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer.write("\r\n");   // write new line
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write_array_double_binary_file(String filename, double[] var)
    {
        int size = var.length;
        RandomAccessFile f = null;
        byte[] byte_buffer;

        try {
            f = new RandomAccessFile(filename, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            f.seek(f.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte_buffer = doubleArray2byteArray(var);
        try {
            f.write(byte_buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] read_array_binary_file(String filename) {
        RandomAccessFile f = null;
        byte[] byte_buffer = null;
        long size = 0;

        File pfile = new File(filename);
        if ( !pfile.exists() )
            return null;

            try {
            f = new RandomAccessFile(filename, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            size = f.length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte_buffer = new byte[(int)size];
        try {
            f.read(byte_buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byte_buffer;
    }



    public static void write_array_string_textfile(String filename, String var)
    {
        int size = 1;//var.length;

        FileWriter writer = null;
        try {
            writer = new FileWriter(filename, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0; i<size; i++) {
            try {
                writer.write(var);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer.write("\r\n");   // write new line
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void file_delete(String filename)
    {
        File pfile = new File(filename);
        if ( pfile == null ) return;
        if ( pfile.exists() == false ) return;
        pfile.delete();
    }

}
