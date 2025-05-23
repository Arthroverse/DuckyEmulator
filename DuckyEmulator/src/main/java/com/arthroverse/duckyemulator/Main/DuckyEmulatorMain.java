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
 * <pre>
 * {@code
 *     static{
 *         Classifications.selectAll();
 *         Topics.selectAll();
 *     }
 * }
 * </pre>
 * {@code selectAll()} method is a performance-impact method, which means each time this method is called,
 * the app performance will be impacted heavily. However, since this method is designed to be run only once,
 * therefore, let them run in a static initializer in the Main class during app initialization is reasonable
 * (since all apps has a pretty long initialization)*/
public class DuckyEmulatorMain extends Application {
    static{
        FileHandler.initialize();
        Classifications.selectAll();
        Topics.selectAll();
    }
    /**
     * Update Java 17: Since JDK 17, any new Stages that have been created will
     * automatically become a windows (if we use {@code show()} method). Therefore,
     * to prevent random stage become a window before any scenes are added, we
     * need to know where we should use {@code show()} method*/
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("DuckyEmulator");
        Navigator.getInstance().setStage(primaryStage);
        Navigator.getInstance().goToLoginPage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}