package ru.ifmo.cs.test_json_and_api;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class Pick_Package_Recycler_Adapter extends RecyclerView.Adapter<Pick_Package_Recycler_Adapter.ViewHolder> {

    private ArrayList<String> mDataset;
    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит только из одного TextView
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.package_name);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context mContext = v.getContext();
                    Intent intent = new Intent(mContext,Pick_Word_Activity.class);
                    intent.putExtra("pack_name",mTextView.getText().toString());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    // Конструктор
    public Pick_Package_Recycler_Adapter(ArrayList<String> dataset) {
        mDataset = dataset;
    }


    // Создает новые views (вызывается layout manager-ом)
    @Override
    public Pick_Package_Recycler_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_pick_pack, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // TODO: 27.03.2017 клик листенер для итема
    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTextView.setText(File_Class.ValidateText(mDataset.get(position)));

    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
