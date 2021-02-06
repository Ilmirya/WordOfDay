package com.yanturin.oneword.classes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Word {
    private String word;
    private String explanation;
    private Integer favorite;
    private Date date;
    private int index;
    private String symbol;
    private String example;
    private String condition;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public Date getDate() {
        return date;
    }
    public String getDateString(){
        SimpleDateFormat iso8601Format = new SimpleDateFormat("dd.MM.yyyy");
        return iso8601Format.format(date);
    }
    public Calendar getCalendar(){
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        }catch (Exception ex){
            return null;
        }
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
