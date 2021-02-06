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
import com.yanturin.oneword.ui.common.ListFavoriteFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class HomeFragment extends Fragment {
    private AdView mAdView;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //реклама
        /*mAdView = root.findViewById(R.id.adView);
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        mAdView.loadAd(adRequestBuilder.build());
         */
        ArrayList<Word> arrWordsForShow = SqlQueries.Instance().GetByDate(root.getContext());
        Map<String,String> dicCondition = SqlQueries.Instance().GetCondition(root.getContext());
        Calendar calendarToday =  Calendar.getInstance();
        if(!arrWordsForShow.isEmpty()){
            for(int i = 0; i < arrWordsForShow.size(); i++){
                if(arrWordsForShow.get(i).getCalendar() == null){
                    SqlQueries.Instance().UpdateRowByWordsTable("date", null,  arrWordsForShow.get(i).getWord(), root.getContext());
                    arrWordsForShow.remove(i);
                    i--;
                }
            }
        }

        boolean isToday = false;
        if(!arrWordsForShow.isEmpty()){
            arrWordsForShow = Helper.Instance().sortByDate(arrWordsForShow);
            if(calendarToday.get(Calendar.DAY_OF_YEAR) == arrWordsForShow.get(arrWordsForShow.size() - 1).getCalendar().get(Calendar.DAY_OF_YEAR) && calendarToday.get(Calendar.YEAR)== arrWordsForShow.get(arrWordsForShow.size() - 1).getCalendar().get(Calendar.YEAR)){
                isToday = true;
            }
        }
        else{
            final Random random = new Random();
            Calendar calendar =  Calendar.getInstance();
            ArrayList<Word> wordsWithoutDate = SqlQueries.Instance().GetWithoutDate(root.getContext());
            for(int i = 0; i < 5; i++){
                int rdmIndex = random.nextInt(wordsWithoutDate.size());
                Word todayWord = wordsWithoutDate.get(rdmIndex);
                wordsWithoutDate.remove(rdmIndex);
                calendar.add(Calendar.DAY_OF_YEAR,-1);
                todayWord.setDate(calendar.getTime());
                arrWordsForShow.add(todayWord);
                SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SqlQueries.Instance().UpdateRowByWordsTable("date", iso8601Format.format(calendar.getTime()), todayWord.getWord(), root.getContext());
            }
            arrWordsForShow = Helper.Instance().sortByDate(arrWordsForShow);
        }
        if(!isToday){
            final Random random = new Random();
            ArrayList<Word> wordsWithoutDate = SqlQueries.Instance().GetWithoutDate(root.getContext());
            Word todayWord = wordsWithoutDate.get(random.nextInt(wordsWithoutDate.size()));
            todayWord.setDate(calendarToday.getTime());
            arrWordsForShow.add(todayWord);
            SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SqlQueries.Instance().UpdateRowByWordsTable("date", iso8601Format.format(calendarToday.getTime()), todayWord.getWord(), root.getContext());
        }

        Word tomorrowWord = new Word();
        calendarToday.add(Calendar.DATE,1);//tomorrow
        tomorrowWord.setDate(calendarToday.getTime());
        tomorrowWord.setExplanation("Новое слово будет ждать вас здесь завтра");
        int currentItem = arrWordsForShow.size() - 1; //-1 тк с начало с нуля
        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey(ListFavoriteFragment.SELECTED_WORD)){
            for(int i = 0; i < arrWordsForShow.size(); i++){
                if(arrWordsForShow.get(i).getWord().equals(bundle.getString(ListFavoriteFragment.SELECTED_WORD))){
                    currentItem = i;
                }
            }
        }

        arrWordsForShow.add(tomorrowWord);
        ViewPager pager = root.findViewById(R.id.pagerHome);
        PagerAdapter adapter = new ViewPagerAdapterHome(root.getContext(), arrWordsForShow,dicCondition);
        pager.setAdapter(adapter);
        pager.setCurrentItem(currentItem);
        return root;
    }

}