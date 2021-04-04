package com.yanturin.oneword.classes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Word {
    public static final String WORD = "word";
    public static final String EXPLANATION = "explanation";
    public static final String FAVORITE = "favorite";
    public static final String DATE = "date";
    public static final String INDEX = "_id";
    public static final String SYMBOL = "symbol";
    public static final String EXAMPLE = "example";
    public static final String CONDITION = "condition";

    private String _word;
    private String _explanation;
    private Integer _favorite;
    private Date _date;
    private int _index;
    private String _symbol;
    private String _example;
    private String _condition;

    public String getCondition() {
        return _condition;
    }

    public void setCondition(String condition) {
        this._condition = condition;
    }

    public String getExample() {
        return _example;
    }

    public void setExample(String example) {
        this._example = example;
    }

    public String getWord() {
        return _word;
    }

    public void setWord(String word) {
        this._word = word;
    }

    public String getExplanation() {
        return _explanation;
    }

    public void setExplanation(String explanation) {
        this._explanation = explanation;
    }

    public Integer getFavorite() {
        return _favorite;
    }

    public void setFavorite(int favorite) {
        this._favorite = favorite;
    }

    public Date getDate() {
        return _date;
    }
    public String getDateString(){
        SimpleDateFormat iso8601Format = new SimpleDateFormat("dd.MM.yyyy");
        return iso8601Format.format(_date);
    }
    public Calendar getCalendar(){
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(_date);
            return cal;
        }catch (Exception ex){
            return null;
        }
    }
    public void setDate(Date date) {
        this._date = date;
    }

    public int getIndex() {
        return _index;
    }

    public void setIndex(int index) {
        this._index = index;
    }

    public String getSymbol() {
        return _symbol;
    }

    public void setSymbol(String symbol) {
        this._symbol = symbol;
    }
}
