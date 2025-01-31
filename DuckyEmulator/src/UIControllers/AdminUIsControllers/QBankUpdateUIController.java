package UIControllers.AdminUIsControllers;

import Database.MainDB.Beans.Classifications;
import Database.MainDB.Beans.Questions;
import Database.MainDB.Beans.Topics;
import UIs.Navigator;
import Utilities.PromptAlert.AlertUtil;
import javafx.collections.FXCollections;
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

    private static ArrayList<String> topicName = new ArrayList<>();

    private static ArrayList<String> className = new ArrayList<>();

    private static StringBuilder errorMessage = new StringBuilder();

    private static Questions updateQuestion;

    @FXML
    void btnChooseImagePathClick(ActionEvent event) {

    }

    @FXML
    void btnUpdateCurrentQuestionClick(ActionEvent event) throws IOException {
        updateQuestion.setForeignKeyTopicId(topicName.indexOf(
                choiceBoxSelectTopic.getValue()
        ));
        updateQuestion.setForeignKeyClassificationId(
                className.indexOf(choiceBoxSelectClass.getValue())
        );
        updateQuestion.setQuestionStatement(txtAreaQStatement.getText());
        updateQuestion.setChoice1(txtxAreaQChoice1.getText());
        updateQuestion.setChoice2(txtxAreaQChoice2.getText());
        updateQuestion.setChoice3(txtxAreaQChoice3.getText());
        updateQuestion.setChoice4(txtxAreaQChoice4.getText());
        updateQuestion.setCorrectAnswer(choiceBoxCorrectAns.getValue());
        if(inputValidation())
            Questions.update(updateQuestion);
        if(errorMessage.toString().isEmpty()){
            Navigator.getInstance().closeSecondStage();
            Navigator.getInstance().goToQBankIndex();
        }
        errorMessage = new StringBuilder();
    }

    @FXML
    void btnResetFieldClick(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(Topics t: topicsQuestionView){
            topicName.add(t.getTopicName());
        }
        topicName.add(0, "");
        ArrayList<String> topicNameUi = new ArrayList<>(topicName);
        choiceBoxSelectTopic.setItems(FXCollections.observableArrayList(topicNameUi));

        for(Classifications c: classQuestionView){
            className.add(c.getClassification());
        }
        className.add(0, "");
        ArrayList<String> classNameUi = new ArrayList<>(className);
        choiceBoxSelectClass.setItems(FXCollections.observableArrayList(classNameUi));

        choiceBoxCorrectAns.setItems(FXCollections.observableArrayList(
                "Choice 1", "Choice 2", "Choice 3", "Choice 4"
        ));
    }

    public void initialize(Questions q){
        updateQuestion = q;
        choiceBoxSelectTopic.setValue(topicName.get(
                q.getForeignKeyTopicId()
        ));

        choiceBoxSelectClass.setValue(className.get(
                q.getForeignKeyClassificationId()
        ));
        txtAreaQStatement.setText(q.getQuestionStatement());
        txtxAreaQChoice1.setText(q.getChoice1());
        txtxAreaQChoice2.setText(q.getChoice2());
        txtxAreaQChoice3.setText(q.getChoice3());
        txtxAreaQChoice4.setText(q.getChoice4());
        choiceBoxCorrectAns.setValue(q.getCorrectAnswer());
    }

    private boolean inputValidation(){
        if(choiceBoxSelectTopic.getValue() == null) errorMessage.append("A question must be associated with a topic !\n");
        if(choiceBoxSelectClass.getValue() == null) errorMessage.append("A question must be associated with a classification !\n");
        if(txtAreaQStatement.getText() == null) errorMessage.append("A question must have a question statement !\n"); //TO DO: CHANGE "== NULL" TO .ISEMPTY()
        if(txtxAreaQChoice1.getText().isEmpty()
                || txtxAreaQChoice2.getText().isEmpty()|| txtxAreaQChoice3.getText().isEmpty()
                || txtxAreaQChoice4.getText().isEmpty()) errorMessage.append("A question must have 4 answers !\n");
        if(choiceBoxCorrectAns.getValue()== null) errorMessage.append("You must selectAll 1 correct answer !\n");

        if(!errorMessage.toString().isEmpty()){
            AlertUtil.generateErrorWindow("Update new question failed", "Update new question",
                    errorMessage.toString());
            return false;
        }
        return true;
    }

}
