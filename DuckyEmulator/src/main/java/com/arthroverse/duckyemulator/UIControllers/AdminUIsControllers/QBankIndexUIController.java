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
package com.arthroverse.duckyemulator.UIControllers.AdminUIsControllers;

import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Classifications;
import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Questions;
import com.arthroverse.duckyemulator.Database.MainDB.CredentialBeans.Users;
import com.arthroverse.duckyemulator.UIs.Navigator;
import com.arthroverse.duckyemulator.Utilities.Constant.ErrorMessage;
import com.arthroverse.duckyemulator.Utilities.Constant.ErrorTitle;
import com.arthroverse.duckyemulator.Utilities.Constant.Reusable;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.arthroverse.duckyemulator.Utilities.PromptAlert.AlertUtil;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

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
    private Button btnTableUpdate;

    @FXML
    private Pagination pageinationQBank;

    @FXML
    private TableColumn<Questions, String> tableImagePath;

    @FXML
    private Label greetingLabel;

    @FXML
    private Button btnLogout;

    @FXML
    private MFXButton btnCredits;

    private static int offset;

    private static int maxPageNum;

    private static int currentPageIndex;

    public static Questions originalQuestion;

    public static int getOffset(){
        return offset;
    }

    public static int getMaxPageNum(){
        return maxPageNum;
    }

    public static void setOffset(int offset){
        QBankIndexUIController.offset = offset;
    }

    public static void setMaxPageNum(int maxPageNum){
        QBankIndexUIController.maxPageNum = maxPageNum;
    }

    @FXML
    void btnTableAddClick(ActionEvent event) throws IOException {
        currentPageIndex = pageinationQBank.getCurrentPageIndex();
        Navigator.getInstance().goToQBankAdd();
    }

    @FXML
    void btnTableDeleteClick(ActionEvent event) throws IOException {
        Questions selectedQuest = tableBankView.getSelectionModel().getSelectedItem();
        if(selectedQuest == null){
            AlertUtil.generateErrorWindow("Delete question failed",
                    "Delete question", "You must select a question to delete");
        }else{
            if(AlertUtil.generateWarningWindow(
                    "Delete question confirmation",
                    "Are you sure you want to delete the selected question ?"
            )){
                Questions.delete(selectedQuest);
                if(tableBankView.getItems().size() == 1 & currentPageIndex > 0){
                    currentPageIndex = pageinationQBank.getCurrentPageIndex() - 1;
                }
                tableBankView.getItems().remove(selectedQuest);
                Navigator.getInstance().goToQBankIndex();
            }
        }
    }

    @FXML
    void btnTableUpdateClick(ActionEvent event) throws IOException {
        Questions selectedQuest = tableBankView.getSelectionModel().getSelectedItem();
        if(selectedQuest == null){
            AlertUtil.generateErrorWindow(ErrorTitle.QUEST_UI_CONTROLLER_DELETE_QUEST_FAILED.toString(),
                    "Question deletion",
                    ErrorMessage.QUEST_UI_CONTROLLER_DELETE_QUEST_FAILED.toString());
        }
        else{
            currentPageIndex = pageinationQBank.getCurrentPageIndex();
            Navigator.getInstance().goToQBankUpdate(selectedQuest);
            originalQuestion = selectedQuest;
        }
    }

    @FXML
    void btnMenuHome(ActionEvent event) throws IOException {
        Navigator.getInstance().goToAdminHomePage();
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
        greetingLabel.setText(String.format(Reusable.DEFAULT_GREETING.toString(),
                Users.getUserName()));
        Questions.setPage();
        pageinationQBank.setPageCount(maxPageNum);
        pageinationQBank.setCurrentPageIndex(currentPageIndex);
        deploy(currentPageIndex);
        pageinationQBank.currentPageIndexProperty().addListener(
                (observable, oldIndex, newIndex) -> {
                    int pageIndex = newIndex.intValue();
                    currentPageIndex = pageIndex;
                    deploy(pageIndex);
                });

        btnTableDelete.disableProperty().set(true);
        btnTableUpdate.disableProperty().set(true);
        tableBankView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSelected, newSelected) -> {
                    if(tableBankView.getSelectionModel().getSelectedItem() != null){
                        btnTableDelete.disableProperty().set(false);
                        btnTableUpdate.disableProperty().set(false);
                    }else{
                        btnTableDelete.disableProperty().set(true);
                        btnTableUpdate.disableProperty().set(true);
                    }
                }
        );

        tableBankView.setRowFactory(new Callback<TableView<Questions>, TableRow<Questions>>() {
            @Override
            public TableRow<Questions> call(TableView<Questions> tableView2) {
                final TableRow<Questions> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    final int index = row.getIndex();
                    if (index >= 0 && index < tableBankView.getItems().size() && tableBankView.getSelectionModel().isSelected(index)  ) {
                        btnTableDelete.disableProperty().set(true);
                        btnTableUpdate.disableProperty().set(true);
                        tableBankView.getSelectionModel().clearSelection(index);
                        event.consume();
                    }
                });
                return row;
            }
        });
    }

    @FXML
    void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLoginPage();
    }

    private void deploy(int pageIndex){
        offset = pageIndex * 10;
        tableBankView.setItems(Questions.select(offset));
        tableClassCol.setCellValueFactory((questions) -> {
            return questions.getValue().getQuestionClassificationProperty();
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

    @FXML
    public void btnCreditsClick(ActionEvent event) throws IOException{
        Navigator.getInstance().goToCredit();
    }
}