package UIs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigator {
    private static final String HOME_FXML = "HomePageUI.fxml";

    private static final String QBANK_NOT_SELECTED = "QBankIndexNotSelectedUI.fxml";

    private static final String QBANK_SELECTED = "QBankIndexSelectedUI.fxml";

    private static final String TOPICS_CLASS_NOT_SELECTED = "TopicsClassIndexNotSelectedUI.fxml";

    private static final String TOPICS_CLASS_SELECTED = "TopicsClassIndexSelectedUI.fxml";

    private static final String QBANK_ADD = "QBankIndexAddUI.fxml";

    private static final String TOPICS_CLASS_ADD = "TopicsClassIndexAddUI.fxml";

    private Stage stage;

    private static Navigator nav = null;

    private FXMLLoader loader;

    private Navigator(){}

    public static Navigator getInstance(){
        if(Navigator.nav == null){
            Navigator.nav = new Navigator();
            return Navigator.nav;
        }
        return Navigator.nav;
    }

    public Stage getStage(){
        return this.stage;
    }

   public FXMLLoader getLoader(){
        return this.loader;
   }

   public void setStage(Stage stage){
        this.stage = stage;
   }

   private void goTo(String fxml) throws IOException {
        this.loader = new FXMLLoader();
        loader.setLocation((getClass().getResource(fxml)));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
   }

   public void goToHome() throws IOException {
        this.goTo(Navigator.HOME_FXML);
   }

    public void goToQuestionBank() throws IOException {
        this.goTo(Navigator.QBANK_NOT_SELECTED);
    }

    public void goToTopicAndClassification() throws IOException {
        this.goTo(Navigator.TOPICS_CLASS_NOT_SELECTED);
    }

    public void goToAddQuestion() throws IOException{
        this.goTo(Navigator.QBANK_ADD);
    }

    public void goToAddTopicsAndClassification() throws IOException{
        this.goTo(Navigator.TOPICS_CLASS_ADD);
    }

}
