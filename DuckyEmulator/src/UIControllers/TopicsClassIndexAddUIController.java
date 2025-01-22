package UIControllers;

import UIs.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class TopicsClassIndexAddUIController {

    @FXML
    private TableColumn<?, ?> tableTopicNameCol;

    @FXML
    private Button btnScrollTopicUpdate;

    @FXML
    private Button btnMenuHome;

    @FXML
    private Button btnMenuQuestBank;

    @FXML
    private Button btnTopicViewDelete;

    @FXML
    private Button tableClassViewAdd;

    @FXML
    private TextField txtFieldTopicName;

    @FXML
    private TableColumn<?, ?> tableClassificationCol;

    @FXML
    private TextField txtFieldClassification;

    @FXML
    private Button btnScrollClassUpdate;

    @FXML
    private TableView<?> tableTopicView;

    @FXML
    private TableView<?> tableClassView;

    @FXML
    private Button btnMenuTopicClass;

    @FXML
    private Button btnScrollTopicReset;

    @FXML
    private Button btnTopicViewAdd;

    @FXML
    private Button btnScrollClassReset;

    @FXML
    private Button tableClassViewDelete;

    @FXML
    void btnScrollTopicUpdate(ActionEvent event) {

    }

    @FXML
    void btnScrollTopicResetClick(ActionEvent event) {

    }

    @FXML
    void btnTopicViewAddClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToAddTopicsAndClassification();
    }

    @FXML
    void btnTopicViewDeleteClick(ActionEvent event) {

    }

    @FXML
    void btnScrollClassUpdateClick(ActionEvent event) {

    }

    @FXML
    void btnScrollClassResetClick(ActionEvent event) {

    }

    @FXML
    void tableClassViewAddClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToAddTopicsAndClassification();
    }

    @FXML
    void tableClassViewDeleteClick(ActionEvent event) {

    }

    @FXML
    void btnMenuHomeClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHome();
    }

    @FXML
    void btnMenuQuestBankClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToQuestionBank();
    }

    @FXML
    void btnMenuTopicClassClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToTopicAndClassification();
    }

}
