package com.ducksabervn.duckyemulator.Utilities.Constant;

public enum WarningTitle {

    UNIVERSAL_RESET_FIELD("Reset all fields");

    private String title;
    private WarningTitle(String title){
        this.title = title;
    }

    @Override
    public String toString(){
        return this.title;
    }
}
