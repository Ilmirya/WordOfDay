package com.yanturin.oneword.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanturin.oneword.Helper;
import com.yanturin.oneword.R;
import com.yanturin.oneword.SqlQueries;
import com.yanturin.oneword.classes.Word;

import java.util.ArrayList;
import java.util.Map;

public class ListExplanationAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    String[] arrExplanation;
    String[] arrExample;
    String[] arrCondition;
    private Map<String,String> dicCondition;
    public ListExplanationAdapter(Context context, Word word, Map<String,String> dicCondition) {
        ctx = context;
        if(word.getExplanation() != null && (word.getExplanation().contains("|") || word.getExplanation().contains("/"))){
            this.arrExplanation = word.getExplanation().replace('|','/').split("/");
        }
        else{
            this.arrExplanation = new String[]{word.getExplanation()};
        }
        if(word.getExample() != null && word.getExample().contains("#")){
            this.arrExample =  word.getExample().split("#");
        }
        else{
            this.arrExample = new String[]{word.getExample()};
        }
        if(word.getCondition() != null && word.getCondition().contains("#")){
            this.arrCondition =  word.getCondition().split("#");
        }
        else{
            this.arrCondition = new String[]{word.getCondition()};
        }
        this.dicCondition = dicCondition;
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
        if(arrExample.length > position) {
            if(arrExample[position] != null && arrExample[position].contains("|")) ((TextView) view.findViewById(R.id.tvItemExampleFromList)).setText(arrExample[position].replace("|", "\n"));
            else ((TextView) view.findViewById(R.id.tvItemExampleFromList)).setText(arrExample[position]);
        }
        if(arrCondition.length > position) {
            ((TextView) view.findViewById(R.id.tvItemConditionFromList)).setText(Helper.Instance().ParseCondition(arrCondition[position], dicCondition));
            //((TextView) view.findViewById(R.id.tvItemConditionFromList)).setText(arrCondition[position]);
        }
        return view;
    }

}
