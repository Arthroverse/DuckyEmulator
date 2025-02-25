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
package com.ducksabervn.duckyemulator.Utilities.PromptAlert;

import com.ducksabervn.duckyemulator.Utilities.Constant.ErrorTitle;
import com.ducksabervn.duckyemulator.Utilities.Constant.Reusable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

public abstract interface AlertUtil {
    public static boolean generateWarningWindow(String warningTitle, String warningMessage){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(warningTitle);
        alert.setHeaderText(warningMessage);
        Optional<ButtonType> confirmationResponse = alert.showAndWait();
        try{
            if(confirmationResponse.get() == ButtonType.OK) return true;
            else return false;
        }catch(Exception e){
            return false;
        }
    }

    public static void generateErrorWindow(String errorTitle, String operationType, String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorTitle);
        alert.setHeaderText(operationType + ErrorTitle.ALERT_UTIL_MIDDLE_TEXT + errorMessage);
        alert.showAndWait();
    }

    public static void generateExceptionViewer(String stackTraceAsString, String title){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(ErrorTitle.ALERT_UTIL_EXCEPTION_TITLE.toString());
        Reusable.ALERT_CONTAINER.getObject().setText(stackTraceAsString);
        alert.getDialogPane().setContent(Reusable.ALERT_CONTAINER.getObject());
        alert.showAndWait();
    }

    public static String generateExceptionString(Throwable t){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }
}
