package com.yanturin.oneword.ui.home;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.yanturin.oneword.DatabaseHelper;
import com.yanturin.oneword.R;
import com.yanturin.oneword.ViewPagerAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        textView.setText("This is home fragment");
        String[] ranks = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        DatabaseHelper mDBHelper = new DatabaseHelper(root.getContext());
        SQLiteDatabase mDb;
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        ArrayList<String> arrWords = new ArrayList<>();
        ArrayList<String> arrExplanation = new ArrayList<>();

        Cursor cursor = mDb.rawQuery("SELECT * FROM words", null);
        cursor.moveToFirst();
        int indexWord = cursor.getColumnIndex("word");
        int indexExplanation = cursor.getColumnIndex("explanation");

        while (!cursor.isAfterLast()) {
            arrWords.add(cursor.getString(indexWord));
            arrExplanation.add(cursor.getString(indexExplanation));
            cursor.moveToNext();
        }
        cursor.close();
        ViewPager pager = root.findViewById(R.id.pager);
        PagerAdapter adapter = new ViewPagerAdapter(root.getContext(), ranks, arrWords, arrExplanation);
        pager.setAdapter(adapter);
        pager.setCurrentItem(5);
        return root;
    }
}