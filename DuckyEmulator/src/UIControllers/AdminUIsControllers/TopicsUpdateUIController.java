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

public class TopicsUpdateUIController{

    @FXML
    private Button btnUpdateTopic;

    @FXML
    private TextArea txtAreaTopicDescription;

    @FXML
    private Button btnResetField;

    @FXML
    private TextField txtFieldTopicName;

    private static StringBuilder errorMessage = new StringBuilder();

    private static Topics updateTopic;

    @FXML
    void btnUpdateTopicClick(ActionEvent event) throws IOException {
        updateTopic.setTopicName(txtFieldTopicName.getText());
        updateTopic.setTopicDescription(txtAreaTopicDescription.getText());
        if(inputValidation()) Topics.update(updateTopic);
        if(errorMessage.toString().isEmpty()){
            Navigator.getInstance().closeSecondStage();
            Navigator.getInstance().goToTopicClassIndex();
        }
        errorMessage = new StringBuilder();
    }

    @FXML
    void btnResetFieldClick(ActionEvent event) {

    }

    public void initialize(Topics t){
        updateTopic = t;
        txtFieldTopicName.setText(t.getTopicName());
        txtAreaTopicDescription.setText(t.getTopicDescription());
    }

    private boolean inputValidation(){
        if(txtFieldTopicName.getText().isEmpty()) errorMessage.append("Topic name shouldn't be leave empty !\n");
        if(!errorMessage.toString().isEmpty()){
            AlertUtil.generateErrorWindow("Update new topic failed", "Update new topic",
                    errorMessage.toString());
            return false;
        }
        return true;
    }
}
