package ru.ifmo.cs.test_json_and_api;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Download_Package_Recycler_Adapter extends RecyclerView.Adapter<Download_Package_Recycler_Adapter.ViewHolder> {

    private ArrayList<String> mDataset;
    private static Context c;

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит только из одного TextView
        public TextView mPackageName;
        public TextView mDownloadStatus;

        public ViewHolder(View v) {
            super(v);
            mPackageName = (TextView) v.findViewById(R.id.package_name);
            mDownloadStatus = (TextView) v.findViewById(R.id.download_status);

            mDownloadStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DownloadPackage().execute(mPackageName.getText().toString().replace(" ", "_").toLowerCase());
                }
            });
        }

        class DownloadPackage extends AsyncTask<String, Integer, String> {


            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                mDownloadStatus.setText(values[0] + "%");
            }

            private ArrayList<String> list;

            @Override
            protected String doInBackground(String... params) {
                String pack_name = params[0];
                int id = c.getResources().getIdentifier(pack_name, "raw", c.getPackageName());
                list = Json_Class.getListFromJson(c, id);

                for (int i = 0; i < list.size(); i++) {


                    String str_url = c.getString(R.string.http) + c.getString(R.string.dict_key) + c.getString(R.string.lang) + "text=" + list.get(i);
                    // получаем данные с внешнего ресурса
                    HttpURLConnection urlConnection = null;
                    BufferedReader reader = null;
                    String resultJson = "";
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
                        File_Class.writeFile(c, pack_name, resultJson);
                        System.out.println(list.get(i));
                        publishProgress(100 * (i + 1) / list.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                File_Class.writeFile(c, "downloaded_packs", pack_name);
                return "";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mDownloadStatus.setClickable(false);
            }
        }
    }


    // Конструктор
    public Download_Package_Recycler_Adapter(ArrayList<String> dataset, Context c) {
        mDataset = dataset;
        this.c = c;
    }


    // Создает новые views (вызывается layout manager-ом)
    @Override
    public Download_Package_Recycler_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_download_pack, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String pack_name = mDataset.get(position);
        holder.mPackageName.setText(File_Class.ValidateText(pack_name));
        pack_name = pack_name.replace(" ", "_");
        try {
            c.openFileInput(pack_name);
            holder.mDownloadStatus.setText("100%");
            holder.mDownloadStatus.setClickable(false);
        } catch (FileNotFoundException e) {
        }


    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}