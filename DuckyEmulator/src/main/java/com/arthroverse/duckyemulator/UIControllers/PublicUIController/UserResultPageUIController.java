package com.arthroverse.duckyemulator.UIControllers.PublicUIController;

import com.arthroverse.duckyemulator.Database.MainDB.PublicBeans.SessionResult;
import com.arthroverse.duckyemulator.Database.MainDB.PublicBeans.Sessions;
import com.arthroverse.duckyemulator.UIs.Navigator;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserResultPageUIController implements Initializable {

    @FXML
    private Label labelCorrectAnsNum;

    @FXML
    private Label labelPauseTime;

    @FXML
    private Label labelPercentage;

    @FXML
    private Label labelSessionName;

    @FXML
    private Label labelStartTime;

    @FXML
    private Label labelTimeTaken;

    @FXML
    private Label labelTotalQuestions;

    @FXML
    private MFXButton mxfbuttonExit;

    @FXML
    private TableColumn<SessionResult, String> tableColClassification;

    @FXML
    private TableColumn<SessionResult, String> tableColCorrectAnswer;

    @FXML
    private TableColumn<SessionResult, String> tableColIsAnswered;

    @FXML
    private TableColumn<SessionResult, Integer> tableColQuestNum;

    @FXML
    private TableColumn<SessionResult, String> tableColQuestionStatement;

    @FXML
    private TableColumn<SessionResult, String> tableColResult;

    @FXML
    private TableColumn<SessionResult, String> tableColTopic;

    @FXML
    private TableColumn<SessionResult, String> tableColYourAnswer;

    @FXML
    private TableView<SessionResult> tableViewSessionResult;

    @FXML
    void btnExitClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToUserHomePage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Sessions sess = Sessions.getCurrentSession();
        labelSessionName.setText("Session" + sess.getSessionId());
        labelCorrectAnsNum.setText(sess.getTotalCorrectQuestions().toString());
        labelTimeTaken.setText(sess.getTimeTaken());
        labelStartTime.setText(sess.getStartTime());
        labelPercentage.setText(sess.getPercentage() + "%");
        labelTotalQuestions.setText(sess.getTotalQuestions().toString());
        labelPauseTime.setText(sess.getEndTime());

        tableViewSessionResult.setItems(FXCollections.observableList(sess.getTestResult()));

        tableColQuestNum.setCellValueFactory((result) -> {
            return result.getValue().getUserInputInformation().getQuestionNumberProperty();
        });

        tableColIsAnswered.setCellValueFactory((result) -> {
            return result.getValue().getUserInputInformation().getIsAnsweredForNavProperty();
        });

        tableColTopic.setCellValueFactory((result) -> {
            return result.getValue().getAssociatedTopicsAsStringProperty();
        });

        tableColClassification.setCellValueFactory((result) -> {
            return result.getValue().getQuestion().getQuestionClassificationProperty();
        });

        tableColQuestionStatement.setCellValueFactory((result) -> {
            return result.getValue().getQuestion().getQuestionStatementProperty();
        });

        tableColResult.setCellValueFactory((result) -> {
            return result.getValue().getIsCorrectAsStringProperty();
        });

        tableColYourAnswer.setCellValueFactory((result) -> {
            return result.getValue().getUserInputInformation().getUserAnswerProperty();
        });

        tableColCorrectAnswer.setCellValueFactory((result) -> {
            return result.getValue().getQuestion().getCorrectAnswerProperty();
        });

        tableColResult.setCellFactory(column -> {
            return new TableCell<SessionResult, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        SessionResult sr = getTableView().getItems().get(getIndex());
                        if (sr != null) {
                            if (!sr.getIsCorrect()) {
                                setStyle("-fx-background-color: #e57373; -fx-border-color: black;");
                            } else {
                                setStyle("-fx-background-color: lightgreen; -fx-border-color: black;");
                            }
                        }
                    }
                }
            };
        });
    }
}

