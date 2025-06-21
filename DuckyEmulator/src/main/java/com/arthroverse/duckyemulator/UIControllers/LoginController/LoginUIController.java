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
package com.arthroverse.duckyemulator.UIControllers.LoginController;

import com.arthroverse.duckyemulator.Database.MainDB.CredentialBeans.Users;
import com.arthroverse.duckyemulator.UIs.Navigator;
import com.arthroverse.duckyemulator.Utilities.Constant.ErrorMessage;
import com.arthroverse.duckyemulator.Utilities.Constant.ErrorTitle;
import com.arthroverse.duckyemulator.Utilities.Constant.FailedOperationType;
import com.arthroverse.duckyemulator.Utilities.PromptAlert.AlertUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginUIController implements Initializable {

    @FXML
    private MFXButton btnLogin;

    @FXML
    private MFXPasswordField passwordFieldUsrPw;

    @FXML
    private MFXComboBox<String> choiceBoxUserType;

    @FXML
    private MFXTextField txtUsrNameEmail;

    private static StringBuilder errorMessage;

    @FXML
    void btnLoginClick(ActionEvent event) throws IOException {
        if(inputValidation()){
            Users u = new Users();
            u.setUserEmail(txtUsrNameEmail.getText());
            u.setUserPassword(passwordFieldUsrPw.getText());
            u.setUserType(choiceBoxUserType.getValue());
            if(Users.checkCredential(u)){
                if(u.getUserType().equals("Admin")){
                    Navigator.getInstance().closeSecondStage();
                    Navigator.getInstance().goToAdminHomePage();
                }else if(u.getUserType().equals("User")){
                    Navigator.getInstance().closeSecondStage();
                    Navigator.getInstance().goToUserHomePage();
                }
            }
            else AlertUtil.generateErrorWindow(
                    ErrorTitle.LOGIN_UI_CONTROLLER_LOGIN_FAILED.toString(),
                    FailedOperationType.LOGIN_UI_CONTROLLER_LOGIN_FAILED.toString(),
                    ErrorMessage.LOGIN_UI_CONTROLLER_INVALID_CREDENTIAL.toString());
        }
        errorMessage = new StringBuilder();
    }

    @FXML
    void btnSignUpHere(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBoxUserType.setItems(FXCollections.observableArrayList("User", "Admin"));
        errorMessage = new StringBuilder();


        //for user testing, since it takes too much time to type all of ts in
        choiceBoxUserType.setValue("User");
        txtUsrNameEmail.setText("user@example.com");
        passwordFieldUsrPw.setText("a");

          //for admin testing, since it takes too much time to type all of ts in
//        choiceBoxUserType.setValue("Admin");
//        txtUsrNameEmail.setText("admin@example.com");
//        passwordFieldUsrPw.setText("a");

    }

    private boolean inputValidation(){
        if(txtUsrNameEmail.getText() == null)
            errorMessage.append(ErrorMessage.LOGIN_UI_CONTROLLER_NO_USERNAME_INPUTTED);
        else
            if(!(Users.checkValidEmail(txtUsrNameEmail.getText())))
                errorMessage.append(ErrorMessage.LOGIN_UI_CONTROLLER_INVALID_EMAIL);
        if(passwordFieldUsrPw.getText() == null)
            errorMessage.append(ErrorMessage.LOGIN_UI_CONTROLLER_NO_PASSWORD_INPUTTED);
        if(choiceBoxUserType.getValue() == null)
            errorMessage.append(ErrorMessage.LOGIN_UI_CONTROLLER_NO_USERTYPE_CHOOSEN);
        if(!errorMessage.toString().isEmpty()) {
            AlertUtil.generateErrorWindow(
                    ErrorTitle.LOGIN_UI_CONTROLLER_LOGIN_FAILED.toString(),
                    FailedOperationType.LOGIN_UI_CONTROLLER_LOGIN_FAILED.toString(),
                    errorMessage.toString());
            return false;
        }
        return true;
    }
}
