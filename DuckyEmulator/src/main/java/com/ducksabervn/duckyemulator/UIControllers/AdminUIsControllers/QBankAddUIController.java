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

import com.ducksabervn.duckyemulator.Database.MainDB.AdminBeans.Classifications;
import com.ducksabervn.duckyemulator.Database.MainDB.AdminBeans.Questions;
import com.ducksabervn.duckyemulator.Database.MainDB.AdminBeans.Topics;
import com.ducksabervn.duckyemulator.UIs.Navigator;
import com.ducksabervn.duckyemulator.Utilities.Constant.ErrorMessage;
import com.ducksabervn.duckyemulator.Utilities.Constant.ErrorTitle;
import com.ducksabervn.duckyemulator.Utilities.Constant.FailedOperationType;
import com.ducksabervn.duckyemulator.Utilities.Constant.WarningMessage;
import com.ducksabervn.duckyemulator.Utilities.Constant.WarningTitle;
import com.ducksabervn.duckyemulator.Utilities.FileHandler.FileHandler;
import com.ducksabervn.duckyemulator.Utilities.PromptAlert.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static
        com.ducksabervn.duckyemulator.Database.MainDB.AdminBeans.Classifications.classificationNames;
import static
        com.ducksabervn.duckyemulator.Database.MainDB.AdminBeans.Topics.topicNames;

public class QBankAddUIController implements Initializable {

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
    private Button btnAddNewQuestion;

    @FXML
    private TextArea txtAreaQStatement;

    @FXML
    private TextArea txtxAreaQChoice4;

    @FXML
    private TextArea txtxAreaQChoice3;

    @FXML
    private Button btnResetField;

    @FXML
    private Button btnAddTopic;

    @FXML
    private Button btnRemoveTopic;

    @FXML
    private TableView<Topics> tableViewSelectedTopic;

    @FXML
    private TableColumn<Topics, String> tableColSelectedTopicName;

    private ArrayList<String> topicName = new ArrayList<>();

    private ArrayList<String> className = new ArrayList<>();

    private StringBuilder errorMessage = new StringBuilder();

    private static ArrayList<Topics> selectedTopics = new ArrayList<>();

    private static String currentSelectedClassification;

    private static String currentQuestionStatement;

    private static String currentChoice1;

    private static String currentChoice2;

    private static String currentChoice3;

    private static String currentChoice4;

    private static String currentCorrectChoice;

    private static String currentImagePath;

    public static void resetAllDatas(){
        currentSelectedClassification = null;
        currentQuestionStatement = null;
        currentChoice1 = null;
        currentChoice2 = null;
        currentChoice3 = null;
        currentChoice4 = null;
        currentCorrectChoice = null;
        currentImagePath = null;
        selectedTopics = new ArrayList<>();
    }

    @FXML
    void btnChooseImagePathClick(ActionEvent event) {

    }

