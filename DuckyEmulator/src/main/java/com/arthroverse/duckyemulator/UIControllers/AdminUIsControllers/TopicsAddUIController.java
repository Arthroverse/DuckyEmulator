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

import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Topics;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TopicsAddUIController implements Initializable {

    @FXML
    private TextArea txtAreaTopicDescription;

    @FXML
    private MFXButton btnAddNewTopic;

    @FXML
    private MFXButton btnResetField;

    @FXML
    private TextField txtFieldTopicName;

    private static StringBuilder errorMessage = new StringBuilder();

    @FXML
    void btnAddNewTopicClick(ActionEvent event) throws IOException {
        Topics t = new Topics();
        t.setTopicName(txtFieldTopicName.getText());
        t.setTopicDescription(txtAreaTopicDescription.getText());
        if(inputValidation()) Topics.insert(t);
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
            txtFieldTopicName.setText(null);
            txtAreaTopicDescription.setText(null);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private boolean inputValidation(){
        if(txtFieldTopicName.getText().isEmpty()) errorMessage.append(ErrorMessage.TOPIC_NO_TOPIC_NAME);
        if(!errorMessage.toString().isEmpty()){
            AlertUtil.generateErrorWindow(ErrorTitle.TOPIC_UI_CONTROLLER_ADD_TOPIC_FAILED.toString(),
                    FailedOperationType.TOPIC_UI_CONTROLLER_ADD_NEW_TOPIC_FAILED.toString(),
                    errorMessage.toString());
            return false;
        }
        return true;
    }

    @FXML
    void btnLogout(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLoginPage();
    }
}
