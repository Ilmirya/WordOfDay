package com.yanturin.oneword.ui.home;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanturin.oneword.Helper;
import com.yanturin.oneword.R;
import com.yanturin.oneword.SqlQueries;
import com.yanturin.oneword.classes.Word;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapterHome extends PagerAdapter {
    private Context mContext;
    private ArrayList<Word> gArrListWords;

    public ViewPagerAdapterHome(Context context, ArrayList<Word> arrListWords) {
        this.mContext = context;
        this.gArrListWords = arrListWords;
    }

    @Override
    public int getCount() {
        return gArrListWords.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(R.layout.pager_home, container,false);

        SimpleDateFormat iso8601Format = new SimpleDateFormat("dd.MM.yyyy");
        TextView tvDate = itemView.findViewById(R.id.tvDate);
        TextView tvWord = itemView.findViewById(R.id.tvWord);
        TextView tvExplanation = itemView.findViewById(R.id.tvExplanation);
        ImageView ivFavorite = itemView.findViewById(R.id.ivFavorite);
        TextView tvExample = itemView.findViewById(R.id.tvExample);
        TextView tvCondition = itemView.findViewById(R.id.tvCondition);
        tvWord.setText(gArrListWords.get(position).word);
        tvExample.setText(gArrListWords.get(position).example);
        tvCondition.setText(Helper.Instance().ParseCondition(gArrListWords.get(position).condition));
        String explanation = gArrListWords.get(position).explanation;
        if(gArrListWords.get(position).explanation.contains("|")){
            String[] arrStrTmp = gArrListWords.get(position).explanation.replace('|','/').split("/");
            for(int i = 0; i < arrStrTmp.length; i++){
                if(i == 0) explanation = arrStrTmp[i];
                else  explanation += "\n" + arrStrTmp[i];
            }
        }
        else{
            explanation = gArrListWords.get(position).explanation;
        }
        tvExplanation.setText(explanation);
        tvDate.setText(iso8601Format.format(gArrListWords.get(position).getDate()));
        // создаем обработчик нажатия
        ivFavorite.setImageResource((gArrListWords.get(position).favorite == 1)? R.drawable.greenheart24: R.drawable.heart24);

        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // создаем объект для данных
                if(gArrListWords.get(position).favorite == 1){
                    Word tmpWord = gArrListWords.get(position);
                    tmpWord.setFavorite(0);
                    gArrListWords.set(position, tmpWord);
                    SqlQueries.Instance().UpdateRowByWordsTable("favorite", 0, gArrListWords.get(position).word, mContext);
                }else{
                    Word tmpWord = gArrListWords.get(position);
                    tmpWord.setFavorite(1);
                    gArrListWords.set(position, tmpWord);
                    SqlQueries.Instance().UpdateRowByWordsTable("favorite", 1, gArrListWords.get(position).word, mContext);
                }
                ImageView ivFavorite1 = itemView.findViewById(R.id.ivFavorite);
                ivFavorite1.setImageResource((gArrListWords.get(position).favorite == 1)? R.drawable.greenheart24: R.drawable.heart24);
            }
        });

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}