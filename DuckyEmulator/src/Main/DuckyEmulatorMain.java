package Main;

import UIs.Navigator;
import javafx.application.Application;
import javafx.stage.Stage;

public class DuckyEmulatorMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("DuckyEmulator");
        Navigator.getInstance().setStage(primaryStage);
        Navigator.getInstance().goToHome();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}