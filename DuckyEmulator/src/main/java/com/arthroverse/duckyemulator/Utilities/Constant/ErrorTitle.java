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

public enum ErrorTitle {

    ALERT_UTIL_MIDDLE_TEXT(" failed due to following reason:\n"),

    ALERT_UTIL_EXCEPTION_TITLE("A fatal error occurred"),

    SQL_CLASS_INIT_QUERY_FAILED("Classification initial query failed"),

    SQL_CLASS_PARTIAL_QUERY_FAILED("Classification partial query failed"),

    SQL_CLASS_PAGINATION_SET_PAGE_FAILED("Classification pagination setPage operation failed"),

    SQL_CLASS_DELETION_FAILED("Classification delete operation failed"),

    SQL_CLASS_INSERTION_FAILED("Classification insert operation failed"),

    SQL_CLASS_UPDATION_FAILED("Classification update operation failed"),

    SQL_QUEST_SELECT_QID_FAILED("Question select qId operation failed"),

    SQL_QUEST_PARTIAL_SELECT_FAILED("Question partial select operation failed"),

    SQL_QUEST_ADD_QUESTION_FAILED("Add new question operation failed"),

    SQL_QUEST_SET_PAGE_FAILED("Question pagination setPage operation failed"),

    SQL_QUEST_QTRELATIONSHIP_DELETE_FAILED("Question qtRelationship delete operation failed"),

    SQL_QUEST_DELETE_FAILED("Question delete operation failed"),

    SQL_QUEST_INSERTION_FAILED("Question insert operation failed"),

    SQL_QUEST_QTRELATIONSHIP_INSERT_FAILED("Question qtRelationship insert operation failed"),

    SQL_QUEST_UPDATE_FAILED("Question update operation failed"),

    SQL_TOPIC_INIT_QUERY_FAILED("Topics initial query operation failed"),

    SQL_TOPIC_PARTIAL_SELECT_FAILED("Topics partial select operation failed"),

    SQL_TOPIC_PAGINATION_SET_PAGE_FAILED("Topics pagination setPage operation failed"),

    SQL_TOPIC_FIND_TOTAL_RELATED_QUEST_FAILED("Topics find total related question operation failed"),

    SQL_TOPIC_DELETE_FAILED("Topics delete operation failed"),

    SQL_TOPIC_INSERT_FAILED("Topics insert operation failed"),

    SQL_TOPIC_UPDATE_FAILED("Topics update operation failed"),

    CLASS_UI_CONTROLLER_ADD_CLASS_FAILED("Add new classification failed"),

    CLASS_UI_CONTROLLER_UPDATE_CLASS_FAILED("Update current classification failed"),

    QUEST_UI_CONTROLLER_ADD_QUEST_FAILED("Add new question failed"),

    QUEST_UI_CONTROLLER_ADD_TOPIC_FAILED("Add new topic failed"),

    QUEST_UI_CONTROLLER_DELETE_QUEST_FAILED("Delete question failed"),

    QUEST_UI_CONTROLLER_UPDATE_QUEST_FAILED("Update current question failed"),

    TOPIC_UI_CONTROLLER_ADD_TOPIC_FAILED("Add new topic failed"),

    TOPIC_UI_CONTROLLER_UPDATE_TOPIC_FAILED("Update new topic failed"),

    LOGIN_UI_CONTROLLER_LOGIN_FAILED("Login failed"),

    SQL_USER_CHECK_CREDENTIAL_FAILED("Check credential failed"),

    SQL_USER_LOAD_DB_FAILED("Users DB select all failed"),

    SQL_USER_LOAD_USERNAME_FAILED("UserName load failed !");

    private String title;

    private ErrorTitle(String title){
        this.title = title;
    }

    @Override
    public String toString(){
        return title;
    }
}
