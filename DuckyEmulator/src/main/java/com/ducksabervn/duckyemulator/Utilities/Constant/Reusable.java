package com.ducksabervn.duckyemulator.Utilities.Constant;

import javafx.scene.control.TextArea;

public enum Reusable {

    ALERT_CONTAINER(),

    DEFAULT_GREETING("Hello %s");

    private TextArea stackTraceAlertContainer;

    private String defaultGreetingMessage;

    private Reusable(){
        stackTraceAlertContainer = new TextArea();
        stackTraceAlertContainer.setEditable(false);
        stackTraceAlertContainer.setWrapText(true);
        stackTraceAlertContainer.setPrefWidth(850);
        stackTraceAlertContainer.setPrefHeight(400);
    }

    private Reusable(String str){
        defaultGreetingMessage = str;
    }

    public TextArea getObject(){
        return this.stackTraceAlertContainer;
    }

    @Override
    public String toString(){
        return this.defaultGreetingMessage;
    }
}
