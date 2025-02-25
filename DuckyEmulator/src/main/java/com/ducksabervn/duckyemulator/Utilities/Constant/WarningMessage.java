package com.ducksabervn.duckyemulator.Utilities.Constant;

public enum WarningMessage{

    UNIVERSAL_RESET_FIELD("Are you sure you want to reset all fields ?");

    private String message;
    private WarningMessage(String message){
        this.message = message;
    }

    @Override
    public String toString(){
        return this.message;
    }
}
