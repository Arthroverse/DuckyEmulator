package Utilities.Constant.FailedOperationType;

public enum FailedOperationType {

    SQL_CLASS_DELETE_CLASS_FAILED("Delete topic"),

    SQL_TOPIC_DELETE_TOPIC_FAILED("Delete topic"),

    CLASS_UI_CONTROLLER_ADD_CLASS_FAILED("Add new classification"),

    CLASS_UI_CONTROLLER_UPDATE_CLASS_FAILED("Update current classification"),

    QUEST_UI_CONTROLLER_ADD_TOPIC_FAILED("Add new topic"),

    QUEST_UI_CONTROLLER_ADD_QUESTION_FAILED("Add new question"),

    QUEST_UI_CONTROLLER_UPDATE_QUESTION_FAILED("Update current question"),

    TOPIC_UI_CONTROLLER_ADD_NEW_TOPIC_FAILED("Add new topic"),

    TOPIC_UI_CONTROLLER_UPDATE_TOPIC_FAILED("Update new topic"),

    LOGIN_UI_CONTROLLER_LOGIN_FAILED("Login Failed");

    private String opType;
    private FailedOperationType(String opType){
        this.opType = opType;
    }

    @Override
    public String toString(){
        return this.opType;
    }
}
