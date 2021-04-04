package com.yanturin.oneword.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yanturin.oneword.Helper;
import com.yanturin.oneword.R;
import com.yanturin.oneword.classes.Word;

import java.util.Map;

public class ListExplanationAdapter extends BaseAdapter {
    private LayoutInflater _layoutInflater;
    private String[] _explanations;
    private String[] _examples;
    private String[] _conditions;
    private Map<String,String> _dicCondition;

    public ListExplanationAdapter(Context context, Word word, Map<String,String> dicCondition) {
        if(word.getExplanation() != null && (word.getExplanation().contains("|") || word.getExplanation().contains("/"))){
            this._explanations = word.getExplanation().replace('|','/').split("/");
        }
        else{
            this._explanations = new String[]{word.getExplanation()};
        }
        if(word.getExample() != null && word.getExample().contains("#")){
            this._examples =  word.getExample().split("#");
        }
        else{
            this._examples = new String[]{word.getExample()};
        }
        if(word.getCondition() != null && word.getCondition().contains("#")){
            this._conditions =  word.getCondition().split("#");
        }
        else{
            this._conditions = new String[]{word.getCondition()};
        }
        this._dicCondition = dicCondition;
        _layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return _explanations.length;
    }

    @Override
    public Object getItem(int position) {
        return _explanations[position];
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = _layoutInflater.inflate(R.layout.item_explanation_from_list, parent, false);
        }

        ((TextView) view.findViewById(R.id.tvItemExplanationFromList)).setText(_explanations[position]);
        if(_examples.length > position) {
            if(_examples[position] != null && _examples[position].contains("|")) ((TextView) view.findViewById(R.id.tvItemExampleFromList)).setText(_examples[position].replace("|", "\n"));
            else ((TextView) view.findViewById(R.id.tvItemExampleFromList)).setText(_examples[position]);
        }
        if(_conditions.length > position) {
            ((TextView) view.findViewById(R.id.tvItemConditionFromList)).setText(Helper.ParseCondition(_conditions[position], _dicCondition));
            //((TextView) view.findViewById(R.id.tvItemConditionFromList)).setText(arrCondition[position]);
        }
        return view;
    }

}
