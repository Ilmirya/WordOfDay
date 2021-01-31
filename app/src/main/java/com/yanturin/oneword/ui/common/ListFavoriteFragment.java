package com.yanturin.oneword.ui.common;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.yanturin.oneword.Helper;
import com.yanturin.oneword.R;
import com.yanturin.oneword.SqlQueries;
import com.yanturin.oneword.classes.Word;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
///в этом классе выводятся списки слов,
///которые изучены, избранные и тд
public class ListFavoriteFragment extends Fragment {
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String SAVE_PAGE_NUMBER = "save_page_number";
    public static final String SELECTED_WORD = "selected_word";

    int pageNumber;
    static Context mContext;
    public static ListFavoriteFragment newInstance(int page, Context context) {
        ListFavoriteFragment pageFragment = new ListFavoriteFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        mContext = context;
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }
    ListView wordsListView;
    View view;
    ArrayList<Word> arrListWords;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pager_list, null);
        wordsListView = view.findViewById(R.id.countriesList);
        wordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(SELECTED_WORD, arrListWords.get(position).getWord());
                Navigation.findNavController(view).navigate(R.id.nav_home, bundle);
            }
        });
        return view;
    }

    public void Main(){
        if(pageNumber == 0){
            arrListWords = SqlQueries.Instance().GetByDate(mContext);
        }else{
            arrListWords = SqlQueries.Instance().GetByDateAndFavorite(mContext);
        }
        arrListWords = Helper.Instance().sortByDate(arrListWords);

        ListAdapter listAdapter = new ListAdapter(view.getContext(), arrListWords);
        wordsListView.setAdapter(listAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_PAGE_NUMBER, pageNumber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(wordsListView == null || view == null) return;
        Main();
    }
}
