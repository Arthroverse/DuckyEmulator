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
package com.ducksabervn.duckyemulator.UIControllers.AdminUIsControllers;

import com.ducksabervn.duckyemulator.Database.MainDB.CredentialBeans.Users;
import com.ducksabervn.duckyemulator.UIs.Navigator;
import com.ducksabervn.duckyemulator.Utilities.Constant.Reusable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageUIController implements Initializable{

    @FXML
    private Button btnMenuTopicClass;

    @FXML
    private Button btnMenuQuestion;

    @FXML
    private Button btnMenuHome;

    @FXML
    private Button btnHomeTopicClass;

    @FXML
    private Button btnHomeQuestion;

    @FXML
    private Button btnLogout;

    @FXML
    private Label greetingLabel;

    @FXML
    void btnHomeQuestionClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToQBankIndex();
    }

    @FXML
    void btnHomeTopicClassClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToTopicClassIndex();
    }

    @FXML
    void btnMenuHomeClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHome();
    }

    @FXML
    void btnMenuQuestionClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToQBankIndex();
    }

    @FXML
    void btnMenuTopicClassClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToTopicClassIndex();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        greetingLabel.setText(String.format(Reusable.DEFAULT_GREETING.toString(),
                Users.getUserNameForFrontEnd()));
    }

    @FXML
    void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLoginPage();
    }
}
