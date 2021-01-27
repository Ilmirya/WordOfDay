package com.yanturin.oneword.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanturin.oneword.R;
import com.yanturin.oneword.SqlQueries;
import com.yanturin.oneword.classes.Word;

import java.util.ArrayList;

public class ListExplanationAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    String[] arrExplanation;
    String example;
    public ListExplanationAdapter(Context context, String[] arrExplanation, String example) {
        ctx = context;
        this.arrExplanation = arrExplanation;
        this.example = example;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return arrExplanation.length;
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return arrExplanation[position];
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_explanation_from_list, parent, false);
        }
        ((TextView) view.findViewById(R.id.tvItemExplanationFromList)).setText(arrExplanation[position]);
        ((TextView) view.findViewById(R.id.tvItemExampleFromList)).setText(example);

        return view;
    }

}
