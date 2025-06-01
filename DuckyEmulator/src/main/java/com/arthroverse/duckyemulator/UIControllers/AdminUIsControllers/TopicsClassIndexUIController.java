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
import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Topics;
import com.arthroverse.duckyemulator.Database.MainDB.CredentialBeans.Users;
import com.arthroverse.duckyemulator.UIs.Navigator;
import com.arthroverse.duckyemulator.Utilities.Constant.Reusable;
import com.arthroverse.duckyemulator.Utilities.PromptAlert.AlertUtil;
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
    private Pagination tableViewClassPagination;

    @FXML
    private Pagination tableViewTopicPageination;

    @FXML
    private TableColumn<Classifications, String> tableClassificationDesc;

    @FXML
    private Button btnLogout;

    @FXML
    private Label greetingLabel;

    private static int topicsMaxPageNum;

    private static int classessMaxPageNum;

    private static int currentTopicPageIndex;

    private static int currentClassPageIndex;

    private static int topicsOffset;

    private static int classesOffset;

    public static int getTopicsOffset(){
        return topicsOffset;
    }

    public static int getTopicsMaxPageNum(){
        return topicsMaxPageNum;
    }

    public static void setTopicsOffset(int topicsOffset){
        TopicsClassIndexUIController.topicsOffset = topicsOffset;
    }

    public static void setTopicsMaxPageNum(int topicsMaxPageNum){
        TopicsClassIndexUIController.topicsMaxPageNum = topicsMaxPageNum;
    }

    public static int getClassesOffset(){
        return topicsOffset;
    }

    public static void setClassessOffset(int classesOffset){
        TopicsClassIndexUIController.classesOffset = classesOffset;
    }

    public static void setClassessMaxPageNum(int classessMaxPageNum){
        TopicsClassIndexUIController.classessMaxPageNum = classessMaxPageNum;
    }

    @FXML
    void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLoginPage();
    }

    @FXML
    void btnTopicViewAddClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToTopicIndexAdd();
    }

    @FXML
    void btnTopicViewUpdateClick(ActionEvent event) throws IOException {
        Topics selectedTop = tableTopicView.getSelectionModel().getSelectedItem();
        currentTopicPageIndex = tableViewTopicPageination.getCurrentPageIndex();
        Navigator.getInstance().goToTopicIndexUpdate(selectedTop);
    }

    @FXML
    void btnTopicViewDeleteClick(ActionEvent event) throws IOException {
        Topics selectedTop = tableTopicView.getSelectionModel().getSelectedItem();
        if(AlertUtil.generateWarningWindow(
                "Delete topic confirmation",
                "Are you sure you want to delete the selected topic ?"
        )){
            Topics.delete(selectedTop);
            if(tableTopicView.getItems().size() == 1 & tableViewTopicPageination.getCurrentPageIndex() > 0){
                currentTopicPageIndex = tableViewTopicPageination.getCurrentPageIndex() - 1;
            }
            tableTopicView.getItems().remove(selectedTop);
            Navigator.getInstance().goToTopicClassIndex();
        }
    }

    @FXML
    void tableClassViewAddClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToClassIndexAdd();
    }

    @FXML
    void tableClassViewUpdateClick(ActionEvent event) throws IOException {
        Classifications selectedClass = tableClassView.getSelectionModel().getSelectedItem();
        currentClassPageIndex = tableViewClassPagination.getCurrentPageIndex();
        Navigator.getInstance().goToClassIndexUpdate(selectedClass);
    }

    @FXML
    void tableClassViewDeleteClick(ActionEvent event) throws IOException {
        Classifications selectedClass = tableClassView.getSelectionModel().getSelectedItem();
        if(AlertUtil.generateWarningWindow(
                "Delete topic confirmation",
                "Are you sure you want to delete the selected topic ?"
        )){
            Classifications.delete(selectedClass);
            if(tableClassView.getItems().size() == 1 & tableViewClassPagination.getCurrentPageIndex() > 0){
                currentClassPageIndex = tableViewClassPagination.getCurrentPageIndex() - 1;
            }
            tableClassView.getItems().remove(selectedClass);
            Navigator.getInstance().goToTopicClassIndex();
        }
    }

    @FXML
    void btnMenuHomeClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToAdminHomePage();
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
        greetingLabel.setText(String.format(Reusable.DEFAULT_GREETING.toString(),
                Users.getUserName()));
        Topics.setPage();
        Classifications.setPage();
        tableViewTopicPageination.setPageCount(topicsMaxPageNum);
        tableViewClassPagination.setPageCount(classessMaxPageNum);
        tableViewTopicPageination.setCurrentPageIndex(currentTopicPageIndex);
        tableViewClassPagination.setCurrentPageIndex(currentClassPageIndex);
        this.topicInitialDeployment(currentTopicPageIndex);
        this.classInitialDeployment(currentClassPageIndex);
        btnTopicViewDelete.disableProperty().set(true);
        btnTopicViewUpdate.disableProperty().set(true);
        tableClassViewDelete.disableProperty().set(true);
        tableClassViewUpdate.disableProperty().set(true);
        tableViewTopicPageination.currentPageIndexProperty().addListener(
                (observable, oldIndex, newIndex) -> {
                    int pageIndex = newIndex.intValue();
                    currentTopicPageIndex = pageIndex;
                    this.topicViewInitialize(pageIndex);
                }
        );
        tableViewClassPagination.currentPageIndexProperty().addListener(
                (observable, oldIndex, newIndex) -> {
                    int pageIndex = newIndex.intValue();
                    currentClassPageIndex = pageIndex;
                    this.classViewInitalize(pageIndex);
                }
        );

        tableTopicView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSelected, newSelected) -> {
                    if(newSelected != null){
                        btnTopicViewDelete.disableProperty().set(false);
                        btnTopicViewUpdate.disableProperty().set(false);

                    }
                }
        );

        tableClassView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSelected, newSelected) -> {
                    if(newSelected != null){
                        tableClassViewDelete.disableProperty().set(false);
                        tableClassViewUpdate.disableProperty().set(false);
                    }
                }
        );
    }

    private void topicViewInitialize(int pageIndex){
        topicsOffset = pageIndex * 10;
        tableTopicView.setItems(Topics.select(topicsOffset));

        tableTopicNameCol.setCellValueFactory((topic) -> {
            return topic.getValue().getTopicNameProperty();
        });

        tableTopicDesc.setCellValueFactory((topic) -> {
            return topic.getValue().getTopicDescriptionProperty();
        });
    }

    private void classViewInitalize(int pageIndex){
        classesOffset = pageIndex * 10;
        tableClassView.setItems(Classifications.select(classesOffset));

        tableClassificationCol.setCellValueFactory((classification) -> {
            return classification.getValue().getClassificationProperty();
        });

        tableClassificationDesc.setCellValueFactory((classification) -> {
            return classification.getValue().getClassificationDescriptionProperty();
        });
    }

    private void topicInitialDeployment(int currentPageIndex){
        this.topicViewInitialize(currentPageIndex);
    }

    private void classInitialDeployment(int currentPageIndex){
        this.classViewInitalize(currentPageIndex);
    }
}