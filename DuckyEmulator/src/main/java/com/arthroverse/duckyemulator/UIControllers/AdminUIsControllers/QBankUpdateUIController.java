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
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QBankUpdateUIController implements Initializable {

    @FXML
    private MFXComboBox<String> choiceBoxSelectTopic;

    @FXML
    private MFXComboBox<String> choiceBoxCorrectAns;

    @FXML
    private MFXButton btnChooseImagePath;

    @FXML
    private TextField txtFieldImagePath;

    @FXML
    private MFXComboBox<String> choiceBoxSelectClass;

    @FXML
    private TextArea txtAreaQChoice2;

    @FXML
    private TextArea txtAreaQChoice1;

    @FXML
    private TextArea txtAreaQStatement;

    @FXML
    private TextArea txtAreaQChoice4;

    @FXML
    private TextArea txtAreaQChoice3;

    @FXML
    private MFXButton btnResetField;

    @FXML
    private MFXButton btnUpdateCurrentQuestion;

    @FXML
    private MFXButton btnAddTopic;

    @FXML
    private MFXButton btnRemoveTopic;

    @FXML
    private TableView<Topics> tableViewSelectedTopic;

    @FXML
    private TableColumn<Topics, String> tableColSelectedTopicName;

    private static StringBuilder errorMessage = new StringBuilder();

    private static Questions originalQuestion;

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
                originalQuestion.setForeignKeyTopicId(selectedTopicIds);
                originalQuestion.setForeignKeyClassificationId(
                        Classifications.searchClassification(
                                choiceBoxSelectClass.getValue()
                        )
                );
                originalQuestion.setQuestionStatement(txtAreaQStatement.getText());
                originalQuestion.setChoice1(txtAreaQChoice1.getText());
                originalQuestion.setChoice2(txtAreaQChoice2.getText());
                originalQuestion.setChoice3(txtAreaQChoice3.getText());
                originalQuestion.setChoice4(txtAreaQChoice4.getText());
                originalQuestion.setCorrectAnswer(choiceBoxCorrectAns.getValue());
                originalQuestion.setImagePath(txtFieldImagePath.getText());
                Questions.update(originalQuestion);
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
        choiceBoxSelectTopic.setItems(FXCollections.observableArrayList(
                Topics.getSortedTopicNames()));

        choiceBoxSelectClass.setItems(FXCollections.observableArrayList(
                Classifications.getSortedClassificationNames()));

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
        tableViewSelectedTopic.setRowFactory(new Callback<TableView<Topics>, TableRow<Topics>>() {
            @Override
            public TableRow<Topics> call(TableView<Topics> tableView2) {
                final TableRow<Topics> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    final int index = row.getIndex();
                    if (index >= 0 && index < tableViewSelectedTopic.getItems().size() && tableViewSelectedTopic.getSelectionModel().isSelected(index)  ) {
                        btnRemoveTopic.disableProperty().set(true);
                        btnAddTopic.disableProperty().set(true);
                        tableViewSelectedTopic.getSelectionModel().clearSelection(index);
                        event.consume();
                    }
                });
                return row;
            }
        });
    }

    public void initialize(Questions q){
        originalQuestion = q;
        ArrayList<Integer> listTopicIds = q.getForeignKeyTopicId();
        ArrayList<Topics> listTopics = Topics.findingTopics(listTopicIds);
        selectedTopics = listTopics;

        selectedTopics.sort(
                (t1, t2) ->
                        t1.getTopicName().compareToIgnoreCase(t2.getTopicName())
        );

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
                    else btnRemoveTopic.disableProperty().set(true);
                }
        );
        choiceBoxSelectTopic.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSelection, newSelection) -> {
                    if(newSelection != null) btnAddTopic.disableProperty().set(false);
                    else btnAddTopic.disableProperty().set(true);
                }
        );
        choiceBoxSelectClass.setValue(
                Classifications.searchClassification(
                        q.getForeignKeyClassificationId()
                )
        );
        txtAreaQStatement.setText(q.getQuestionStatement());
        txtAreaQChoice1.setText(q.getChoice1());
        txtAreaQChoice2.setText(q.getChoice2());
        txtAreaQChoice3.setText(q.getChoice3());
        txtAreaQChoice4.setText(q.getChoice4());
        choiceBoxCorrectAns.setValue(q.getCorrectAnswer());
        txtFieldImagePath.setText(q.getImagePath());
    }

    private boolean inputValidation(){
        ObservableList<Topics> temp = tableViewSelectedTopic.getItems();
        if(temp.size() == 0) errorMessage.append(ErrorMessage.QUEST_NO_TOPIC_ASSOCIATED);
        if(choiceBoxSelectClass.getValue() == null) errorMessage.append(ErrorMessage.QUEST_NO_CLASSIFICATION_ASSOCIATED);
        if(txtAreaQStatement.getText() == null) errorMessage.append(ErrorMessage.QUEST_NO_QUESTION_STATEMENT);
        if(txtAreaQChoice1.getText() == null
                || txtAreaQChoice2.getText() == null || txtAreaQChoice3.getText().isEmpty()
                || txtAreaQChoice4.getText() == null) errorMessage.append(ErrorMessage.QUEST_NO_CHOICE);
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

            selectedTopics.sort(
                    (t1, t2) ->
                            t1.getTopicName().compareToIgnoreCase(t2.getTopicName())
            );

            tableViewSelectedTopic.setItems(FXCollections.observableArrayList(selectedTopics));
        }else{
            AlertUtil.generateErrorWindow(ErrorTitle.QUEST_UI_CONTROLLER_ADD_TOPIC_FAILED.toString(),
                    FailedOperationType.QUEST_UI_CONTROLLER_ADD_TOPIC_FAILED.toString(),
                    ErrorMessage.QUEST_UI_CONTROLLER_ADD_TOPIC_FAILED.toString());
        }
        choiceBoxSelectTopic.clearSelection();
    }

    @FXML
    void btnRemoveTopicClick(ActionEvent event) throws IOException {
        Topics t = tableViewSelectedTopic.getSelectionModel().getSelectedItem();
        selectedTopics.remove(t);
        tableViewSelectedTopic.getItems().remove(t);
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
        txtAreaQChoice1.setText(originalQuestion.getChoice1());
        txtAreaQChoice2.setText(originalQuestion.getChoice2());
        txtAreaQChoice3.setText(originalQuestion.getChoice3());
        txtAreaQChoice4.setText(originalQuestion.getChoice4());
        choiceBoxCorrectAns.setValue(originalQuestion.getCorrectAnswer());
        txtFieldImagePath.setText(originalQuestion.getImagePath());
    }
}
