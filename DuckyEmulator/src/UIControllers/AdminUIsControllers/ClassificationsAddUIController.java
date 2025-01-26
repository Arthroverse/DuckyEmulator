package UIControllers.AdminUIsControllers;

import Database.MainDB.Beans.Classifications;
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

public class ClassificationsAddUIController implements Initializable {

    @FXML
    private TextField txtFieldClassificationName;

    @FXML
    private TextArea txtAreaClassificationDescription;

    @FXML
    private Button btnResetField;

    @FXML
    private Button btnAddNewClassification;

    @FXML
    void btnAddNewClassificationClick(ActionEvent event) throws IOException {
        Classifications clazz = new Classifications();
        clazz.setClassification(txtFieldClassificationName.getText());
        clazz.setClassificationDescription((txtAreaClassificationDescription.getText()));
        if(inputValidation()) Classifications.insert(clazz);
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
        if(txtFieldClassificationName.getText().isEmpty()) errorMessage.append("Classification shouldn't be leave empty !\n");
        if(!errorMessage.toString().isEmpty()){
            AlertUtil.generateErrorWindow("Add new classification failed", "Add new classification",
                    errorMessage.toString());
            return false;
        }
        return true;
    }
}
