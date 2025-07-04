package com.arthroverse.duckyemulator.Database.MainDB.PublicBeans;

import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Questions;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SessionResult {
    private SessionInput userInputInformation;
    private Questions question;
    private ObjectProperty<Boolean> isCorrect;
    private StringProperty associatedTopicsAsString;
    private StringProperty isCorrectAsString;

    public SessionResult(SessionInput userInputInformation,
                        Questions question,
                        boolean isCorrect,
                        String associatedTopicsAsString,
                        String isCorrectAsString){
        this.userInputInformation = userInputInformation;
        this.question = question;
        this.isCorrect = new SimpleObjectProperty<>();
        this.associatedTopicsAsString = new SimpleStringProperty();
        this.isCorrectAsString = new SimpleStringProperty();
        this.setIsCorrect(isCorrect);
        this.setAssociatedTopicsAsString(associatedTopicsAsString);
        this.setIsCorrectAsString(isCorrectAsString);
    }

    public SessionInput getUserInputInformation(){
        return this.userInputInformation;
    }

    public Questions getQuestion(){
        return this.question;
    }

    public ObjectProperty<Boolean> getIsCorrectProperty(){
        return this.isCorrect;
    }

    public boolean getIsCorrect(){
        return this.isCorrect.get();
    }

    public StringProperty getAssociatedTopicsAsStringProperty(){
        return this.associatedTopicsAsString;
    }

    public String getAssociatedTopicsAsString(){
        return this.associatedTopicsAsString.get();
    }

    public StringProperty getIsCorrectAsStringProperty(){
        return this.isCorrectAsString;
    }

    public void setUserInputInformation(SessionInput si){
        this.userInputInformation = si;
    }

    public void setQuestion(Questions q){
        this.question = q;
    }

    public void setIsCorrect(boolean isCorrect){
        this.isCorrect.set(isCorrect);
    }

    public void setAssociatedTopicsAsString(String associatedTopicsAsString){
        this.associatedTopicsAsString.set(associatedTopicsAsString);
    }

    public void setIsCorrectAsString(String isCorrectAsString){
        this.isCorrectAsString.set(isCorrectAsString);
    }
 }
