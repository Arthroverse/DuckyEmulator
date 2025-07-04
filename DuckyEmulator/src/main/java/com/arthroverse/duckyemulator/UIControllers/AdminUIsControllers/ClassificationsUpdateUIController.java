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
package com.arthroverse.duckyemulator.UIControllers.AdminUIsControllers;

import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Classifications;
import com.arthroverse.duckyemulator.UIs.Navigator;
import com.arthroverse.duckyemulator.Utilities.Constant.ErrorMessage;
import com.arthroverse.duckyemulator.Utilities.Constant.ErrorTitle;
import com.arthroverse.duckyemulator.Utilities.Constant.FailedOperationType;
import com.arthroverse.duckyemulator.Utilities.Constant.WarningMessage;
import com.arthroverse.duckyemulator.Utilities.Constant.WarningTitle;
import com.arthroverse.duckyemulator.Utilities.PromptAlert.AlertUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ClassificationsUpdateUIController {

    @FXML
    private MFXButton btnUpdateClassification;

    @FXML
    private TextField txtFieldClassificationName;

    @FXML
    private TextArea txtAreaClassificationDescription;

    @FXML
    private MFXButton btnResetField;

    private static StringBuilder errorMessage = new StringBuilder();

    private static Classifications updateClass;

    private static String oldClassificationName;

    @FXML
    void btnUpdateClassificationClick(ActionEvent event) throws IOException {
        updateClass.setClassification(txtFieldClassificationName.getText());
        updateClass.setClassificationDescription((txtAreaClassificationDescription.getText()));
        if(inputValidation()) Classifications.update(updateClass, oldClassificationName);
        if(errorMessage.toString().isEmpty()){
            Navigator.getInstance().closeSecondStage();
            Navigator.getInstance().goToTopicClassIndex();
        }
        errorMessage = new StringBuilder();
    }

    @FXML
    void btnResetFieldClick(ActionEvent event) {
        boolean isOk = AlertUtil.generateWarningWindow(WarningTitle.UNIVERSAL_RESET_FIELD.toString(),
                WarningMessage.UNIVERSAL_RESET_FIELD.toString());
        if(isOk){
            txtFieldClassificationName.setText(updateClass.getClassification());
            txtAreaClassificationDescription.setText(updateClass.getClassificationDescription());
        }
    }

    public void initialize(Classifications c){
        updateClass = c;
        oldClassificationName = c.getClassification();
        txtFieldClassificationName.setText(c.getClassification());
        txtAreaClassificationDescription.setText(c.getClassificationDescription());
    }

    private boolean inputValidation(){
        if(txtFieldClassificationName.getText().isEmpty()) errorMessage.append(ErrorMessage.CLASS_NO_CLASSIFICATION_NAME);
        if(!errorMessage.toString().isEmpty()){
            AlertUtil.generateErrorWindow(ErrorTitle.CLASS_UI_CONTROLLER_UPDATE_CLASS_FAILED.toString(),
                    FailedOperationType.CLASS_UI_CONTROLLER_UPDATE_CLASS_FAILED.toString(),
                    errorMessage.toString());
            return false;
        }
        return true;
    }
}
