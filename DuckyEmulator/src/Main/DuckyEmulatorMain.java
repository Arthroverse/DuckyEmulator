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
package Main;

import Database.MainDB.AdminBeans.Classifications;
import Database.MainDB.AdminBeans.Topics;
import UIs.Navigator;
import Utilities.FileHandler.FileHandler;
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
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("DuckyEmulator");
        Navigator.getInstance().setStage(primaryStage);
        Navigator.getInstance().goToHome();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}