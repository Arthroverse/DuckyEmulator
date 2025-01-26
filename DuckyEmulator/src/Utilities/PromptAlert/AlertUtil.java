package Utilities.PromptAlert;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public abstract interface AlertUtil {
    public static boolean generateWarningWindow(String warningTitle, String warningMessage){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(warningTitle);
        alert.setHeaderText(warningMessage);
        Optional<ButtonType> confirmationResponse = alert.showAndWait();
        if(confirmationResponse.get() == ButtonType.OK) return true;
        else return false;
    }

    public static void generateErrorWindow(String errorTitle, String operationType, String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorTitle);
        alert.setHeaderText(operationType + " failed due to following reason:\n" + errorMessage);
        alert.showAndWait();
    }
}
