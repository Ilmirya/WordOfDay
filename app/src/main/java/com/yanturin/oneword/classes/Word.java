package com.yanturin.oneword.classes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Word {

    public String word;
    public String explanation;
    public int favorite;
    public Date date;
    public int index;
    public String symbol;

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

    public int getFavorite() {
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
