package com.yanturin.oneword;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private String[] mRanks;
    private ArrayList<String> gArrWords;
    private ArrayList<String> gArrExplanation;

    public ViewPagerAdapter(Context context, String[] ranks, ArrayList<String> arrWords, ArrayList<String> arrExplanation) {
        this.mContext = context;
        this.mRanks = ranks;
        this.gArrWords = arrWords;
        this.gArrExplanation = arrExplanation;
    }

    @Override
    public int getCount() {
        return mRanks.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        TextView rankTextView;
        TextView tvWord, tvExplanation;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.pager, container,false);

        rankTextView = itemView.findViewById(R.id.textViewRank);
        tvWord = itemView.findViewById(R.id.tvWord);
        tvExplanation = itemView.findViewById(R.id.tvExplanation);

        rankTextView.setText(mRanks[position]);
        tvWord.setText(gArrWords.get(position));
        tvExplanation.setText(gArrExplanation.get(position));

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}