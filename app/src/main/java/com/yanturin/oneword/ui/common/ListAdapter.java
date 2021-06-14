package com.yanturin.oneword.ui.common;

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
///class ListAdapter выводит список слов из ListFavoriteFragment
///в пользовательском виде, т.е. Слово и рисунок
public class ListAdapter extends BaseAdapter {
    private Context _context;
    private LayoutInflater _layoutInflater;
    private ArrayList<Word> _words;
    public ListAdapter(Context context, ArrayList<Word> words) {
        _context = context;
        _words = words;
        _layoutInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return _words.size();
    }

    @Override
    public Object getItem(int position) {
        return _words.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = _layoutInflater.inflate(R.layout.item_from_list, parent, false);
        }
        ((TextView) view.findViewById(R.id.tvItemFromList)).setText(_words.get(position).getWord());

        ImageView ivItemFromList = view.findViewById(R.id.ivItemFromList);
        ivItemFromList.setImageResource((_words.get(position).getFavorite() == 1)? R.drawable.yellowheart24: R.drawable.heart24);
        ivItemFromList.setTag(position);
        ivItemFromList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position =(Integer) v.getTag();
                Word tmpWord = _words.get(position);
                if(_words.get(position).getFavorite() == 1){
                    tmpWord.setFavorite(0);
                    SqlQueries.Instance().UpdateRowByWordsTable(Word.FAVORITE, 0, _words.get(position).getWord(), _context);
                }else{
                    tmpWord.setFavorite(1);
                    SqlQueries.Instance().UpdateRowByWordsTable(Word.FAVORITE, 1, _words.get(position).getWord(), _context);
                }
                _words.set(position, tmpWord);
                ImageView ivItemFromList = v.findViewById(R.id.ivItemFromList);
                ivItemFromList.setImageResource((_words.get(position).getFavorite() == 1)? R.drawable.yellowheart24: R.drawable.heart24);
            }
        });
        return view;
    }

}
