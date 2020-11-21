package com.yanturin.oneword;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yanturin.oneword.classes.Word;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Helper {
    private static Helper instance;

    public static synchronized Helper Instance() {
        if (instance == null) {
            instance = new Helper();
        }
        return instance;
    }

    public Date ParseDateFromString(String timeToFormat) {
        SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        if (timeToFormat == null) return null;
        try {
            date = iso8601Format.parse(timeToFormat);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }
    public ArrayList<Word> sortByDate(ArrayList<Word> arr) {
        Collections.sort(arr, new Comparator<Word>() {
            @Override
            public int compare(Word word1, Word word2) {
                if (word1.date.after(word2.date)) {
                    return 1;
                } else if (word1.date.before(word2.date)) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return arr;
    }
    public String ParseCondition(String condition){
        if(condition == null) return "";
        String[] arrStr = condition.split(" ");
        for(String str : arrStr){
            switch (str){
                case "и.":
                    condition = condition.replace("и.","исем");
                    break;
                case "с.":
                    condition = condition.replace("с.","сифат");
                    break;
            }
        }
        return condition;
    }
}
