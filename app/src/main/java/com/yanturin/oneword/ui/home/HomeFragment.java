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
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class HomeFragment extends Fragment {
    private AdView mAdView;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mAdView = root.findViewById(R.id.adView);
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        mAdView.loadAd(adRequestBuilder.build());
        ArrayList<Word> arrWordsForShow = SqlQueries.Instance().GetByDate(root.getContext());
        Date dtNow = new Date();
        boolean isToday = false;
        if(arrWordsForShow.size() > 0){
            arrWordsForShow = Helper.Instance().sortByDate(arrWordsForShow);
            if(dtNow.getDay() == arrWordsForShow.get(arrWordsForShow.size()-1).getDate().getDay() && dtNow.getMonth() == arrWordsForShow.get(arrWordsForShow.size()-1).getDate().getMonth() && dtNow.getYear() == arrWordsForShow.get(arrWordsForShow.size()-1).getDate().getYear()){
                isToday = true;
            }
        }
        if(!isToday){
            final Random random = new Random();
            ArrayList<Word> wordsWithoutDate = SqlQueries.Instance().GetWithoutDate(root.getContext());
            Word todayWord = wordsWithoutDate.get(random.nextInt(wordsWithoutDate.size()));
            todayWord.setDate(dtNow);
            arrWordsForShow.add(todayWord);
            SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SqlQueries.Instance().UpdateRowByWordsTable("date", iso8601Format.format(dtNow), todayWord.getWord(), root.getContext());
        }

        Word forNextDay = new Word();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        forNextDay.setDate(calendar.getTime());
        forNextDay.setExplanation("Новое слово будет ждать вас здесь завтра");

        arrWordsForShow.add(forNextDay);
        ViewPager pager = root.findViewById(R.id.pagerHome);
        PagerAdapter adapter = new ViewPagerAdapterHome(root.getContext(), arrWordsForShow);
        pager.setAdapter(adapter);
        pager.setCurrentItem(arrWordsForShow.size()-2);
        return root;
    }

}