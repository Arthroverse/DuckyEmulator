package com.arthroverse.duckyemulator.Database.MainDB.PublicBeans;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SessionInput {
    private ObjectProperty<Integer> questionNumber;
    private StringProperty isAnsweredForNav;
    private boolean answered;
    private String userAnswer;

    public SessionInput(int questionNumber, String isAnsweredForNav){
        this.questionNumber = new SimpleObjectProperty<>(questionNumber);
        this.isAnsweredForNav = new SimpleStringProperty(isAnsweredForNav);
    }

    public ObjectProperty<Integer> getQuestionNumberProperty(){
        return this.questionNumber;
    }

    public StringProperty getIsAnsweredForNavProperty(){
        return this.isAnsweredForNav;
    }

    public Integer getQuestionNumber(){
        return this.questionNumber.get();
    }

    public String getIsAnsweredForNav(){
        return this.isAnsweredForNav.get();
    }

    public boolean isAnswered(){
        return this.answered;
    }

    public String getUserAnswer(){
        return this.userAnswer;
    }

    public void setAnswered(boolean answered){
        this.answered = answered;
    }

    public void setUserAnswer(String userAnswer){
        this.userAnswer = userAnswer;
    }

    public void setIsAnsweredForNav(String isAnsweredForNav){
        this.isAnsweredForNav.set(isAnsweredForNav);
    }
}
