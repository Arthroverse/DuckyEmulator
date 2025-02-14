/*
 * Copyright (c) 2025 Arthroverse Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Organization: Arthroverse Laboratory
 * Author: Vinh Dinh Mai
 * Contact: business@arthroverse.com
 *
 *
 * @author ducksabervn
 */
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

    private static StringBuilder errorMessage = new StringBuilder();

    @FXML
    void btnAddNewClassificationClick(ActionEvent event) throws IOException {
        Classifications clazz = new Classifications();
        clazz.setClassification(txtFieldClassificationName.getText());
        clazz.setClassificationDescription((txtAreaClassificationDescription.getText()));
        if(inputValidation()) Classifications.insert(clazz);
        if(errorMessage.toString().isEmpty()){
            Navigator.getInstance().closeSecondStage();
            Navigator.getInstance().goToTopicClassIndex();
        }
        errorMessage = new StringBuilder();
    }

    @FXML
    void btnResetFieldClick(ActionEvent event) {
        boolean isOk = AlertUtil.generateWarningWindow("Reset all fields",
                "Are you sure you want to reset all fields ?");
        if(isOk){
            txtFieldClassificationName.setText(null);
            txtAreaClassificationDescription.setText(null);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