    @FXML
    void btnAddNewQuestionClick(ActionEvent event) throws IOException {
        try{
            if(inputValidation()){
                Questions quest = new Questions();
                ArrayList<String> selectedTopicNames = new ArrayList<>();
                for(Topics t: selectedTopics){
                    selectedTopicNames.add(t.getTopicName());
                }
                ArrayList<Integer> selectedTopicIds = Topics.findingTopicIds(selectedTopicNames);
                quest.setForeignKeyTopicId(selectedTopicIds);
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
                quest.setImagePath(txtFieldImagePath.getText());
                Questions.insert(quest);
            }
            if(errorMessage.toString().isEmpty()){
                Navigator.getInstance().closeSecondStage();
                Navigator.getInstance().goToQBankIndex();
                selectedTopics = new ArrayList<>();
                resetAllDatas();
            }
            errorMessage = new StringBuilder();
        }catch(Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, ErrorTitle.QUEST_UI_CONTROLLER_ADD_QUEST_FAILED.toString());
        }
    }

    @FXML
    void btnResetFieldClick(ActionEvent event) {
        boolean isOk = AlertUtil.generateWarningWindow(WarningTitle.UNIVERSAL_RESET_FIELD.toString(),
                WarningMessage.UNIVERSAL_RESET_FIELD.toString());
        if(isOk){
            resetAllDatas();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBoxSelectTopic.setItems(FXCollections.observableArrayList(topicNames.values()));

        choiceBoxSelectClass.setItems(FXCollections.observableArrayList(classificationNames.values()));

        choiceBoxCorrectAns.setItems(FXCollections.observableArrayList(
                "Choice 1", "Choice 2", "Choice 3", "Choice 4"
        ));
        loadLastSavedData();
        tableViewSelectedTopic.setItems(
                FXCollections.observableArrayList(selectedTopics)
        );
        btnAddTopic.disableProperty().set(true);
        btnRemoveTopic.disableProperty().set(true);
        tableColSelectedTopicName.setCellValueFactory(
                (selectedTopics) -> {
                    return selectedTopics.getValue().getTopicNameProperty();
                }
        );
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
        txtFieldImagePath.setEditable(false);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("*.png, *.jpg, *.jpeg, *.gif, *.bmp",
                        "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );

        btnChooseImagePath.setOnAction(event -> {
            txtFieldImagePath.setText(FileHandler.returnImgPath(fileChooser));
        });
    }

    private boolean inputValidation(){
        ObservableList<Topics> temp = tableViewSelectedTopic.getItems();
        if(temp.size() == 0) errorMessage.append(ErrorMessage.QUEST_NO_TOPIC_ASSOCIATED);
        if(choiceBoxSelectClass.getValue() == null) errorMessage.append(ErrorMessage.QUEST_NO_CLASSIFICATION_ASSOCIATED);
        if(txtAreaQStatement.getText() == null) errorMessage.append(ErrorMessage.QUEST_NO_QUESTION_STATEMENT);
        if(txtxAreaQChoice1.getText() == null
        || txtxAreaQChoice2.getText() == null|| txtxAreaQChoice3.getText().isEmpty()
        || txtxAreaQChoice4.getText() == null) errorMessage.append(ErrorMessage.QUEST_NO_CHOICE);
        if(choiceBoxCorrectAns.getValue() == null) errorMessage.append(ErrorMessage.QUEST_NO_CORRECT_ANS);
        if(txtFieldImagePath.getText() == null) txtFieldImagePath.setText("");

        if(!errorMessage.toString().isEmpty()){
            AlertUtil.generateErrorWindow(ErrorTitle.QUEST_UI_CONTROLLER_ADD_QUEST_FAILED.toString(),
                    FailedOperationType.QUEST_UI_CONTROLLER_ADD_QUESTION_FAILED.toString(),
                    errorMessage.toString());
            return false;
        }
        return true;
    }

    @FXML
    void btnAddTopic(ActionEvent event) throws IOException {
        ArrayList<String> selectedTopicNames = new ArrayList<>();
        for(Topics t: selectedTopics){
            selectedTopicNames.add(t.getTopicName());
        }
        if(!selectedTopicNames.contains(choiceBoxSelectTopic.getValue())){
            selectedTopics.add(Topics.findingTopics(choiceBoxSelectTopic.getValue()));
            saveCurrentInputData();
            Navigator.getInstance().closeSecondStage();
            Navigator.getInstance().goToQBankAdd();
        }else{
            AlertUtil.generateErrorWindow(ErrorTitle.QUEST_UI_CONTROLLER_ADD_TOPIC_FAILED.toString(),
                    FailedOperationType.QUEST_UI_CONTROLLER_ADD_TOPIC_FAILED.toString(),
                    ErrorMessage.QUEST_UI_CONTROLLER_ADD_TOPIC_FAILED.toString());
        }
    }

    @FXML
    void btnRemoveTopic(ActionEvent event) throws IOException {
        Topics t = tableViewSelectedTopic.getSelectionModel().getSelectedItem();
        selectedTopics.remove(t);
        tableViewSelectedTopic.getItems().remove(t);
        saveCurrentInputData();
        Navigator.getInstance().closeSecondStage();
        Navigator.getInstance().goToQBankAdd();
    }

    private void saveCurrentInputData(){
        currentSelectedClassification = choiceBoxSelectClass.getValue();
        currentQuestionStatement = txtAreaQStatement.getText();
        currentChoice1 = txtxAreaQChoice1.getText();
        currentChoice2 = txtxAreaQChoice2.getText();
        currentChoice3 = txtxAreaQChoice3.getText();
        currentChoice4 = txtxAreaQChoice4.getText();
        currentCorrectChoice = choiceBoxCorrectAns.getValue();
        currentImagePath = txtFieldImagePath.getText();
    }

    private void loadLastSavedData(){
        choiceBoxSelectClass.setValue(currentSelectedClassification);
        txtAreaQStatement.setText(currentQuestionStatement);
        txtxAreaQChoice1.setText(currentChoice1);
        txtxAreaQChoice2.setText(currentChoice2);
        txtxAreaQChoice3.setText(currentChoice3);
        txtxAreaQChoice4.setText(currentChoice4);
        choiceBoxCorrectAns.setValue(currentCorrectChoice);
        txtFieldImagePath.setText(currentImagePath);
    }
}
