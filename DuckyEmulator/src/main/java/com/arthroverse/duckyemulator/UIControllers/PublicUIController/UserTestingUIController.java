package com.arthroverse.duckyemulator.UIControllers.PublicUIController;

import io.github.palexdev.materialfx.controls.MFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
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

    @FXML
    void btnNextQuestion(ActionEvent event) {

    }

    @FXML
    void btnPreviousQuestion(ActionEvent event) {

    }

    @FXML
    void btnSubmitTest(ActionEvent event) {

    }

    @FXML
    void tableViewQuestNav(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
