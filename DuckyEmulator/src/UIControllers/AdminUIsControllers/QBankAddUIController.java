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
package UIControllers.AdminUIsControllers;

import Database.MainDB.Beans.Classifications;
import Database.MainDB.Beans.Questions;
import Database.MainDB.Beans.Topics;
import UIs.Navigator;
import Utilities.PromptAlert.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static Database.MainDB.Beans.Topics.topicsQuestionView;
import static Database.MainDB.Beans.Classifications.classQuestionView;

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

    public static void resetAllDatas(){
        currentSelectedClassification = null;
        currentQuestionStatement = null;
        currentChoice1 = null;
        currentChoice2 = null;
        currentChoice3 = null;
        currentChoice4 = null;
        currentCorrectChoice = null;
        selectedTopics = new ArrayList<>();
    }

    @FXML
    void btnChooseImagePathClick(ActionEvent event) {

    }

    @FXML
    void btnAddNewQuestionClick(ActionEvent event) throws IOException {
        Questions quest = new Questions();
        ArrayList<String> selectedTopicNames = new ArrayList<>();
        for(Topics t: selectedTopics){
            selectedTopicNames.add(t.getTopicName());
        }
        ArrayList<Integer> selectedTopicIds = Topics.findingTopicIds(selectedTopicNames);
        String selectedTopicNamesDisplay = selectedTopicNames.toString();
        quest.setForeignKeyTopicIdForDisplay(selectedTopicNamesDisplay.substring(1, selectedTopicNamesDisplay.length() - 1));
        quest.setForeignKeyTopicId(selectedTopicIds);
        quest.setForeignKeyClassificationId(
                className.indexOf(choiceBoxSelectClass.getValue())
        );
        quest.setQuestionStatement(txtAreaQStatement.getText());
        quest.setChoice1(txtxAreaQChoice1.getText());
        quest.setChoice2(txtxAreaQChoice2.getText());
        quest.setChoice3(txtxAreaQChoice3.getText());
        quest.setChoice4(txtxAreaQChoice4.getText());
        quest.setCorrectAnswer(choiceBoxCorrectAns.getValue());
        if(inputValidation())
            Questions.insert(quest);
        if(errorMessage.toString().isEmpty()){
            Navigator.getInstance().closeSecondStage();
            Navigator.getInstance().goToQBankIndex();
            selectedTopics = new ArrayList<>();
        }
        errorMessage = new StringBuilder();
    }

    @FXML
    void btnResetFieldClick(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(Topics t : topicsQuestionView){
            topicName.add(t.getTopicName());
        }
        topicName.add(0, "");
        ArrayList<String> topicNameUi = new ArrayList<>(topicName);
        topicNameUi.remove(0);
        choiceBoxSelectTopic.setItems(FXCollections.observableArrayList(topicNameUi));

        for(Classifications clazz: classQuestionView){
            className.add(clazz.getClassification());
        }
        className.add(0, "");
        ArrayList<String> classNameUi = new ArrayList<>(className);
        classNameUi.remove(0);
        choiceBoxSelectClass.setItems(FXCollections.observableArrayList(classNameUi));

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
    }

    private boolean inputValidation(){
        ObservableList<Topics> temp = tableViewSelectedTopic.getItems();
        if(temp.size() == 0) errorMessage.append("A question must be associated with a topic !\n");
        if(choiceBoxSelectClass.getValue() == null) errorMessage.append("A question must be associated with a classification !\n");
        if(txtAreaQStatement.getText() == null) errorMessage.append("A question must have a question statement !\n"); //TO DO: CHANGE "== NULL" TO .ISEMPTY()
        if(txtxAreaQChoice1.getText() == null
        || txtxAreaQChoice2.getText() == null|| txtxAreaQChoice3.getText().isEmpty()
        || txtxAreaQChoice4.getText() == null) errorMessage.append("A question must have 4 answers !\n");
        if(choiceBoxCorrectAns.getValue() == null) errorMessage.append("You must select 1 correct answer !\n");

        if(!errorMessage.toString().isEmpty()){
            AlertUtil.generateErrorWindow("Add new question failed", "Add new question",
                    errorMessage.toString());
            return false;
        }
        return true;
    }

    @FXML
    void btnAddTopic(ActionEvent event) throws IOException {
        if(!selectedTopics.contains(topicsQuestionView.get(topicName.indexOf(choiceBoxSelectTopic.getValue()) - 1))){
            selectedTopics.add(topicsQuestionView.get(
                    topicName.indexOf(choiceBoxSelectTopic.getValue()) - 1
            ));
            saveCurrentInputData();
            Navigator.getInstance().closeSecondStage();
            Navigator.getInstance().goToQBankAdd();
        }else{
            AlertUtil.generateErrorWindow("Add new topic failed", "Add new topic",
                    "Topic has been added before !, please choose other topics");
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
    }

    private void loadLastSavedData(){
        choiceBoxSelectClass.setValue(currentSelectedClassification);
        txtAreaQStatement.setText(currentQuestionStatement);
        txtxAreaQChoice1.setText(currentChoice1);
        txtxAreaQChoice2.setText(currentChoice2);
        txtxAreaQChoice3.setText(currentChoice3);
        txtxAreaQChoice4.setText(currentChoice4);
        choiceBoxCorrectAns.setValue(currentCorrectChoice);
    }
}
