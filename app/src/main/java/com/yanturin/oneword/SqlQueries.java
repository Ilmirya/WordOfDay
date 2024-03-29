package com.yanturin.oneword;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.yanturin.oneword.classes.Word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SqlQueries {
    private static SqlQueries _instance;
    public static synchronized SqlQueries Instance() {
        if (_instance == null) {
            _instance = new SqlQueries();
        }
        return _instance;
    }
    public ArrayList<Word> GetAll(Context context){
        return TMPQuery(context, "SELECT * FROM words");
    }

    public ArrayList<Word> GetByDate(Context context){
        return TMPQuery(context, "SELECT * FROM words WHERE date NOT NULL");
    }
    public ArrayList<Word> GetWithoutDate(Context context){
        return TMPQuery(context, "SELECT * FROM words WHERE date IS NULL");
    }
    public ArrayList<Word> GetRandomWordsWithoutDate(Context context, int count){
        return TMPQuery(context, "SELECT * FROM words WHERE date IS NULL ORDER BY RANDOM() LIMIT " + count);
    }
    public ArrayList<Word> GetByDateAndFavorite(Context context){
        return TMPQuery(context, "SELECT * FROM words WHERE date NOT NULL AND favorite = 1");
    }

    private ArrayList<Word> TMPQuery(Context context, String query){
        DatabaseHelper mDBHelper = new DatabaseHelper(context);
        ArrayList<Word> arrListWords  = new ArrayList<>();
        SQLiteDatabase mDb;
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw new Error("mDBHelper.getWritableDatabase()" + mSQLException.getMessage());
        }
        Cursor cursor = mDb.rawQuery(query, null);

        cursor.moveToFirst();
        int indexWord = cursor.getColumnIndex(Word.WORD);
        int indexExplanation = cursor.getColumnIndex(Word.EXPLANATION);
        int indexFavorite = cursor.getColumnIndex(Word.FAVORITE);
        int indexDate = cursor.getColumnIndex(Word.DATE);
        int indexIndexes = cursor.getColumnIndex(Word.INDEX);
        int indexExample = cursor.getColumnIndex(Word.EXAMPLE);
        int indexCondition = cursor.getColumnIndex(Word.CONDITION);

        while (!cursor.isAfterLast()) {
            Word word = new Word();
            word.setWord(cursor.getString(indexWord));
            word.setExplanation(cursor.getString(indexExplanation));
            word.setDate(Helper.ParseDateFromString(cursor.getString(indexDate)));
            word.setFavorite(cursor.getInt(indexFavorite));
            word.setIndex(cursor.getInt(indexIndexes));

            word.setExample(cursor.isNull(indexExample)? "": cursor.getString(indexExample));
            word.setCondition(cursor.isNull(indexCondition)? "": cursor.getString(indexCondition));
            arrListWords.add(word);

            cursor.moveToNext();
        }
        cursor.close();
        mDBHelper.close();
        return arrListWords;
    }
    public void UpdateRowByWordsTable(String key, String value, String word, Context context){
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        DatabaseHelper mDBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.update("words", cv, "word = ?", new String[]{word});
        mDBHelper.close();
    }
    public void UpdateRowByWordsTable(String key, int value, String word, Context context){
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        DatabaseHelper mDBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.update("words", cv, "word = ?", new String[]{word});
        mDBHelper.close();
    }

    public Map<String,String> GetCondition(Context context){
        DatabaseHelper mDBHelper = new DatabaseHelper(context);
        Map<String,String> dicCondition = new HashMap<>();
        SQLiteDatabase mDb;
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            return dicCondition;
        }
        Cursor cursor = mDb.rawQuery( "SELECT * FROM condition", null);

        cursor.moveToFirst();
        int indexCondition = cursor.getColumnIndex(Word.CONDITION);
        int indexBash = cursor.getColumnIndex("bash");

        while (!cursor.isAfterLast()) {
            dicCondition.put(cursor.getString(indexCondition), cursor.getString(indexBash));
            cursor.moveToNext();
        }
        cursor.close();
        mDBHelper.close();
        return dicCondition;
    }


}
