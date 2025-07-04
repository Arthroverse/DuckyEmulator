package com.arthroverse.duckyemulator.Database.MainDB.PublicBeans;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SessionInput {
    private ObjectProperty<Integer> questionNumber;
    private StringProperty isAnsweredAsString;
    private boolean answered;
    private StringProperty userAnswer;

    public SessionInput(int questionNumber, String isAnsweredForNav){
        this.questionNumber = new SimpleObjectProperty<>(questionNumber);
        this.isAnsweredAsString = new SimpleStringProperty(isAnsweredForNav);
        this.userAnswer = new SimpleStringProperty();
    }

    public ObjectProperty<Integer> getQuestionNumberProperty(){
        return this.questionNumber;
    }

    public StringProperty getIsAnsweredForNavProperty(){
        return this.isAnsweredAsString;
    }

    public StringProperty getUserAnswerProperty(){
        return this.userAnswer;
    }

    public Integer getQuestionNumber(){
        return this.questionNumber.get();
    }

    public String getIsAnsweredAsString(){
        return this.isAnsweredAsString.get();
    }

    public boolean isAnswered(){
        return this.answered;
    }

    public String getUserAnswer(){
        return this.userAnswer.get();
    }

    public void setAnswered(boolean answered){
        this.answered = answered;
    }

    public void setUserAnswer(String userAnswer){
        this.userAnswer.set(userAnswer);
    }

    public void setIsAnsweredAsString(String isAnsweredAsString){
        this.isAnsweredAsString.set(isAnsweredAsString);
    }
}
