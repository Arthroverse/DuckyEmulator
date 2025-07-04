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

public enum ErrorMessage {

    QUEST_NO_TOPIC_ASSOCIATED("A question must be associated with a topic !\n"),

    QUEST_NO_CLASSIFICATION_ASSOCIATED("A question must be associated with a classification !\n"),

    QUEST_NO_QUESTION_STATEMENT("A question must have a question statement !\n"),

    QUEST_NO_CHOICE("A question must have 4 answers !\n"),

    QUEST_NO_CORRECT_ANS("You must select 1 correct answer !\n"),

    TOPIC_NO_TOPIC_NAME("Topic name shouldn't be leave empty !\n"),

    CLASS_NO_CLASSIFICATION_NAME("Classification shouldn't be leave empty !\n"),

    SQL_CLASS_NUM_OF_RELATED_QUESTION("There are %d questions associated with this classification, please delete them first"),

    SQL_TOPIC_NUM_OF_QUEST_RELATED("There are %d questions associated with this topic, please delete them first !"),

    QUEST_UI_CONTROLLER_ADD_TOPIC_FAILED("Topic has been added before !, please choose other topics"),

    QUEST_UI_CONTROLLER_DELETE_QUEST_FAILED("A question must be selected to perform this operation !"),

    LOGIN_UI_CONTROLLER_NO_PASSWORD_INPUTTED("Please enter your password !\n"),

    LOGIN_UI_CONTROLLER_NO_USERNAME_INPUTTED("Please enter your username/email\n"),

    LOGIN_UI_CONTROLLER_NO_USERTYPE_CHOOSEN("Please choose a user type !\n"),

    LOGIN_UI_CONTROLLER_INVALID_EMAIL("Invalid email, please choose another email address !\n"),

    LOGIN_UI_CONTROLLER_INVALID_CREDENTIAL("Failed to authenticate!"),

    CREATE_ACCOUNT_DUPLICATE_EMAIL("Email has been used!, Please choose another email");
    private String errorMessage;

    private ErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString(){
        return errorMessage;
    }
}
