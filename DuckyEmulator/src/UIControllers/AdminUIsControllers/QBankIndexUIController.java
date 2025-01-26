package UIControllers.AdminUIsControllers;

import Database.MainDB.Beans.Classifications;
import Database.MainDB.Beans.Questions;
import Database.MainDB.Beans.Topics;
import UIs.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Database.MainDB.Beans.Classifications.classQuestionView;
import static Database.MainDB.Beans.Topics.topicsQuestionView;
import Utilities.PromptAlert.AlertUtil;

public class QBankIndexUIController implements Initializable {

    @FXML
    private TableColumn<Questions, String> tableClassCol;

    @FXML
    private TableColumn<Questions, String> tableChoice4Col;

    @FXML
    private TableColumn<Questions, String> tableChoice3Col;

    @FXML
    private TableColumn<Questions, String> tableCorrectAnsCol;

    @FXML
    private Button btnMenuQuestion;

    @FXML
    private TableColumn<Questions, String> tableChoice1Col;

    @FXML
    private Button btnMenuHome;

    @FXML
    private TableColumn<Questions, String> tableChoice2Col;

    @FXML
    private TableView<Questions> tableBankView;

    @FXML
    private TableColumn<Questions, String> tableQuestStateCol;

    @FXML
    private Button btnTableDelete;

    @FXML
    private Button btnMenuTopicClass;

    @FXML
    private Button btnTableAdd;

    @FXML
    private TableColumn<Questions, String> tableTopicCol;

    @FXML
    private Button btnTableUpdate;

    @FXML
    private Pagination pageinationQBank;

    @FXML
    private TableColumn<Questions, String> tableImagePath;

    @FXML
    void btnTableAddClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToQBankAdd();
    }

    @FXML
    void btnTableDeleteClick(ActionEvent event) {
        Questions selectedQuest = tableBankView.getSelectionModel().getSelectedItem();
        if(selectedQuest == null){
            AlertUtil.generateErrorWindow("Delete question failed", "Question deletion",
                    "A question must be selected to perform this operation !");
        }
        else{
            if(AlertUtil.generateWarningWindow(
                    "Delete question confirmation",
                    "Are you sure you want to delete the selected question ?"
            )){
                if(Questions.delete(selectedQuest)) tableBankView.getItems().remove(selectedQuest);
            }
        }
    }

    @FXML
    void btnTableUpdateClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToQBankUpdate();
    }

    @FXML
    void btnMenuHome(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHome();
    }

    @FXML
    void btnMenuQuestionClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToQBankIndex();
    }

    @FXML
    void btnMenuTopicClassClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToTopicClassIndex();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableBankView.setItems(Questions.selectAll());
        Classifications.selectAll();
        Topics.selectAll();
        tableClassCol.setCellValueFactory((questions) -> {
            return classQuestionView.get(
                    questions.getValue().getForeignKeyClassificationId() - 1
            ).getClassificationProperty();
        });

        tableTopicCol.setCellValueFactory((questions) -> {
            return topicsQuestionView.get(
                    questions.getValue().getForeignKeyTopicId() - 1
            ).getTopicNameProperty();
        });


        tableQuestStateCol.setCellValueFactory((questions) -> {
            return questions.getValue().getQuestionStatementProperty();
        });

        tableChoice1Col.setCellValueFactory((questions) -> {
            return questions.getValue().getChoice1Property();
        });

        tableChoice2Col.setCellValueFactory((questions) -> {
            return questions.getValue().getChoice2Property();
        });

        tableChoice3Col.setCellValueFactory((questions) -> {
            return questions.getValue().getChoice3Property();
        });

        tableChoice4Col.setCellValueFactory((questions) -> {
            return questions.getValue().getChoice4Property();
        });

        tableCorrectAnsCol.setCellValueFactory((questions) -> {
            return questions.getValue().getCorrectAnswerProperty();
        });

        tableImagePath.setCellValueFactory((questions) -> {
            return questions.getValue().getImagePathProperty();
        });
    }
}
