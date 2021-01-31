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
    private static SqlQueries instance;
    public static synchronized SqlQueries Instance() {
        if (instance == null) {
            instance = new SqlQueries();
        }
        return instance;
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
    public ArrayList<Word> GetByDateAndFavorite(Context context){
        return TMPQuery(context, "SELECT * FROM words WHERE date NOT NULL AND favorite = 1");
    }

    private ArrayList<Word> TMPQuery(Context context, String query){
        DatabaseHelper mDBHelper = new DatabaseHelper(context);
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
        ArrayList<Word> arrListWords  = new ArrayList<>();
        Cursor cursor = mDb.rawQuery(query, null);

        cursor.moveToFirst();
        int indexWord = cursor.getColumnIndex("word");
        int indexExplanation = cursor.getColumnIndex("explanation");
        int indexFavorite = cursor.getColumnIndex("favorite");
        int indexDate = cursor.getColumnIndex("date");
        int indexIndexes = cursor.getColumnIndex("_id");
        int indexExample = cursor.getColumnIndex("example");
        int indexCondition = cursor.getColumnIndex("condition");

        while (!cursor.isAfterLast()) {
            Word word = new Word();
            word.setWord(cursor.getString(indexWord));
            word.setExplanation(cursor.getString(indexExplanation));
            word.setDate(Helper.Instance().ParseDateFromString(cursor.getString(indexDate)));
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
        Map<String,String> dicCondition = new HashMap<String, String>();
        Cursor cursor = mDb.rawQuery( "SELECT * FROM condition", null);

        cursor.moveToFirst();
        int indexCondition = cursor.getColumnIndex("condition");
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
