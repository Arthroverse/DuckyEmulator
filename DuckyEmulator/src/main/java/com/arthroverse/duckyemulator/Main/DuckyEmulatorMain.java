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
package com.arthroverse.duckyemulator.Main;

import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Classifications;
import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Topics;
import com.arthroverse.duckyemulator.UIs.Navigator;
import com.arthroverse.duckyemulator.Utilities.FileHandler.FileHandler;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class serves as the entry point to the emulator
 * <p>
 * DuckyEmulator is a universal testing emulator that supports real-time
 * testing (currently only supports MCQs), question bank management
 * (creating, editing, and deleting questions, topics, and classifications),
 * test customization, ... and more !
 * @author ducksabervn
 * @version 0.1
 * @since 2025-05-25
 */
public class DuckyEmulatorMain extends Application {
    /**
     * A static initializer that starts only once when the emulator runs for the first time
     * <p>
     * These are all essential data that need to be loaded during the initialization
     * of the emulator. These data will be initialized only once
     *
     */
    static{
        //initialize necessary directories
        FileHandler.initialize();

        //Initialize all available question classifications for the user to edit any question
        Classifications.selectAll();

        //Initialize all available question topics for the user to edit any question
        Topics.selectAll();
    }

    /**
     * JavaFX start method, don't ask me, ask the JavaFX developer why tf they put it here
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("DuckyEmulator"); //Set up the name for the emulator windows
        Navigator.getInstance().setStage(primaryStage); //Set up the main stage where all emulator scenes will be loaded
        Navigator.getInstance().goToLoginPage(); //Once everything has been set up, redirect the user to the authenication form
    }

    /**
     * This main method will start the emulator, nothing special
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args); //I don't know what this is, ask JavaFX developer if you want to know
    }
}