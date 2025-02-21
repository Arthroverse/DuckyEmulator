package UIControllers.LoginController;

import Database.MainDB.CredentialBeans.Users;
import UIs.Navigator;
import Utilities.Constant.ErrorMessage.ErrorMessage;
import Utilities.Constant.ErrorTitle.ErrorTitle;
import Utilities.Constant.FailedOperationType.FailedOperationType;
import Utilities.PromptAlert.AlertUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginUIController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField passwordFieldUsrPw;

    @FXML
    private ChoiceBox<String> choiceBoxUserType;

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField txtUsrNameEmail;

    private static StringBuilder errorMessage;

    @FXML
    void btnLoginClick(ActionEvent event) throws IOException {
        if(inputValidation()){
            Users u = new Users();
            u.setUserEmail(txtUsrNameEmail.getText());
            u.setUserPassword(passwordFieldUsrPw.getText());
            u.setUserType(choiceBoxUserType.getValue());
            u.setUserEmailUuid(Users.emailToUuid(txtUsrNameEmail.getText()));
            if(Users.checkCredential(u)) Navigator.getInstance().goToHome();
        }
        errorMessage = new StringBuilder();
    }

    @FXML
    void btnSignUpHere(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBoxUserType.setItems(FXCollections.observableArrayList("User", "Admin"));
        errorMessage = new StringBuilder();
    }

    private boolean inputValidation(){
        if(txtUsrNameEmail.getText() == null)
            errorMessage.append(ErrorMessage.LOGIN_UI_CONTROLLER_NO_USERNAME_INPUTTED);
        else
            if(!(Users.checkValidEmail(txtUsrNameEmail.getText())))
                errorMessage.append(ErrorMessage.LOGIN_UI_CONTROLLER_INVALID_EMAIL);
        if(passwordFieldUsrPw.getText() == null)
            errorMessage.append(ErrorMessage.LOGIN_UI_CONTROLLER_NO_PASSWORD_INPUTTED);
        if(choiceBoxUserType.getValue() == null)
            errorMessage.append(ErrorMessage.LOGIN_UI_CONTROLLER_NO_USERTYPE_CHOOSEN);
        if(errorMessage.toString().isEmpty()) {
            AlertUtil.generateErrorWindow(
                    ErrorTitle.LOGIN_UI_CONTROLLER_LOGIN_FAILED.toString(),
                    FailedOperationType.LOGIN_UI_CONTROLLER_LOGIN_FAILED.toString(),
                    errorMessage.toString());
            return false;
        }
        return true;
    }
}
