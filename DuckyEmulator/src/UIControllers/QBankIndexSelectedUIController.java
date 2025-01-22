package UIControllers;

import UIs.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class QBankIndexSelectedUIController {

    @FXML
    private TextArea txtAreaQuestState;

    @FXML
    private Button btnScrollReset;

    @FXML
    private Button btnMenuBank;

    @FXML
    private Button btnScrollUpdate;

    @FXML
    private Button btnMenuHome;

    @FXML
    private TableView<?> tableBankView;

    @FXML
    private ChoiceBox<?> choiceBoxClass;

    @FXML
    private TableColumn<?, ?> tableBankClassCol;

    @FXML
    private TableColumn<?, ?> tableBankQuestStateCol;

    @FXML
    private TextArea txtAreChoice2;

    @FXML
    private TableColumn<?, ?> tableBankChoice2Col;

    @FXML
    private TableColumn<?, ?> tableBankChoice3Col;

    @FXML
    private ChoiceBox<?> choiceBoxTopic;

    @FXML
    private TextArea txtAreaChoice4;

    @FXML
    private TableColumn<?, ?> tableBankChoice1Col;

    @FXML
    private TextArea txtAreaChoice1;

    @FXML
    private TableColumn<?, ?> tableBankCorrectAnsCol;

    @FXML
    private ChoiceBox<?> choiceBoxCorrectAns;

    @FXML
    private Button btnTableDelete;

    @FXML
    private Button btnMenuTopicClass;

    @FXML
    private TextArea txtxAreaChoice3;

    @FXML
    private Button btnTableAdd;

    @FXML
    private TableColumn<?, ?> tableBankTopicCol;

    @FXML
    private TableColumn<?, ?> tableBankChoice4Col;

    @FXML
    void btnTableAddClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToAddQuestion();
    }

    @FXML
    void btnTableDeleteClick(ActionEvent event) {

    }

    @FXML
    void btnMenuHomeClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHome();
    }

    @FXML
    void btnMenuBankClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToQuestionBank();
    }

    @FXML
    void btnMenuTopicClassClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToTopicAndClassification();
    }

    @FXML
    void btnScrollUpdateClick(ActionEvent event) {

    }

    @FXML
    void btnScrollResetClick(ActionEvent event) {

    }

}
