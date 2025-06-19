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
 * @author ducksabervn
 */
package com.arthroverse.duckyemulator.UIControllers.PublicUIController;

import com.arthroverse.duckyemulator.Database.MainDB.CredentialBeans.Users;
import com.arthroverse.duckyemulator.Database.MainDB.PublicBeans.Sessions;
import com.arthroverse.duckyemulator.UIs.Navigator;
import com.arthroverse.duckyemulator.Utilities.Constant.Reusable;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controller class for UserHomePage.fxml
 * Contains field declarations for FXML elements that would have fx:id attributes
 *
 * Note: Currently the FXML file has no fx:id attributes.
 * Add fx:id="fieldName" to FXML elements to connect them to these fields.
 */
public class UserHomePageUIController implements Initializable {
    @FXML
    private Label greetingLabel;

    @FXML
    private MFXScrollPane scrollPaneMain;

    @FXML
    private VBox scrollPaneContentVBox;

    private static LocalDateTime startTime;

    public static LocalDateTime getStartTime(){
        return startTime;
    }

    @FXML
    public void btnStartSession(ActionEvent event) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        Sessions session = new Sessions();
        Sessions.setCurrentSession(session);
        session.setSessionId(now.format(formatter));
        session.setStartTime(now.format(formatter));
        Sessions.insert(session);
        startTime = LocalDateTime.now();
        Navigator.getInstance().goToTestPage();
    }

    @FXML
    public void btnGoToHome(ActionEvent event) throws IOException {
        Navigator.getInstance().goToUserHomePage();
    }

    @FXML
    public void btnLogout(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLoginPage();
    }

    @FXML
    public void btnGoToTestHistory(ActionEvent event) throws IOException {
        Navigator.getInstance().goToUserSessionHistories();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        greetingLabel.setText(String.format(Reusable.DEFAULT_GREETING.toString(),
                Users.getUserName()));
    }
}