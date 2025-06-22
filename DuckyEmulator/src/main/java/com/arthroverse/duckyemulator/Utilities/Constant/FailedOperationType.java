/*
 * Copyright (c) 2025 Arthroverse Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Organization: Arthroverse Laboratory
 * Author: Vinh Dinh Mai
 * Contact: business@arthroverse.com
 *
 *
 * @author ducksabervn
 */
package com.arthroverse.duckyemulator.Utilities.Constant;

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

    LOGIN_UI_CONTROLLER_LOGIN_FAILED("Login"),

    SQL_SESSION_INSERT_NEW_QUERY_FAILED("Session insert"),

    SQL_CREATE_NEW_ACCOUNT_FAILED("Create new account failed");

    private String opType;
    private FailedOperationType(String opType){
        this.opType = opType;
    }

    @Override
    public String toString(){
        return this.opType;
    }
}
