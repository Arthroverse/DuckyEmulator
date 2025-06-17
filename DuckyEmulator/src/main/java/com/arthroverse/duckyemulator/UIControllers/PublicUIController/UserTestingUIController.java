package com.arthroverse.duckyemulator.UIControllers.PublicUIController;

import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Questions;
import com.arthroverse.duckyemulator.Database.MainDB.PublicBeans.SessionInput;
import com.arthroverse.duckyemulator.Database.MainDB.PublicBeans.Sessions;
import com.arthroverse.duckyemulator.Utilities.FileHandler.FileHandler;
import com.arthroverse.duckyemulator.Utilities.PromptAlert.AlertUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
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
    private TableColumn<SessionInput, Integer> tableColQuestNum;

    @FXML
    private TableColumn<SessionInput, String> tableColQuestStatus;

    @FXML
    private TableView<SessionInput> tableViewQuestNav;

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
        tableViewQuestNav.getSelectionModel().clearSelection();
        saveUserAnswer();
        btnPreviousQuestion.disableProperty().set(false);
        currentQuestionNum++;
        deployQuestion();
        if(currentQuestionNum == Sessions.getCurrentSession().getTotalQuestions()){
            btnNextQuestion.disableProperty().set(true);
        }

    }

    @FXML
    void btnPreviousQuestionClick(ActionEvent event) {
        tableViewQuestNav.getSelectionModel().clearSelection();
        saveUserAnswer();
        btnNextQuestion.disableProperty().set(false);
        currentQuestionNum--;
        deployQuestion();
        if(currentQuestionNum == 1){
            btnPreviousQuestion.disableProperty().set(true);
        }
    }

    @FXML
    void btnSubmitTest(ActionEvent event) {
        int totalUnansweredQuestions = 0;
        for(SessionInput si: Sessions.getCurrentSession().getUserInput().values()){
            if(!si.isAnswered()){
                totalUnansweredQuestions++;
            }
        }

        if((totalUnansweredQuestions != 0 &&
                AlertUtil.generateWarningWindow("DuckyEmulator",
                        String.format("You have %d question(s) unanswered, proceed to submit?"
                                , totalUnansweredQuestions))) || totalUnansweredQuestions == 0){
            timeline.stop();
            endTime = LocalDateTime.now();
            Duration timeTaken = Duration.between(UserHomePageUIController.getStartTime(), endTime);
            timeTakenInSeconds = timeTaken.getSeconds();
            timeTakenAsString = String.format("%dh:%dm:%ds",
                    timeTaken.toHoursPart(), timeTaken.toMinutesPart(), timeTaken.toSecondsPart());
        }
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
        ToggleGroup tg = new ToggleGroup();
        radBtnFirst.setToggleGroup(tg);
        radBtnSecond.setToggleGroup(tg);
        radBtnThird.setToggleGroup(tg);
        radBtnFourth.setToggleGroup(tg);

        updateQuestionNavStatus();

        tg.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle != null) {
                SessionInput si = Sessions.getCurrentSession().getUserInput().get(currentQuestionNum);
                si.setAnswered(true);
                si.setIsAnsweredForNav("✅");
                updateQuestionNavStatus();
                saveUserAnswer();
            }
        });



        tableViewQuestNav.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSelection, newSelection) -> {
                    if(newSelection != null){
                        saveUserAnswer();
                        currentQuestionNum = newSelection.getQuestionNumber();
                        if(currentQuestionNum == Sessions.getCurrentSession().getTotalQuestions()){
                            btnNextQuestion.disableProperty().set(true);
                            btnPreviousQuestion.disableProperty().set(false);
                        }else if(currentQuestionNum == 1){
                            btnPreviousQuestion.disableProperty().set(true);
                            btnNextQuestion.disableProperty().set(false);
                        }else{
                            btnPreviousQuestion.disableProperty().set(false);
                            btnNextQuestion.disableProperty().set(false);
                        }
                        deployQuestion();
                    }
                }
        );

        tableViewQuestNav.setItems(FXCollections.observableList(Sessions.getCurrentSession().getIsAnsweredStatForNav()));
        tableColQuestNum.setCellValueFactory((sessionInput) -> {
            return sessionInput.getValue().getQuestionNumberProperty();
        });
        tableColQuestStatus.setCellValueFactory((sessionInput) -> {
            return sessionInput.getValue().getIsAnsweredForNavProperty();
        });
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

        radBtnFirst.setSelected(false);
        radBtnSecond.setSelected(false);
        radBtnThird.setSelected(false);
        radBtnFourth.setSelected(false);

        boolean isAnswered = Sessions.getCurrentSession().getUserInput().get(currentQuestionNum).isAnswered();
        if(isAnswered){
            String userAnwer = Sessions.getCurrentSession().getUserInput().get(currentQuestionNum).getUserAnswer();
            if(radBtnFirst.getText().equals(userAnwer)){
                radBtnFirst.setSelected(true);
            }else if(radBtnSecond.getText().equals(userAnwer)){
                radBtnSecond.setSelected(true);
            }else if(radBtnThird.getText().equals(userAnwer)){
                radBtnThird.setSelected(true);
            }else if(radBtnFourth.getText().equals(userAnwer)){
                radBtnFourth.setSelected(true);
            }
        }
        if(!question.getImagePath().equals("")){
            Image image = FileHandler.imageLoader(question.getImagePath());
            imageViewQuestImg.setImage(image);
        }else{
            imageViewQuestImg.setImage(null);
        }
    }

    private void saveUserAnswer(){
        SessionInput si = Sessions.getCurrentSession().getUserInput().get(currentQuestionNum);

        if(radBtnFirst.isSelected()){
            si.setUserAnswer(radBtnFirst.getText());
        }else if(radBtnSecond.isSelected()){
            si.setUserAnswer(radBtnSecond.getText());
        }else if(radBtnThird.isSelected()){
            si.setUserAnswer(radBtnThird.getText());
        }else if(radBtnFourth.isSelected()){
            si.setUserAnswer(radBtnFourth.getText());
        }
    }

    private void updateQuestionNavStatus(){
        tableColQuestStatus.setCellFactory(column -> {
            return new TableCell<SessionInput, String>() {
                private SessionInput si;
                private InvalidationListener siListener;

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if(si != null && siListener != null){
                        si.getIsAnsweredForNavProperty().removeListener( siListener);
                    }

                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                        si = null;// Clear any previous styling
                    } else {
                        setText(item);

                        si = getTableView().getItems().get(getIndex());

                        siListener = observable -> updateCellStyle();

                        si.getIsAnsweredForNavProperty().addListener( siListener);

                        updateCellStyle();
                    }
                }

                private void updateCellStyle() {
                    if (si != null) {
                        if (!si.isAnswered()) {
                            setStyle("-fx-background-color: lightyellow; -fx-border-color: black;");
                        } else {
                            setStyle("-fx-background-color: lightgreen; -fx-border-color: black;");
                        }
                    }
                }
            };
        });
    }
}
