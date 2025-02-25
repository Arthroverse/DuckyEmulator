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
