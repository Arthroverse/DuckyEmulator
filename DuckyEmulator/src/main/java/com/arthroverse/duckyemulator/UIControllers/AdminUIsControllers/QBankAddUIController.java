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
import com.arthroverse.duckyemulator.Utilities.FileHandler.FileHandler;
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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QBankAddUIController implements Initializable {

    @FXML
    private MFXComboBox<String> choiceBoxSelectTopic;

    @FXML
    private RadioButton radBtnCorrect1;

    @FXML
    private RadioButton radBtnCorrect2;

    @FXML
    private RadioButton radBtnCorrect3;

    @FXML
    private RadioButton radBtnCorrect4;

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
    private MFXButton btnAddNewQuestion;

    @FXML
    private TextArea txtAreaQStatement;

    @FXML
    private TextArea txtAreaQChoice4;

    @FXML
    private TextArea txtAreaQChoice3;

    @FXML
    private MFXButton btnResetField;

    @FXML
    private MFXButton btnAddTopic;

    @FXML
    private MFXButton btnRemoveTopic;

    @FXML
    private TableView<Topics> tableViewSelectedTopic;

    @FXML
    private TableColumn<Topics, String> tableColSelectedTopicName;

    private StringBuilder errorMessage = new StringBuilder();

    private static ArrayList<Topics> selectedTopics = new ArrayList<>();

    public void resetAllDatas(){
        choiceBoxSelectTopic.clearSelection();
        radBtnCorrect1.setSelected(false);
        radBtnCorrect2.setSelected(false);
        radBtnCorrect3.setSelected(false);
        radBtnCorrect4.setSelected(false);
        txtFieldImagePath.setText(null);
        choiceBoxSelectClass.clearSelection();
        txtAreaQChoice2.setText(null);
        txtAreaQChoice1.setText(null);
        txtAreaQStatement.setText(null);
        txtAreaQChoice4.setText(null);
        txtAreaQChoice3.setText(null);
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
                quest.setChoice1(txtAreaQChoice1.getText());
                quest.setChoice2(txtAreaQChoice2.getText());
                quest.setChoice3(txtAreaQChoice3.getText());
                quest.setChoice4(txtAreaQChoice4.getText());
                if(radBtnCorrect1.isSelected()){
                    quest.setCorrectAnswer(txtAreaQChoice1.getText());
                }else if(radBtnCorrect2.isSelected()){
                    quest.setCorrectAnswer(txtAreaQChoice2.getText());
                }else if(radBtnCorrect3.isSelected()){
                    quest.setCorrectAnswer(txtAreaQChoice3.getText());
                }else if(radBtnCorrect4.isSelected()){
                    quest.setCorrectAnswer(txtAreaQChoice4.getText());
                }
                if(txtFieldImagePath.getText() == null){
                    txtFieldImagePath.setText("");
                }
                quest.setImagePath(txtFieldImagePath.getText());
                Questions.insert(quest);
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
        selectedTopics = new ArrayList<>();
        choiceBoxSelectTopic.setItems(FXCollections.observableArrayList(
                Topics.getSortedTopicNames()));

        choiceBoxSelectClass.setItems(FXCollections.observableArrayList(
                Classifications.getSortedClassificationNames()));
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
                    else btnRemoveTopic.disableProperty().set(true);
                }
        );
        choiceBoxSelectTopic.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSelection, newSelection) -> {
                    if(newSelection != null) btnAddTopic.disableProperty().set(false);
                    else btnAddTopic.disableProperty().set(true);
                }
        );

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

        txtFieldImagePath.setEditable(false);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("*.png, *.jpg, *.jpeg, *.gif, *.bmp",
                        "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );

        btnChooseImagePath.setOnAction(event -> {
            txtFieldImagePath.setText(FileHandler.returnImgPath(fileChooser));
        });

        ToggleGroup tg = new ToggleGroup();
        radBtnCorrect1.setToggleGroup(tg);
        radBtnCorrect2.setToggleGroup(tg);
        radBtnCorrect3.setToggleGroup(tg);
        radBtnCorrect4.setToggleGroup(tg);
    }

    private boolean inputValidation(){
        ObservableList<Topics> temp = tableViewSelectedTopic.getItems();
        if(temp.size() == 0) errorMessage.append(ErrorMessage.QUEST_NO_TOPIC_ASSOCIATED);
        if(choiceBoxSelectClass.getValue() == null) errorMessage.append(ErrorMessage.QUEST_NO_CLASSIFICATION_ASSOCIATED);
        if(txtAreaQStatement.getText().isEmpty()) errorMessage.append(ErrorMessage.QUEST_NO_QUESTION_STATEMENT);
        if(txtAreaQChoice1.getText().isEmpty()
        || txtAreaQChoice2.getText().isEmpty() || txtAreaQChoice3.getText().isEmpty()
        || txtAreaQChoice4.getText().isEmpty()) errorMessage.append(ErrorMessage.QUEST_NO_CHOICE);
        if(!radBtnCorrect1.isSelected() && !radBtnCorrect2.isSelected() &&
                !radBtnCorrect3.isSelected() && !radBtnCorrect4.isSelected()) errorMessage.append(ErrorMessage.QUEST_NO_CORRECT_ANS);
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

            selectedTopics.sort(
                    (t1, t2) ->
                            t1.getTopicName().compareToIgnoreCase(t2.getTopicName())
            );

            tableViewSelectedTopic.setItems(
                    FXCollections.observableArrayList(selectedTopics)
            );
        }else {
            AlertUtil.generateErrorWindow(ErrorTitle.QUEST_UI_CONTROLLER_ADD_TOPIC_FAILED.toString(),
                    FailedOperationType.QUEST_UI_CONTROLLER_ADD_TOPIC_FAILED.toString(),
                    ErrorMessage.QUEST_UI_CONTROLLER_ADD_TOPIC_FAILED.toString());
        }
        choiceBoxSelectTopic.clearSelection();
    }

    @FXML
    void btnRemoveTopic(ActionEvent event) throws IOException {
        Topics t = tableViewSelectedTopic.getSelectionModel().getSelectedItem();
        selectedTopics.remove(t);
        tableViewSelectedTopic.getItems().remove(t);
    }
}
