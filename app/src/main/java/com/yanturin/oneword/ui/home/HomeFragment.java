package com.yanturin.oneword.ui.home;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.yanturin.oneword.Helper;
import com.yanturin.oneword.R;
import com.yanturin.oneword.SqlQueries;
import com.yanturin.oneword.classes.Word;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class HomeFragment extends Fragment {
    private AdView mAdView;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mAdView = root.findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        //adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        mAdView.loadAd(adRequestBuilder.build());
        ArrayList<Word> arrWordsForShow = SqlQueries.Instance().GetByDate(root.getContext());
        Date dtNow = new Date();
        boolean isToday = false;
        if(arrWordsForShow.size() > 0){
            arrWordsForShow = Helper.Instance().sortByDate(arrWordsForShow);
            if(dtNow.getDay() == arrWordsForShow.get(arrWordsForShow.size()-1).date.getDay() && dtNow.getMonth() == arrWordsForShow.get(arrWordsForShow.size()-1).date.getMonth() && dtNow.getYear() == arrWordsForShow.get(arrWordsForShow.size()-1).date.getYear()){
                isToday = true;
            }
        }
        if(!isToday){
            final Random random = new Random();
            ArrayList<Word> wordsWithoutDate = SqlQueries.Instance().GetWithoutDate(root.getContext());
            Word todayWord = wordsWithoutDate.get(random.nextInt(wordsWithoutDate.size()));
            todayWord.date = dtNow;
            arrWordsForShow.add(todayWord);
            SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SqlQueries.Instance().UpdateRowByWordsTable("date", iso8601Format.format(dtNow), todayWord.word, root.getContext());
        }
        ViewPager pager = root.findViewById(R.id.pagerHome);
        PagerAdapter adapter = new ViewPagerAdapterHome(root.getContext(), arrWordsForShow);
        pager.setAdapter(adapter);
        pager.setCurrentItem(arrWordsForShow.size()-1);
        return root;
    }

}