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

import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Classifications;
import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Questions;
import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Topics;
import com.arthroverse.duckyemulator.UIs.Navigator;
import com.arthroverse.duckyemulator.Utilities.Constant.ErrorMessage;
import com.arthroverse.duckyemulator.Utilities.Constant.ErrorTitle;
import com.arthroverse.duckyemulator.Utilities.Constant.FailedOperationType;
import com.arthroverse.duckyemulator.Utilities.Constant.WarningMessage;
import com.arthroverse.duckyemulator.Utilities.Constant.WarningTitle;
import com.arthroverse.duckyemulator.Utilities.PromptAlert.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static
        com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Classifications.classificationNames;
import static
        com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Topics.topicNames;
import static
        com.arthroverse.duckyemulator.UIControllers.AdminUIsControllers.QBankIndexUIController.originalQuestion;

public class QBankUpdateUIController implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBoxSelectTopic;

    @FXML
    private ChoiceBox<String> choiceBoxCorrectAns;

    @FXML
    private Button btnChooseImagePath;

    @FXML
    private TextField txtFieldImagePath;

    @FXML
    private ChoiceBox<String> choiceBoxSelectClass;

    @FXML
    private TextArea txtxAreaQChoice2;

    @FXML
    private TextArea txtxAreaQChoice1;

    @FXML
    private TextArea txtAreaQStatement;

    @FXML
    private TextArea txtxAreaQChoice4;

    @FXML
    private TextArea txtxAreaQChoice3;

    @FXML
    private Button btnResetField;

    @FXML
    private Button btnUpdateCurrentQuestion;

    @FXML
    private Button btnAddTopic;

    @FXML
    private Button btnRemoveTopic;

    @FXML
    private TableView<Topics> tableViewSelectedTopic;

    @FXML
    private TableColumn<Topics, String> tableColSelectedTopicName;

    private static StringBuilder errorMessage = new StringBuilder();

    private static Questions updateQuestion;

    private static ArrayList<Topics> selectedTopics = new ArrayList<>();

    @FXML
    void btnChooseImagePathClick(ActionEvent event) {

    }

    @FXML
    void btnUpdateCurrentQuestionClick(ActionEvent event) throws IOException {
        try{
            if(inputValidation()){
                ArrayList<String> selectedTopicNames = new ArrayList<>();
                for(Topics t: selectedTopics){
                    selectedTopicNames.add(t.getTopicName());
                }
                ArrayList<Integer> selectedTopicIds = Topics.findingTopicIds(selectedTopicNames);
                updateQuestion.setForeignKeyTopicId(selectedTopicIds);
                updateQuestion.setForeignKeyClassificationId(
                        Classifications.searchClassification(
                                choiceBoxSelectClass.getValue()
                        )
                );
                updateQuestion.setQuestionStatement(txtAreaQStatement.getText());
                updateQuestion.setChoice1(txtxAreaQChoice1.getText());
                updateQuestion.setChoice2(txtxAreaQChoice2.getText());
                updateQuestion.setChoice3(txtxAreaQChoice3.getText());
                updateQuestion.setChoice4(txtxAreaQChoice4.getText());
                updateQuestion.setCorrectAnswer(choiceBoxCorrectAns.getValue());
                updateQuestion.setImagePath(txtFieldImagePath.getText());
                Questions.update(updateQuestion);
            }
            if(errorMessage.toString().isEmpty()){
                Navigator.getInstance().closeSecondStage();
                Navigator.getInstance().goToQBankIndex();
                selectedTopics = new ArrayList<>();
            }
            errorMessage = new StringBuilder();
        }catch(Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, ErrorTitle.QUEST_UI_CONTROLLER_UPDATE_QUEST_FAILED.toString());
        }
    }

    @FXML
    void btnResetFieldClick(ActionEvent event) {
        boolean isOk = AlertUtil.generateWarningWindow(WarningTitle.UNIVERSAL_RESET_FIELD.toString(),
                WarningMessage.UNIVERSAL_RESET_FIELD.toString());
        if(isOk){
            resetAllFields();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBoxSelectTopic.setItems(FXCollections.observableArrayList(topicNames.values()));

        choiceBoxSelectClass.setItems(FXCollections.observableArrayList(classificationNames.values()));

        choiceBoxCorrectAns.setItems(FXCollections.observableArrayList(
                "Choice 1", "Choice 2", "Choice 3", "Choice 4"
        ));
        txtFieldImagePath.setEditable(false);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("*.png, *.jpg, *.jpeg, *.gif, *.bmp"
                        , "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );

        btnChooseImagePath.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(Navigator.getInstance().getStage());
            if (selectedFile != null) {
                txtFieldImagePath.setText(selectedFile.getPath());
            }
        });
    }

    public void initialize(Questions q){
        updateQuestion = q;
        ArrayList<Integer> listTopicIds = q.getForeignKeyTopicId();
        ArrayList<Topics> listTopics = Topics.findingTopics(listTopicIds);
        selectedTopics = listTopics;
        tableViewSelectedTopic.setItems(FXCollections.observableArrayList(listTopics));
        tableColSelectedTopicName.setCellValueFactory(
                (topic) -> {
                    return topic.getValue().getTopicNameProperty();
                }
        );
        btnAddTopic.disableProperty().set(true);
        btnRemoveTopic.disableProperty().set(true);
        tableViewSelectedTopic.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSelection, newSelection) -> {
                    if(newSelection != null) btnRemoveTopic.disableProperty().set(false);
                }
        );
        choiceBoxSelectTopic.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSelection, newSelection) -> {
                    if(newSelection != null) btnAddTopic.disableProperty().set(false);
                }
        );
        choiceBoxSelectClass.setValue(
                Classifications.searchClassification(
                        q.getForeignKeyClassificationId()
                )
        );
        txtAreaQStatement.setText(q.getQuestionStatement());
        txtxAreaQChoice1.setText(q.getChoice1());
        txtxAreaQChoice2.setText(q.getChoice2());
        txtxAreaQChoice3.setText(q.getChoice3());
        txtxAreaQChoice4.setText(q.getChoice4());
        choiceBoxCorrectAns.setValue(q.getCorrectAnswer());
        txtFieldImagePath.setText(q.getImagePath());
    }

    private boolean inputValidation(){
        ObservableList<Topics> temp = tableViewSelectedTopic.getItems();
        if(temp.size() == 0) errorMessage.append(ErrorMessage.QUEST_NO_TOPIC_ASSOCIATED);
        if(choiceBoxSelectClass.getValue() == null) errorMessage.append(ErrorMessage.QUEST_NO_CLASSIFICATION_ASSOCIATED);
        if(txtAreaQStatement.getText() == null) errorMessage.append(ErrorMessage.QUEST_NO_QUESTION_STATEMENT);
        if(txtxAreaQChoice1.getText() == null
                || txtxAreaQChoice2.getText() == null || txtxAreaQChoice3.getText().isEmpty()
                || txtxAreaQChoice4.getText() == null) errorMessage.append(ErrorMessage.QUEST_NO_CHOICE);
        if(choiceBoxCorrectAns.getValue() == null) errorMessage.append(ErrorMessage.QUEST_NO_CORRECT_ANS);
        if(txtFieldImagePath.getText() == null) txtFieldImagePath.setText("");

        if(!errorMessage.toString().isEmpty()){
            AlertUtil.generateErrorWindow(ErrorTitle.QUEST_UI_CONTROLLER_UPDATE_QUEST_FAILED.toString(),
                    FailedOperationType.QUEST_UI_CONTROLLER_UPDATE_QUESTION_FAILED.toString(),
                    errorMessage.toString());
            return false;
        }
        return true;
    }

    @FXML
    void btnAddTopicClick(ActionEvent event) throws IOException {
        ArrayList<String> selectedTopicNames = new ArrayList<>();
        for(Topics t: selectedTopics){
            selectedTopicNames.add(t.getTopicName());
        }
        if(!selectedTopicNames.contains(choiceBoxSelectTopic.getValue())){
            selectedTopics.add(Topics.findingTopics(choiceBoxSelectTopic.getValue()));
            Navigator.getInstance().closeSecondStage();
            Navigator.getInstance().goToQBankUpdate(this.generateTempQuestionObject());
        }else{
            AlertUtil.generateErrorWindow(ErrorTitle.QUEST_UI_CONTROLLER_ADD_TOPIC_FAILED.toString(),
                    FailedOperationType.QUEST_UI_CONTROLLER_ADD_TOPIC_FAILED.toString(),
                    ErrorMessage.QUEST_UI_CONTROLLER_ADD_TOPIC_FAILED.toString());
        }
    }

    @FXML
    void btnRemoveTopicClick(ActionEvent event) throws IOException {
        Topics t = tableViewSelectedTopic.getSelectionModel().getSelectedItem();
        selectedTopics.remove(t);
        tableViewSelectedTopic.getItems().remove(t);
        Navigator.getInstance().closeSecondStage();
        Navigator.getInstance().goToQBankUpdate(this.generateTempQuestionObject());
    }

    private Questions generateTempQuestionObject(){
        Questions quest = new Questions();
        quest.setQuestionId(updateQuestion.getQuestionId());
        quest.setForeignKeyClassificationId(
                Classifications.searchClassification(
                        choiceBoxSelectClass.getValue()
                )
        );
        quest.setQuestionStatement(txtAreaQStatement.getText());
        quest.setChoice1(txtxAreaQChoice1.getText());
        quest.setChoice2(txtxAreaQChoice2.getText());
        quest.setChoice3(txtxAreaQChoice3.getText());
        quest.setChoice4(txtxAreaQChoice4.getText());
        quest.setCorrectAnswer(choiceBoxCorrectAns.getValue());
        ArrayList<String> selectedTopicNames = new ArrayList<>();
        for(Topics topic: selectedTopics){
            selectedTopicNames.add(topic.getTopicName());
        }
        ArrayList<Integer> selectedTopicIds = Topics.findingTopicIds(selectedTopicNames);
        quest.setForeignKeyTopicId(selectedTopicIds);
        quest.setOldForeignKeyTopicId(updateQuestion.getOldForeignKeyTopicId());
        return quest;
    }

    private void resetAllFields(){
        tableViewSelectedTopic.setItems(
                FXCollections.observableArrayList(
                        Topics.findingTopics(originalQuestion.getForeignKeyTopicId())
                )
        );
        txtFieldImagePath.setText(originalQuestion.getImagePath());
        choiceBoxSelectClass.setValue(
                Classifications.searchClassification(
                        originalQuestion.getForeignKeyClassificationId())
        );
        txtAreaQStatement.setText(originalQuestion.getQuestionStatement());
        txtxAreaQChoice1.setText(originalQuestion.getChoice1());
        txtxAreaQChoice2.setText(originalQuestion.getChoice2());
        txtxAreaQChoice3.setText(originalQuestion.getChoice3());
        txtxAreaQChoice4.setText(originalQuestion.getChoice4());
        choiceBoxCorrectAns.setValue(originalQuestion.getCorrectAnswer());
        txtFieldImagePath.setText(originalQuestion.getImagePath());
    }

}
