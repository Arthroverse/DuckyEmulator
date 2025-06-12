package com.arthroverse.duckyemulator.UIControllers.PublicUIController;

import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Questions;
import com.arthroverse.duckyemulator.Database.MainDB.PublicBeans.Sessions;
import com.arthroverse.duckyemulator.Utilities.FileHandler.FileHandler;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UserTestingUIController implements Initializable{

    @FXML
    private ImageView imageViewQuestImg;

    @FXML
    private Label labelCurrentQuestion;

    @FXML
    private Label labelElapsedTime;

    @FXML
    private Button btnPrimaryQuestCounter;

    @FXML
    private Label labelQuestionStatement;

    @FXML
    private RadioButton radBtnFirst;

    @FXML
    private RadioButton radBtnSecond;

    @FXML
    private RadioButton radBtnThird;

    @FXML
    private RadioButton radBtnFourth;

    @FXML
    private TableColumn<String, Integer> tableColQuestNum;

    @FXML
    private TableColumn<String, String> tableColQuestStatus;

    @FXML
    private TableView<String> tableViewQuestNav;

    @FXML
    private VBox vBoxQuestNav;

    @FXML
    private MFXButton btnNextQuestion;

    @FXML
    private MFXButton btnPreviousQuestion;

    private static LocalDateTime endTime;

    private static String timeTakenAsString;

    private static long timeTakenInSeconds;

    private static int currentQuestionNum = 1;

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
    void btnNextQuestionClick(ActionEvent event) {
        btnPreviousQuestion.disableProperty().set(false);
        currentQuestionNum++;
        deployQuestion();
        if(currentQuestionNum == Sessions.getCurrentSession().getTotalQuestions()){
            btnNextQuestion.disableProperty().set(true);
        }

    }

    @FXML
    void btnPreviousQuestionClick(ActionEvent event) {
        btnNextQuestion.disableProperty().set(false);
        currentQuestionNum--;
        deployQuestion();
        if(currentQuestionNum == 1){
            btnPreviousQuestion.disableProperty().set(true);
        }
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

    @Override
    public void initialize(URL location, ResourceBundle resources){
        timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(1000), ae -> incrementTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        vBoxQuestNav.setVisible(false); //automatically hide the quest navigator for user to have more space
        vBoxQuestNav.setManaged(false); //hiding isn't enough, so we have to make sure that they don't occupy any space
        deployQuestion();
        btnPreviousQuestion.disableProperty().set(true);
        imageViewQuestImg.setPreserveRatio(true);
    }

    private void incrementTime() {
        time = time.plusSeconds(1);
        labelElapsedTime.setText(time.format(dtf));
    }

    @FXML
    public void btnPrimaryQuestCounterClick(){
        if(vBoxQuestNav.isManaged() && vBoxQuestNav.isVisible()){
            vBoxQuestNav.setVisible(false);
            vBoxQuestNav.setManaged(false);
        }else{
            vBoxQuestNav.setVisible(true);
            vBoxQuestNav.setManaged(true);
        }
    }

    private void deployQuestion(){
        int qId = Sessions.getCurrentSession().getQuestionNumAndIdPair().get(currentQuestionNum);
        Questions question = Sessions.getCurrentSession().getSelectedQuestions().get(qId);

        int totalQuestions = Sessions.getCurrentSession().getQuestionNumAndIdPair().size();
        labelCurrentQuestion.setText(String.format("Question %d", currentQuestionNum));
        btnPrimaryQuestCounter.setText(String.format("Question %d of %d", currentQuestionNum, totalQuestions));

        labelQuestionStatement.setText(question.getQuestionStatement());
        radBtnFirst.setText(question.getChoice1());
        radBtnSecond.setText(question.getChoice2());
        radBtnThird.setText(question.getChoice3());
        radBtnFourth.setText(question.getChoice4());
        if(!question.getImagePath().equals("")){
            Image image = FileHandler.imageLoader(question.getImagePath());
            imageViewQuestImg.setImage(image);
        }else{
            imageViewQuestImg.setImage(null);
        }
    }
}
