package UIControllers;

import UIs.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class QBankIndexNotSelectedUIController {

    @FXML
    private TableColumn<?, ?> tableClassCol;

    @FXML
    private TableColumn<?, ?> tableChoice4Col;

    @FXML
    private TableColumn<?, ?> tableChoice3Col;

    @FXML
    private TableColumn<?, ?> tableCorrectAnsCol;

    @FXML
    private Button btnMenuQuestion;

    @FXML
    private TableColumn<?, ?> tableChoice1Col;

    @FXML
    private Button btnMenuHome;

    @FXML
    private TableColumn<?, ?> tableChoice2Col;

    @FXML
    private TableView<?> tableBankView;

    @FXML
    private TableColumn<?, ?> tableQuestStateCol;

    @FXML
    private Button btnTableDelete;

    @FXML
    private Button btnMenuTopicClass;

    @FXML
    private Button btnTableAdd;

    @FXML
    private TableColumn<?, ?> tableTopicCol;

    @FXML
    void btnTableAddClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToAddQuestion();
    }

    @FXML
    void btnTableDeleteClick(ActionEvent event) {

    }

    @FXML
    void btnMenuHome(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHome();
    }

    @FXML
    void btnMenuQuestionClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToQuestionBank();
    }

    @FXML
    void btnMenuTopicClassClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToTopicAndClassification();
    }

}
