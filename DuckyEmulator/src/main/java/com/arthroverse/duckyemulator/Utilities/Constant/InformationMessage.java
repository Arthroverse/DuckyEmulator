package com.arthroverse.duckyemulator.Utilities.Constant;

public enum InformationMessage {
    CREATE_ACCOUNT_SUCCESSFUL("Create account successful!");
    private String informationMessage;

    private InformationMessage(String informationMessage){
        this.informationMessage = informationMessage;
    }

    @Override
    public String toString(){
        return informationMessage;
    }
}
