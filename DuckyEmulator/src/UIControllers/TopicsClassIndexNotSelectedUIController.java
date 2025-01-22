package UIControllers;

import UIs.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class TopicsClassIndexNotSelectedUIController {

    @FXML
    private Button btnTopicDelete;

    @FXML
    private Button btnMenuTopicClass;

    @FXML
    private Button btnTopicAdd;

    @FXML
    private Button btnClassDelete;

    @FXML
    private Button btnMenuHome;

    @FXML
    private Button btnMenuQuestBank;

    @FXML
    private Button btnClassAdd;

    @FXML
    void btnTopicAddClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToAddTopicsAndClassification();
    }

    @FXML
    void btnTopicDeleteClick(ActionEvent event) {

    }

    @FXML
    void btnClassAddClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToAddTopicsAndClassification();
    }

    @FXML
    void btnClassDeleteClick(ActionEvent event) {

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
