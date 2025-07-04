package com.arthroverse.duckyemulator.UIControllers.PublicUIController;

import com.arthroverse.duckyemulator.Database.MainDB.CredentialBeans.Users;
import com.arthroverse.duckyemulator.Database.MainDB.PublicBeans.Sessions;
import com.arthroverse.duckyemulator.UIs.Navigator;
import com.arthroverse.duckyemulator.Utilities.Constant.Reusable;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserSessionHistoriesPageUIController implements Initializable{

    @FXML
    private Label greetingLabel;

    @FXML
    private TableColumn<Sessions, String> tableColPauseTime;

    @FXML
    private TableColumn<Sessions, String> tableColPercentage;

    @FXML
    private TableColumn<Sessions, String> tableColSessionName;

    @FXML
    private TableColumn<Sessions, String> tableColStartTime;

    @FXML
    private TableColumn<Sessions, String> tableColTimeTaken;

    @FXML
    private TableView<Sessions> tableViewSessionHis;

    @FXML
    private Pagination pagination;

    @FXML
    private MFXButton mfxButtonViewResult;

    @FXML
    private MFXButton mfxButtonDeleteSession;

    @FXML
    private MFXButton btnCredits;

    private static int offset;

    private static int maxPageNum;

    private static int currentPageIndex;

    public static int getOffset(){
        return offset;
    }

    public static int getMaxPageNum(){
        return maxPageNum;
    }

    public static void setOffset(int offset){
        UserSessionHistoriesPageUIController.offset = offset;
    }

    public static void setMaxPageNum(int maxPageNum){
        UserSessionHistoriesPageUIController.maxPageNum = maxPageNum;
    }

    @FXML
    void btnDeleteSelectedSession(ActionEvent event) throws IOException {
        Sessions s = tableViewSessionHis.getSelectionModel().getSelectedItem();
        Sessions.delete(s);
        Navigator.getInstance().goToUserSessionHistories();
    }

    @FXML
    void btnGoToHome(ActionEvent event) throws IOException {
        Navigator.getInstance().goToUserHomePage();
    }

    @FXML
    void btnGoToTestHistory(ActionEvent event) throws IOException {
        Navigator.getInstance().goToUserSessionHistories();
    }

    @FXML
    void btnLogout(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLoginPage();
    }

    @FXML
    void btnViewSelectedResult(ActionEvent event) throws IOException {
        Sessions session = tableViewSessionHis.getSelectionModel().getSelectedItem();
        Sessions.selectASession(session);
        Navigator.getInstance().goToResultPage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        greetingLabel.setText(String.format(Reusable.DEFAULT_GREETING.toString(),
                Users.getUserName()));
        Sessions.setPage();
        pagination.setPageCount(maxPageNum);
        pagination.setCurrentPageIndex(currentPageIndex);
        deploy(currentPageIndex);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldIndex, newIndex) -> {
                    int pageIndex = newIndex.intValue();
                    currentPageIndex = pageIndex;
                    deploy(pageIndex);
                });

        mfxButtonViewResult.disableProperty().set(true);
        mfxButtonDeleteSession.disableProperty().set(true);
        tableViewSessionHis.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(tableViewSessionHis.getSelectionModel().getSelectedItem() != null){
                        mfxButtonViewResult.disableProperty().set(false);
                        mfxButtonDeleteSession.disableProperty().set(false);
                    }else{
                        mfxButtonViewResult.disableProperty().set(true);
                        mfxButtonDeleteSession.disableProperty().set(true);
                    }
                }
        );

        tableViewSessionHis.setRowFactory(new Callback<TableView<Sessions>, TableRow<Sessions>>() {
            @Override
            public TableRow<Sessions> call(TableView<Sessions> tableView2) {
                final TableRow<Sessions> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    final int index = row.getIndex();
                    if (index >= 0 && index < tableViewSessionHis.getItems().size() && tableViewSessionHis.getSelectionModel().isSelected(index)  ) {
                        mfxButtonViewResult.disableProperty().set(true);
                        mfxButtonDeleteSession.disableProperty().set(true);
                        tableViewSessionHis.getSelectionModel().clearSelection(index);
                        event.consume();
                    }
                });
                return row;
            }
        });
    }

    private void deploy(int pageIndex){
        offset = pageIndex * 10;
        Sessions.selectSessions(offset);
        tableViewSessionHis.setItems(FXCollections.observableList(Sessions.selectSessions(offset)));

        tableColPercentage.setCellValueFactory((session) -> {
            return session.getValue().getPercentageProperty();
        });

        tableColPauseTime.setCellValueFactory((session) -> {
            return session.getValue().getEndTimeProperty();
        });

        tableColSessionName.setCellValueFactory((session) -> {
            return session.getValue().getSessionIdProperty();
        });

        tableColStartTime.setCellValueFactory((session) -> {
            return session.getValue().getStartTimeProperty();
        });

        tableColTimeTaken.setCellValueFactory((session) -> {
            return session.getValue().getTimeTakenProperty();
        });
    }

    @FXML
    public void btnCreditsClick(ActionEvent event) throws IOException{
        Navigator.getInstance().goToCredit();
    }
}