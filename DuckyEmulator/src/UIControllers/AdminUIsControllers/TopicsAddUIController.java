package UIControllers.AdminUIsControllers;

import Database.MainDB.Beans.Topics;
import UIs.Navigator;
import Utilities.PromptAlert.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TopicsAddUIController implements Initializable {

    @FXML
    private TextArea txtAreaTopicDescription;

    @FXML
    private Button btnAddNewTopic;

    @FXML
    private Button btnResetField;

    @FXML
    private TextField txtFieldTopicName;

    @FXML
    void btnAddNewTopicClick(ActionEvent event) throws IOException {
        Topics t = new Topics();
        t.setTopicName(txtFieldTopicName.getText());
        t.setTopicDescription(txtAreaTopicDescription.getText());
        if(inputValidation()) Topics.insert(t);
        Navigator.getInstance().closeSecondStage();
        Navigator.getInstance().goToTopicClassIndex();
    }

    @FXML
    void btnResetFieldClick(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private boolean inputValidation(){
        StringBuilder errorMessage = new StringBuilder();
        if(txtFieldTopicName.getText().isEmpty()) errorMessage.append("Topic name shouldn't be leave empty !\n");
        if(!errorMessage.toString().isEmpty()){
            AlertUtil.generateErrorWindow("Add new topic failed", "Add new topic",
                    errorMessage.toString());
            return false;
        }
        return true;
    }
}
