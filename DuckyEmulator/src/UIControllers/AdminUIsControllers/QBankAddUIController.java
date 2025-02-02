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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

    private ArrayList<String> topicName = new ArrayList<>();

    private ArrayList<String> className = new ArrayList<>();

    private StringBuilder errorMessage = new StringBuilder();

    @FXML
    void btnChooseImagePathClick(ActionEvent event) {

    }

    @FXML
    void btnAddNewQuestionClick(ActionEvent event) throws IOException {
        Questions quest = new Questions();
<<<<<<< Updated upstream
        quest.setForeignKeyTopicId(topicName.indexOf(
                choiceBoxSelectTopic.getValue()
        ));
=======
        ArrayList<Integer> selectedTopicIds = new ArrayList<>();
        for(Topics t: selectedTopics){
            selectedTopicIds.add(topicName.indexOf(t.getTopicName()));
        }
        ArrayList<String> selectedTopicNames = new ArrayList<>();
        for(Topics t: selectedTopics){
            selectedTopicNames.add(t.getTopicName());
        }
        String selectedTopicNamesDisplay = selectedTopicNames.toString();
        quest.setForeignKeyTopicIdForDisplay(selectedTopicNamesDisplay.substring(1, selectedTopicNamesDisplay.length() - 1));
        System.out.println(selectedTopicIds.toString());
        quest.setForeignKeyTopicId(selectedTopicIds);
>>>>>>> Stashed changes
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
        }
        errorMessage = new StringBuilder();
        selectedTopics = new ArrayList<>();
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
    }

    private boolean inputValidation(){
        ObservableList<Topics> temp = tableViewSelectedTopic.getItems();
        if(temp.size() == 0) errorMessage.append("A question must be associated with a topic !\n");
        if(choiceBoxSelectClass.getValue() == null) errorMessage.append("A question must be associated with a classification !\n");
        if(txtAreaQStatement.getText() == null) errorMessage.append("A question must have a question statement !\n"); //TO DO: CHANGE "== NULL" TO .ISEMPTY()
        if(txtxAreaQChoice1.getText().isEmpty()
        || txtxAreaQChoice2.getText().isEmpty()|| txtxAreaQChoice3.getText().isEmpty()
        || txtxAreaQChoice4.getText().isEmpty()) errorMessage.append("A question must have 4 answers !\n");
        if(choiceBoxCorrectAns.getValue()== null) errorMessage.append("You must selectAll 1 correct answer !\n");

        if(!errorMessage.toString().isEmpty()){
            AlertUtil.generateErrorWindow("Add new question failed", "Add new question",
                    errorMessage.toString());
            return false;
        }
        return true;
    }
}
