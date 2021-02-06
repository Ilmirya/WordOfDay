package com.yanturin.oneword;

import com.yanturin.oneword.classes.Word;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

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
                if (word1.getDate().after(word2.getDate())) {
                    return 1;
                } else if (word1.getDate().before(word2.getDate())) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return arr;
    }
    public String ParseCondition(String condition, Map<String,String> dicCondition){
        if(condition == null) return "";
        String[] arrStr = condition.split(" ");
        for(String str : arrStr){
            String fullCondition = dicCondition.get(str);
            if(fullCondition == null) continue;
            condition = condition.replace(str, fullCondition);
        }
        return condition.replace(" ", ", ");
    }
}
