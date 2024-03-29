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
    private static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    private static final String SAVE_PAGE_NUMBER = "save_page_number";
    public static final String SELECTED_WORD = "selected_word";

    private int _pageNumber;
    private static Context _context;

    private ListView _wordsListView;
    private View _view;
    private ArrayList<Word> _words;

    public static ListFavoriteFragment newInstance(int page, Context context) {
        ListFavoriteFragment pageFragment = new ListFavoriteFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        _context = context;
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.pager_list, null);
        _wordsListView = _view.findViewById(R.id.countriesList);
        _wordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(SELECTED_WORD, _words.get(position).getWord());
                Navigation.findNavController(view).navigate(R.id.nav_home, bundle);
            }
        });
        return _view;
    }

    public void Main(){
        if(_pageNumber == 0){
            _words = SqlQueries.Instance().GetByDate(_context);
        }else{
            _words = SqlQueries.Instance().GetByDateAndFavorite(_context);
        }
        _words = Helper.sortByDate(_words);

        ListAdapter listAdapter = new ListAdapter(_view.getContext(), _words);
        _wordsListView.setAdapter(listAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_PAGE_NUMBER, _pageNumber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(_wordsListView == null || _view == null) return;
        Main();
    }
}
