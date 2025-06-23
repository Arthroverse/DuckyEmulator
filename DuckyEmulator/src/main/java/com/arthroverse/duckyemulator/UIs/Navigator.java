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
package com.arthroverse.duckyemulator.UIs;

import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Classifications;
import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Questions;
import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Topics;
import com.arthroverse.duckyemulator.Database.MainDB.PublicBeans.SessionResult;
import com.arthroverse.duckyemulator.Main.DuckyEmulatorMain;
import com.arthroverse.duckyemulator.UIControllers.AdminUIsControllers.ClassificationsUpdateUIController;
import com.arthroverse.duckyemulator.UIControllers.AdminUIsControllers.QBankAddUIController;
import com.arthroverse.duckyemulator.UIControllers.AdminUIsControllers.QBankUpdateUIController;
import com.arthroverse.duckyemulator.UIControllers.AdminUIsControllers.TopicsUpdateUIController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigator {
    /**
     * If we change the position of the {@code .fxml} files<p>
     * to another position, then we have to add the new package name<p>
     * where the fxml file reside !*/
    private static final String HOME_FXML = "/com/arthroverse/duckyemulator/fxmls/AdminUIs/NewHomePageUI.fxml";

    private static final String QBANK_INDEX = "/com/arthroverse/duckyemulator/fxmls/AdminUIs/NewQBankIndexUI.fxml";

    private static final String QBANK_ADD = "/com/arthroverse/duckyemulator/fxmls/AdminUIs/NewQBankAddUI.fxml";

    private static final String QBANK_UPDATE = "/com/arthroverse/duckyemulator/fxmls/AdminUIs/NewQBankUpdateUI.fxml";

    private static final String TOPICS_CLASS_INDEX = "/com/arthroverse/duckyemulator/fxmls/AdminUIs/NewTopicsClassIndexUI.fxml";

    private static final String TOPICS_INDEX_ADD = "/com/arthroverse/duckyemulator/fxmls/AdminUIs/NewTopicsAddUI.fxml";

    private static final String TOPICS_INDEX_UPDATE = "/com/arthroverse/duckyemulator/fxmls/AdminUIs/NewTopicsUpdateUI.fxml";

    private static final String CLASS_INDEX_ADD = "/com/arthroverse/duckyemulator/fxmls/AdminUIs/NewClassificationsAddUI.fxml";

    private static final String CLASS_INDEX_UPDATE = "/com/arthroverse/duckyemulator/fxmls/AdminUIs/NewClassificationsUpdateUI.fxml";

    private static final String LOGIN_PAGE = "/com/arthroverse/duckyemulator/fxmls/Login/NewLoginUI.fxml";

    private static final String CREATE_ACCOUNT = "/com/arthroverse/duckyemulator/fxmls/CreateAccount/NewCreateAccountDialog.fxml";

    private static final String USER_HOME_PAGE = "/com/arthroverse/duckyemulator/fxmls/PublicUserUI/UserHomePage.fxml";

    private static final String USER_TESTING_PAGE = "/com/arthroverse/duckyemulator/fxmls/PublicUserUI/UserTestingUI.fxml";

    private static final String USER_RESULT_PAGE = "/com/arthroverse/duckyemulator/fxmls/PublicUserUI/UserResultPage.fxml";

    private static final String USER_SESSION_HISTORIES_PAGE = "/com/arthroverse/duckyemulator/fxmls/PublicUserUI/UserSessionHistoriesPage.fxml";

    private static final String CREDITS = "/com/arthroverse/duckyemulator/fxmls/CreditDialog/CreditDialogUI.fxml";

    private Stage stage;

    private Stage secondStage = new Stage();

    private Stage thirdStage = new Stage();

    private static Navigator nav = null;

    private FXMLLoader loader;

    private Image fxIcons;

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

    public Stage getSecondStage(){
        return this.secondStage;
    }

    public Stage getThirdStage(){
        return this.thirdStage;
    }

   public FXMLLoader getLoader(){
        return this.loader;
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
    *
    * The reason we put it on this method is because that this method
    * set stage is called only ONCE in {@link DuckyEmulatorMain}
    * and also all main panels are mostly use a single instance of
    * {@code Stage} => leave it here will improve the overall
    * performance of the app (extremely improved)*/
   public void setStage(Stage stage){
        this.stage = stage;
       Rectangle2D screenBound = Screen.getPrimary().getVisualBounds();
       this.stage.setX(screenBound.getMinX());
       this.stage.setY(screenBound.getMinY());
       this.stage.setWidth(screenBound.getWidth());
       this.stage.setHeight(screenBound.getHeight());
   }
   private void goTo(String fxml) throws IOException {
        this.generateWindow(fxml);
   }

    /**
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
     * If not, we will ask it to bring it to the front of the screen<p>
     * Update JDK 17: In this new version of DuckyEmulator, I have updated
     * the JDK version to JDK 17, which will change how JavaFX works a bit.
     * As mentioned in the JavaDoc in
     * {@link DuckyEmulatorMain}
     * in JDK 17, any stages that dont have scene set and invoked with method {@code show()}
     * will become a visible window. Although the current code base is good, the
     * introduction of login/logout form has made my current {@code generateWindow()}
     * method become a bit buggy (since login/logout form is the entry point of my program,
     * therefore, activating its FXML along with the {@code this.stage} invocation from
     * {@link DuckyEmulatorMain} will make my UI
     * become broken. Therefore, only appropriate scene will be loaded to the
     * {@code primaryStage}, which has been pointed to {@code this.stage} will be invoked
     * by using {@code show()} method (if all conditions satisfied)*/

    private void generateWindow(String fxml) throws IOException {
        if(!fxml.equals(QBANK_ADD) & !fxml.equals(QBANK_UPDATE)
                & !fxml.equals(TOPICS_INDEX_ADD) & !fxml.equals(TOPICS_INDEX_UPDATE)
                & !fxml.equals(CLASS_INDEX_ADD) & !fxml.equals(CLASS_INDEX_UPDATE)
                & !fxml.equals(LOGIN_PAGE) & !fxml.equals(CREATE_ACCOUNT) & !fxml.equals(CREDITS)){
            this.loader = new FXMLLoader();
            loader.setLocation((getClass().getResource(fxml)));
            Parent root = loader.load();
            Scene currentScene = new Scene(root);
            this.stage.setScene(currentScene);
            this.stage.show();
        }else if(fxml.equals(CREDITS)){
           this.thirdStage = new Stage();
           this.thirdStage.getIcons().add(fxIcons);
           this.loader = new FXMLLoader();
           this.loader.setLocation(getClass().getResource(CREDITS));
            Parent thirdRoot = this.loader.load();
            Scene qBankAddScene = new Scene(thirdRoot);
            thirdStage.setScene(qBankAddScene);
            thirdStage.initModality(Modality.WINDOW_MODAL);
            thirdStage.initOwner(this.stage);
            if(thirdStage != null){
                thirdStage.requestFocus();
            }
            thirdStage.show();
        }else{
            this.secondStage = new Stage();
            this.secondStage.getIcons().add(fxIcons);
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
            else if(fxml.equals(CLASS_INDEX_UPDATE))
                this.loader.setLocation(getClass().getResource(CLASS_INDEX_UPDATE));
            else if(fxml.equals(LOGIN_PAGE))
                this.loader.setLocation(getClass().getResource(LOGIN_PAGE));
            else if(fxml.equals(CREATE_ACCOUNT)){
                this.loader.setLocation(getClass().getResource(CREATE_ACCOUNT));
            }
            Parent secondRoot = this.loader.load();
            Scene qBankAddScene = new Scene(secondRoot);
            secondStage.setScene(qBankAddScene);
            secondStage.initModality(Modality.WINDOW_MODAL);
            secondStage.initOwner(this.stage);
            if(secondStage != null){
                secondStage.requestFocus();
            }
            secondStage.show();
        }
   }

   /**
    * This method is created to close all secondary windows (or main window but
    * doesn't need full screen feature (such as login/logout form). This will
    * make sure that there are no random window will pop up as a result of
    * unused or used un-closed stage*/
   public void closeSecondStage(){
        this.secondStage.close();
        this.secondStage = null;
   }

    public void closeThirdStage(){
        this.thirdStage.close();
        this.thirdStage = null;
    }

   public void setIcons(Image fxIcon){
       this.fxIcons = fxIcon;
   }

   public void goToAdminHomePage() throws IOException {
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

    public void goToLoginPage() throws IOException {
       if(this.stage.isShowing()) this.stage.close();
       this.goTo(LOGIN_PAGE);
    }

    public void goToUserHomePage() throws IOException{
       this.goTo(USER_HOME_PAGE);
    }

    public void goToTestPage() throws IOException{
       this.goTo(USER_TESTING_PAGE);
    }

    public void goToResultPage() throws IOException{
       this.goTo(USER_RESULT_PAGE);
    }

    public void goToUserSessionHistories() throws IOException{
       this.goTo(USER_SESSION_HISTORIES_PAGE);
    }

    public void goToUserCreateAccount() throws IOException{
       this.goTo(CREATE_ACCOUNT);
    }

    public void goToCredit() throws IOException{
       this.goTo(CREDITS);
    }
}
