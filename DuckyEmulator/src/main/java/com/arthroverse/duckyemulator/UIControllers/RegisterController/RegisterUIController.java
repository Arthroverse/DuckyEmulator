package com.arthroverse.duckyemulator.UIControllers.RegisterController;

import com.arthroverse.duckyemulator.Database.MainDB.CredentialBeans.Users;
import com.arthroverse.duckyemulator.UIs.Navigator;
import com.arthroverse.duckyemulator.Utilities.Constant.*;
import com.arthroverse.duckyemulator.Utilities.PromptAlert.AlertUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterUIController implements Initializable {
    @FXML
    private MFXButton btnRegister;

    @FXML
    private MFXPasswordField passwordFieldUsrPw;

    @FXML
    private MFXComboBox<String> choiceBoxUserType;

    @FXML
    private MFXTextField txtUserName;

    @FXML
    private MFXTextField txtUserEmail;

    @FXML
    private Pane paneBackground;

    @FXML
    private Pane paneColorLayer;

    @FXML
    private Label welcomeLabel;

    private static StringBuilder errorMessage;

    @FXML
    void btnRegisterClick(ActionEvent event) throws IOException {
        if(inputValidation()){
            Users u = new Users();
            if(choiceBoxUserType.getText().equals("Admin")){
                u.setAdminPrivileged(true);
            }else if(choiceBoxUserType.getText().equals("User")){
                u.setAdminPrivileged(false);
            }
            u.setUserEmail(txtUserEmail.getText());
            u.setUserObjectName(txtUserName.getText());
            u.setUserPassword(passwordFieldUsrPw.getText());
            if(Users.insert(u)){
                AlertUtil.generateInformationDialog(InformationMessage.CREATE_ACCOUNT_SUCCESSFUL.toString());
                btnReturnToLogin();
            }
        }
        errorMessage = new StringBuilder();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String welcomeText = Reusable.WELCOME_LABEL_TEXT.getAllWelcomeTexts().get((int)(Math.random() *
                Reusable.WELCOME_LABEL_TEXT.getAllWelcomeTexts().size()));
        welcomeLabel.setText(welcomeText);
        AnimationTimer animation = createSplashAnimation(welcomeLabel);
        animation.start();
        choiceBoxUserType.setItems(FXCollections.observableArrayList("User", "Admin"));
        errorMessage = new StringBuilder();
        final int[] colorFileCode = {1,2,3,4,5,6,40,46,49,53,5,4,55,56,58,59};
        paneBackground.setStyle(
                String.format("-fx-background-image: url('/com/arthroverse/duckyemulator/images/loginbg/game_bg_%d_001-uhd.png');", colorFileCode[(int)(Math.random() * colorFileCode.length)])
        );
        paneColorLayer.setStyle("-fx-background-color: linear-gradient(to bottom, #332D7B 0%, #3D4B9F 25%, #4984C4 100%);" + "-fx-opacity: 70%");
    }

    private boolean inputValidation(){
        if(txtUserEmail.getText() == null || txtUserName.getText() == null)
            errorMessage.append(ErrorMessage.LOGIN_UI_CONTROLLER_NO_USERNAME_INPUTTED);
        else
            if(!(Users.checkValidEmail(txtUserEmail.getText())))
                errorMessage.append(ErrorMessage.LOGIN_UI_CONTROLLER_INVALID_EMAIL);
        if(passwordFieldUsrPw.getText() == null)
            errorMessage.append(ErrorMessage.LOGIN_UI_CONTROLLER_NO_PASSWORD_INPUTTED);
        if(choiceBoxUserType.getValue() == null)
            errorMessage.append(ErrorMessage.LOGIN_UI_CONTROLLER_NO_USERTYPE_CHOOSEN);
        if(!errorMessage.toString().isEmpty()) {
            AlertUtil.generateErrorWindow(
                    ErrorTitle.LOGIN_UI_CONTROLLER_LOGIN_FAILED.toString(),
                    FailedOperationType.LOGIN_UI_CONTROLLER_LOGIN_FAILED.toString(),
                    errorMessage.toString());
            return false;
        }
        return true;
    }

    @FXML
    public void btnReturnToLogin() throws IOException {
        Navigator.getInstance().closeSecondStage();
        Navigator.getInstance().goToLoginPage();
    }

    public AnimationTimer createSplashAnimation(Node textNode) {
        return new AnimationTimer() {
            private long startTime = -1;

            @Override
            public void handle(long now) {
                if (startTime == -1) startTime = now;

                double elapsed = (now - startTime) / 1_000_000_000.0;
                double scale = 1.0 + 0.08 * Math.sin(elapsed * 5);

                textNode.setScaleX(scale);
                textNode.setScaleY(scale);
            }
        };
    }
}
