package UIControllers.AdminUIsControllers;

import Database.MainDB.Beans.Classifications;
import UIs.Navigator;
import Utilities.PromptAlert.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ClassificationsUpdateUIController {

    @FXML
    private Button btnUpdateClassification;

    @FXML
    private TextField txtFieldClassificationName;

    @FXML
    private TextArea txtAreaClassificationDescription;

    @FXML
    private Button btnResetField;

    private static StringBuilder errorMessage = new StringBuilder();

    private static Classifications updateClass;

    @FXML
    void btnUpdateClassificationClick(ActionEvent event) throws IOException {
        updateClass.setClassification(txtFieldClassificationName.getText());
        updateClass.setClassificationDescription((txtAreaClassificationDescription.getText()));
        if(inputValidation()) Classifications.update(updateClass);
        if(errorMessage.toString().isEmpty()){
            Navigator.getInstance().closeSecondStage();
            Navigator.getInstance().goToTopicClassIndex();
        }
        errorMessage = new StringBuilder();
    }

    @FXML
    void btnResetFieldClick(ActionEvent event) {

    }

    public void initialize(Classifications c){
        updateClass = c;
        txtFieldClassificationName.setText(c.getClassification());
        txtAreaClassificationDescription.setText(c.getClassificationDescription());
    }

    private boolean inputValidation(){
        if(txtFieldClassificationName.getText().isEmpty()) errorMessage.append("Classification shouldn't be leave empty !\n");
        if(!errorMessage.toString().isEmpty()){
            AlertUtil.generateErrorWindow("Add new classification failed", "Add new classification",
                    errorMessage.toString());
            return false;
        }
        return true;
    }


}
