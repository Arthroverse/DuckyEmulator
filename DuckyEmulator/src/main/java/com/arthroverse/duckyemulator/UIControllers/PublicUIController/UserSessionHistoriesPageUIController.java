package com.arthroverse.duckyemulator.UIControllers.PublicUIController;

import com.arthroverse.duckyemulator.Database.MainDB.PublicBeans.Sessions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class UserSessionHistoriesPageUIController implements Initializable{

    @FXML
    private Label greetingLabel;

    @FXML
    private TableColumn<Sessions, String> tableColCorrectAns;

    @FXML
    private TableColumn<Sessions, String> tableColPauseTime;

    @FXML
    private TableColumn<Sessions, String> tableColPercentage;

    @FXML
    private TableColumn<Sessions, String> tableColSessionName;

    @FXML
    private TableColumn<Sessions, String> tableColStartTime;

    @FXML
    private TableColumn<Sessions, String> tableColTimeTaken;

    @FXML
    private TableColumn<Sessions, Integer> tableColTotalQuests;

    @FXML
    private TableView<Sessions> tableViewSessionHis;

    @FXML
    void btnDeleteSelectedSession(ActionEvent event) {

    }

    @FXML
    void btnGoToHome(ActionEvent event) {

    }

    @FXML
    void btnGoToTestHistory(ActionEvent event) {

    }

    @FXML
    void btnLogout(ActionEvent event) {

    }

    @FXML
    void btnViewSelectedResult(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
