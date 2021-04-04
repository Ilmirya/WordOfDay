package com.yanturin.oneword.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yanturin.oneword.R;
import com.yanturin.oneword.SqlQueries;
import com.yanturin.oneword.classes.Word;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapterHome extends PagerAdapter {
    private Context _context;
    private ArrayList<Word> _words;
    private  Map<String,String> _dicCondition;
    public ViewPagerAdapterHome(Context context, ArrayList<Word> arrListWords, Map<String,String> dicCondition) {
        this._context = context;
        this._words = arrListWords;
        this._dicCondition = dicCondition;
    }

    @Override
    public int getCount() {
        return _words.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(R.layout.pager_home, container,false);

        SimpleDateFormat iso8601Format = new SimpleDateFormat("dd.MM.yyyy");
        TextView tvDate = itemView.findViewById(R.id.tvDate);
        TextView tvWord = itemView.findViewById(R.id.tvWord);
        ImageView ivFavorite = itemView.findViewById(R.id.ivFavorite);
        tvWord.setText(_words.get(position).getWord());

        ListView lvExplanation = itemView.findViewById(R.id.lvExplanation);
        ListExplanationAdapter listExplanationAdapter = new ListExplanationAdapter(itemView.getContext(), _words.get(position), _dicCondition);
        lvExplanation.setAdapter(listExplanationAdapter);

        tvDate.setText(iso8601Format.format(_words.get(position).getDate()));
        // создаем обработчик нажатия
        if(_words.get(position).getFavorite() != null)
        {
            ivFavorite.setImageResource((_words.get(position).getFavorite() == 1)? R.drawable.greenheart24: R.drawable.heart24);
            ivFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // создаем объект для данных
                    if(_words.get(position).getFavorite() == 1){
                        Word tmpWord = _words.get(position);
                        tmpWord.setFavorite(0);
                        _words.set(position, tmpWord);
                        SqlQueries.Instance().UpdateRowByWordsTable(Word.FAVORITE, 0, _words.get(position).getWord(), _context);
                    }else{
                        Word tmpWord = _words.get(position);
                        tmpWord.setFavorite(1);
                        _words.set(position, tmpWord);
                        SqlQueries.Instance().UpdateRowByWordsTable(Word.FAVORITE, 1, _words.get(position).getWord(), _context);
                    }
                    ImageView ivFavorite1 = itemView.findViewById(R.id.ivFavorite);
                    ivFavorite1.setImageResource((_words.get(position).getFavorite() == 1)? R.drawable.greenheart24: R.drawable.heart24);
                }
            });
        }

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((androidx.constraintlayout.widget.ConstraintLayout) object);
    }
}