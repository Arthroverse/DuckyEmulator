package UIs;

import Database.MainDB.Beans.Classifications;
import Database.MainDB.Beans.Questions;
import Database.MainDB.Beans.Topics;
import UIControllers.AdminUIsControllers.ClassificationsUpdateUIController;
import UIControllers.AdminUIsControllers.QBankAddUIController;
import UIControllers.AdminUIsControllers.QBankUpdateUIController;
import UIControllers.AdminUIsControllers.TopicsUpdateUIController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigator {
    /**
     * If we change the position of the {@code .fxml} files<p>
     * to another position, then we have to add the new package name<p>
     * where the fxml file reside !*/
    private static final String HOME_FXML = "AdminUIs/HomePageUI.fxml";

    private static final String QBANK_INDEX = "AdminUIs/QBankIndexUI.fxml";

    private static final String QBANK_ADD = "AdminUIs/QBankAddUI.fxml";

    private static final String QBANK_UPDATE = "AdminUIs/QBankUpdateUI.fxml";

    private static final String TOPICS_CLASS_INDEX = "AdminUIs/TopicsClassIndexUI.fxml";

    private static final String TOPICS_INDEX_ADD = "AdminUIs/TopicsAddUI.fxml";

    private static final String TOPICS_INDEX_UPDATE = "AdminUIs/TopicsUpdateUI.fxml";

    private static final String CLASS_INDEX_ADD = "AdminUIs/ClassificationsAddUI.fxml";

    private static final String CLASS_INDEX_UPDATE = "AdminUIs/ClassificationsUpdateUI.fxml";

    private Stage stage;

    private Stage secondStage = new Stage();

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

    public Stage getStage() {
        return this.stage;
    }

   public FXMLLoader getLoader(){
        return this.loader;
   }

   public void setStage(Stage stage){
        this.stage = stage;
   }
   private void goTo(String fxml) throws IOException {
        this.generateWindow(fxml);
   }

    /**
     * <pre>{@code
     * Rectangle2D screenBound = Screen.getPrimary().getVisualBounds();
     * this.stage.setX(screenBound.getMinX());
     * this.stage.setY(screenBound.getMinY());
     * this.stage.setWidth(screenBound.getWidth());
     * this.stage.setHeight(screenBound.getHeight());}</pre><p>
     * The first line is used to get the general shape of your screen (also gather your screen's resolution)<p>
     * The next 2 lines is to set the initial position of the stage at the minimum positon<p>
     * The last 2 lines is to set the stage size using the resolution extracted from the shape gathed in the first line<p>
     * <p>
     * <pre>
     * {@code
     * qBank.initModality(Modality.WINDOW_MODAL);
     * qBank.initOwner(this.stage);
     * if(qBank != null) qBank.requestFocus();}
     * </pre><p>
     * In order to generate 2 windows at once, we will need 2 separate stages<p>
     * The first step is just basic, create separate stage, scene for our UIs.<p>
     * However, in order for the second windows to work perfectly and also block any interactions<p>
     * with our main windows, we will neeed additional 3 lines:<p>
     * {@code qBank.initModality(Modality.WINDOW_MOAL);} these line indicate that we will block all<p>
     * interactions with the primary windows (which we will set which one in the next line)<p>
     * {@code qBank.initOwner(this.stage);} this line means that we will set the primary windows is the main windows<p>
     * we are using right now (in this case either TopicClassIndex or QuestionBank index)<p>
     * {@code if(qBank != null) qBank.requestFocus();} this line will determine whether our secondary window has shown in the screen or not.<p>
     * If not, we will ask it to bring it to the front of the screen*/

    private void generateWindow(String fxml) throws IOException {
       if(!fxml.equals(QBANK_ADD) & !fxml.equals(QBANK_UPDATE)
               & !fxml.equals(TOPICS_INDEX_ADD) & !fxml.equals(TOPICS_INDEX_UPDATE)
               & !fxml.equals(CLASS_INDEX_ADD) & !fxml.equals(CLASS_INDEX_UPDATE)){
           this.loader = new FXMLLoader();
           loader.setLocation((getClass().getResource(fxml)));
           Parent root = loader.load();
           Scene currentScene = new Scene(root);
           Rectangle2D screenBound = Screen.getPrimary().getVisualBounds();
           this.stage.setX(screenBound.getMinX());
           this.stage.setY(screenBound.getMinY());
           this.stage.setWidth(screenBound.getWidth());
           this.stage.setHeight(screenBound.getHeight());
           this.stage.setScene(currentScene);
       }else{
           this.secondStage = new Stage();
           this.loader = new FXMLLoader();
           if(fxml.equals(QBANK_ADD))
               this.loader.setLocation(getClass().getResource(QBANK_ADD));
           else if(fxml.equals(QBANK_UPDATE))
               this.loader.setLocation(getClass().getResource(QBANK_UPDATE));
           else if(fxml.equals(TOPICS_INDEX_ADD))
               this.loader.setLocation(getClass().getResource(TOPICS_INDEX_ADD));
           else if(fxml.equals(TOPICS_INDEX_UPDATE))
               this.loader.setLocation(getClass().getResource(TOPICS_INDEX_UPDATE));
           else if(fxml.equals(CLASS_INDEX_ADD))
               this.loader.setLocation(getClass().getResource(CLASS_INDEX_ADD));
           else
               this.loader.setLocation(getClass().getResource(CLASS_INDEX_UPDATE));
           Parent secondRoot = this.loader.load();
           Scene qBankAddScene = new Scene(secondRoot);
           secondStage.setScene(qBankAddScene);
           secondStage.initModality(Modality.WINDOW_MODAL);
           secondStage.initOwner(this.stage);
           if(secondStage != null) secondStage.requestFocus();
           secondStage.show();
           if(fxml.equals(QBANK_ADD)){
               secondStage.setOnCloseRequest(event -> {
                   QBankAddUIController.resetAllDatas();
               });
           }
       }
   }

   /**
    * After a successful add or update of Topic/Classification, we need to close the window.
    * To do that, we need to close the stage that contains the secondary scene and to do that,
    * we will use {@code secondStage.close()}*/
   public void closeSecondStage(){
        this.secondStage.close();
        this.secondStage = null;
   }

   public void goToHome() throws IOException {
        this.goTo(HOME_FXML);
   }

   public void goToQBankIndex() throws IOException {
        this.goTo(QBANK_INDEX);
   }

   public void goToQBankAdd() throws IOException {
        this.goTo(QBANK_ADD);
   }

   public void goToQBankUpdate(Questions q) throws IOException {
       this.goTo(QBANK_UPDATE);
       QBankUpdateUIController qbUpdateCtrl = this.loader.getController();
       qbUpdateCtrl.initialize(q);
   }

   public void goToTopicClassIndex() throws IOException {
        this.goTo(TOPICS_CLASS_INDEX);
   }

   public void goToTopicIndexAdd() throws IOException{
       this.goTo(TOPICS_INDEX_ADD);
   }

   public void goToTopicIndexUpdate(Topics t) throws IOException {
       this.goTo(TOPICS_INDEX_UPDATE);
       TopicsUpdateUIController tUpdateCtrl = this.loader.getController();
       tUpdateCtrl.initialize(t);
   }

   public void goToClassIndexAdd() throws IOException{
       this.goTo(CLASS_INDEX_ADD);
   }

    public void goToClassIndexUpdate(Classifications c) throws IOException{
        this.goTo(CLASS_INDEX_UPDATE);
        ClassificationsUpdateUIController cUpdateCtrl = this.loader.getController();
        cUpdateCtrl.initialize(c);
    }
}
