package ru.ifmo.cs.test_json_and_api;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class File_Class {

    public static void writeFile(Context c,String FILENAME,String output) {
        String old = readFile(c,FILENAME);
        output+="|";    
        output=old.concat(output);
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    c.openFileOutput(FILENAME.replace(" ","_").toLowerCase(), MODE_PRIVATE)));
            // пишем данные

            bw.write(output);
            // закрываем поток
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cleanAll(Context c){
        c.deleteFile("words1");
        c.deleteFile("saved_words");
        c.deleteFile("downloaded_packs");
        c.deleteFile("body_parts");
        c.deleteFile("cutlery");
    }

    public static  String ValidateText(String str){
        str=str.replace("_"," ");
        str=str.substring(0,1).toUpperCase()+str.substring(1,str.length()).toLowerCase();
        return str;
    }

    public static void cleanFile(Context c, String FILENAME) {
        boolean b = c.deleteFile(FILENAME.replace(" ","_").toLowerCase());
    }


    public static String readFile(Context c, String FILENAME) {
        String res="";
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(c.openFileInput(FILENAME.replace(" ","_").toLowerCase())));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                res+=str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static ArrayList<String> getListFromFileString(String str){
        ArrayList<String> list = new ArrayList<>();
        int spliter_pos = str.indexOf("|");
        while (spliter_pos > 0) {
            list.add(str.substring(0, spliter_pos));
            if (spliter_pos == (str.length() - 1)) {
                str = "";
            } else {
                str = str.substring(spliter_pos + 1, str.length());
            }
            spliter_pos = str.indexOf("|");
        }
        return list;
    }
}
