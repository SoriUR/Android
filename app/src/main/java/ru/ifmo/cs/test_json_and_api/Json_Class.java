package ru.ifmo.cs.test_json_and_api;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by Yura on 05.03.2017.
 */

public class Json_Class {
    private Context c;
    private String str;

    public Json_Class(Context c, String req) {
        this.c = c;
        try {
            str = new getData().execute(req).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public String getStr() {
        return str;
    }

    public static HashMap<String, String> getPartsOfSpeech(String jsonStr) {
        HashMap<String, String> list = new HashMap<>();
        JSONObject dataJsonObj;
        try {
            dataJsonObj = new JSONObject(jsonStr);
            //массив частей речи
            JSONArray def_array = dataJsonObj.getJSONArray("def");

            for (int i = 0; i < def_array.length(); i++) {
                JSONObject def_obj = def_array.getJSONObject(i);
                list.put("main", def_obj.getString("text"));
                //получим часть речи
                String PoS = def_obj.getString("pos");
                JSONArray tr_array = def_obj.getJSONArray("tr");
                JSONObject tr_obj = tr_array.getJSONObject(0);
                String transl = tr_obj.getString("text");
                list.put(PoS, transl);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }

    public static String getWord(String strJson){
        String word="";
        JSONObject dataJsonObj;
        try {
            dataJsonObj = new JSONObject(strJson);
            //массив частей речи
            JSONArray def_array = dataJsonObj.getJSONArray("def");
                JSONObject def_obj = def_array.getJSONObject(0);
                //получим само слово
                word = File_Class.ValidateText(def_obj.getString("text"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return word;
    }

    public static String parseJson(String strJson) {
        JSONObject dataJsonObj;
        String word, word_transl = "", example, example_transl = "", PoS;
        StringBuilder res = new StringBuilder();
        try {
            dataJsonObj = new JSONObject(strJson);
            //массив частей речи
            JSONArray def_array = dataJsonObj.getJSONArray("def");

            for (int i = 0; i < def_array.length(); i++) {
                JSONObject def_obj = def_array.getJSONObject(i);
                //получим само слово
                word = File_Class.ValidateText(def_obj.getString("text"));
                //получим часть речи
                PoS = def_obj.getString("pos").toLowerCase();

                res.append(word);
                res.append("(");
                res.append(PoS);
                res.append(")");
                res.append(":");
                res.append("\n\n");

                //массим переводов
                JSONArray tr_array = def_obj.getJSONArray("tr");
                for (int j = 0; (j < tr_array.length()) && (j < 4); j++) {
                    JSONObject tr_obj = tr_array.getJSONObject(j);
                    //получим перевод слова
                    word_transl = File_Class.ValidateText(tr_obj.getString("text"));

                    res.append(String.valueOf(j + 1));
                    res.append(") ");
                    res.append(word_transl);

                    if (!tr_obj.isNull("syn")) {
                        res.append(" (син: ");
                        JSONArray syn_array = tr_obj.getJSONArray("syn");
                        for (int p = 0; p < syn_array.length() && p < 5; p++) {
                            if (p > 0)
                                res.append(", ");
                            JSONObject syn_obj = syn_array.getJSONObject(p);
                            String syn_trnsl = syn_obj.getString("text").toLowerCase();

                            res.append(syn_trnsl);
                        }
                        res.append(")");
                    }
                    res.append("\n");

                    //если есть примеры
                    if (!tr_obj.isNull("ex")) {
                        //массив примеров перевода
                        JSONArray ex_array = tr_obj.getJSONArray("ex");
                        for (int k = 0; k < ex_array.length() && k < 5; k++) {
                            JSONObject ex_obj = ex_array.getJSONObject(k);
                            //перевод примера
                            example = ex_obj.getString("text");

                            res.append("   • ");
                            res.append(example);
                            res.append(" - ");

                            JSONArray ex_tr_array = ex_obj.getJSONArray("tr");
                            JSONObject ex_tr_obj = ex_tr_array.getJSONObject(0);
                            example_transl = ex_tr_obj.getString("text");

                            res.append(example_transl);
                            res.append("\n");
                        }
                    }
                    res.append("\n");
                }
                res.append("\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res.toString();
    }

    private class getData extends AsyncTask<String, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(String... params) {
            //получаем URL
            String str_url = c.getString(R.string.http) + c.getString(R.string.dict_key) + c.getString(R.string.lang) + "text=" + params[0];
            // получаем данные с внешнего ресурса
            try {
                URL url = new URL(str_url);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(5000);
                urlConnection.connect();

                //int status = urlConnection.getResponseCode();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(resultJson);
            return resultJson;
        }

    }

    public static ArrayList<String> getListFromJson(Context c, Integer id) {
        ArrayList<String> list;
        InputStream is = c.getResources().openRawResource(id);

        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String s = writer.toString();

        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        list = new Gson().fromJson(s, type);
        return list;
    }

}
