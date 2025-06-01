package com.arthroverse.duckyemulator.UIControllers.PublicUIController;

import io.github.palexdev.materialfx.controls.MFXRadioButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UserTestingUIController implements Initializable {

    @FXML
    private ImageView imageViewQuestImg;

    @FXML
    private Label labelCurrentQuestion;

    @FXML
    private Label labelElapsedTime;

    @FXML
    private Label labelPrimaryQuestCounter;

    @FXML
    private Label labelQuestionStatement;

    @FXML
    private Label labelSecondaryQuestCounter;

    @FXML
    private MFXRadioButton mfxRadBtnFirst;

    @FXML
    private MFXRadioButton mfxRadBtnFourth;

    @FXML
    private MFXRadioButton mfxRadBtnSecond;

    @FXML
    private MFXRadioButton mfxRadBtnThird;

    @FXML
    private TableColumn<String, Integer> tableColQuestNum;

    @FXML
    private TableColumn<String, String> tableColQuestStatus;

    @FXML
    private TableView<String> tableViewQuestNav;

    private static LocalDateTime endTime;

    private static String timeTakenAsString;

    private static long timeTakenInSeconds;

    Timeline timeline;
    LocalTime time = LocalTime.parse("00:00:00");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static LocalDateTime getEndTime(){
        return endTime;
    }

    public static String getTimeTakenAsString(){
        return timeTakenAsString;
    }

    public static long getTimeTakenInSeconds(){
        return timeTakenInSeconds;
    }

    @FXML
    void btnNextQuestion(ActionEvent event) {

    }

    @FXML
    void btnPreviousQuestion(ActionEvent event) {

    }

    @FXML
    void btnSubmitTest(ActionEvent event) {
        timeline.stop();
        endTime = LocalDateTime.now();
        Duration timeTaken = Duration.between(UserHomePageUIController.getStartTime(), endTime);
        timeTakenInSeconds = timeTaken.getSeconds();
        timeTakenAsString = String.format("%dh:%dm:%ds",
                timeTaken.toHoursPart(), timeTaken.toMinutesPart(), timeTaken.toSecondsPart());
    }

    @FXML
    void tableViewQuestNav(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(1000), ae -> incrementTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void incrementTime() {
        time = time.plusSeconds(1);
        labelElapsedTime.setText(time.format(dtf));
    }
}
