package com.yanturin.oneword.ui.common;

import android.content.ContentValues;
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

public class ListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Word> arrListWords;
    public ListAdapter(Context context, ArrayList<Word> arrListWords) {
        ctx = context;
        this.arrListWords = arrListWords;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return arrListWords.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return arrListWords.get(position);
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
            view = lInflater.inflate(R.layout.item_from_list, parent, false);
        }
        ((TextView) view.findViewById(R.id.tvItemFromList)).setText(arrListWords.get(position).getWord());

        ImageView ivItemFromList = view.findViewById(R.id.ivItemFromList);
        ivItemFromList.setImageResource((arrListWords.get(position).getFavorite() == 1)? R.drawable.greenheart24: R.drawable.heart24);
        ivItemFromList.setTag(position);
        ivItemFromList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position =(Integer) v.getTag();
                Word tmpWord = arrListWords.get(position);
                if(arrListWords.get(position).getFavorite() == 1){
                    tmpWord.setFavorite(0);
                    SqlQueries.Instance().UpdateRowByWordsTable("favorite", 0, arrListWords.get(position).getWord(), ctx);
                }else{
                    tmpWord.setFavorite(1);
                    SqlQueries.Instance().UpdateRowByWordsTable("favorite", 1, arrListWords.get(position).getWord(), ctx);
                }
                arrListWords.set(position, tmpWord);
                ImageView ivItemFromList = v.findViewById(R.id.ivItemFromList);
                ivItemFromList.setImageResource((arrListWords.get(position).getFavorite() == 1)? R.drawable.greenheart24: R.drawable.heart24);
            }
        });
        return view;
    }

}
