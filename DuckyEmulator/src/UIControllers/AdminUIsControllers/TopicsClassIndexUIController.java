package UIControllers.AdminUIsControllers;

import Database.MainDB.Beans.Classifications;
import Database.MainDB.Beans.Topics;
import UIs.Navigator;
import Utilities.PromptAlert.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TopicsClassIndexUIController implements Initializable{

    @FXML
    private TableColumn<Topics, String> tableTopicNameCol;

    @FXML
    private TableColumn<Topics, String> tableTopicDesc;

    @FXML
    private Button btnTopicViewUpdate;

    @FXML
    private Button tableClassViewUpdate;

    @FXML
    private Button btnMenuHome;

    @FXML
    private Button btnMenuQuestBank;

    @FXML
    private Button btnTopicViewDelete;

    @FXML
    private Button tableClassViewAdd;

    @FXML
    private TableColumn<Classifications, String> tableClassificationCol;

    @FXML
    private TableView<Topics> tableTopicView;

    @FXML
    private TableView<Classifications> tableClassView;

    @FXML
    private Button btnMenuTopicClass;

    @FXML
    private Button btnTopicViewAdd;

    @FXML
    private Button tableClassViewDelete;

    @FXML
    private Pagination tableVewClassPagination;

    @FXML
    private Pagination tableViewTopicPageination;

    @FXML
    private TableColumn<Classifications, String> tableClassificationDesc;


    @FXML
    void btnTopicViewAddClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToTopicIndexAdd();
    }

    @FXML
    void btnTopicViewUpdateClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToTopicIndexUpdate();
    }

    @FXML
    void btnTopicViewDeleteClick(ActionEvent event) {
        Topics selectedTop = tableTopicView.getSelectionModel().getSelectedItem();
        if(selectedTop == null){
            AlertUtil.generateErrorWindow("Delete topic failed", "Delete",
                    "A topic must be selected to perform this operation !");
        }
        else{
            if(AlertUtil.generateWarningWindow(
                    "Delete topic confirmation",
                    "Are you sure you want to delete the selected topic ?"
            )){
                if(Topics.delete(selectedTop)) tableTopicView.getItems().remove(selectedTop);
            }
        }
    }

    @FXML
    void tableClassViewAddClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToClassIndexAdd();
    }

    @FXML
    void tableClassViewUpdateClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToClassIndexUpdate();
    }

    @FXML
    void tableClassViewDeleteClick(ActionEvent event) {
        Classifications selectedClass = tableClassView.getSelectionModel().getSelectedItem();
        if(selectedClass == null){
            AlertUtil.generateErrorWindow("Delete topic failed", "Delete",
                    "A topic must be selected to perform this operation !");
        }
        else{
            if(AlertUtil.generateWarningWindow(
                    "Delete topic confirmation",
                    "Are you sure you want to delete the selected topic ?"
            )){
                if(Classifications.delete(selectedClass)) tableClassView.getItems().remove(selectedClass);
            }
        }
    }

    @FXML
    void btnMenuHomeClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHome();
    }

    @FXML
    void btnMenuQuestBankClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToQBankIndex();
    }

    @FXML
    void btnMenuTopicClassClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToTopicClassIndex();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.topicViewInitialize();
        this.classViewInitalize();
    }

    private void topicViewInitialize(){
        tableTopicView.setItems(Topics.selectAll());

        tableTopicNameCol.setCellValueFactory((topic) -> {
            return topic.getValue().getTopicNameProperty();
        });

        tableTopicDesc.setCellValueFactory((topic) -> {
            return topic.getValue().getTopicDescriptionProperty();
        });
    }

    private void classViewInitalize(){
        tableClassView.setItems(Classifications.selectAll());

        tableClassificationCol.setCellValueFactory((classification) -> {
            return classification.getValue().getClassificationProperty();
        });

        tableClassificationDesc.setCellValueFactory((classification) -> {
            return classification.getValue().getClassificationDescriptionProperty();
        });
    }
}
