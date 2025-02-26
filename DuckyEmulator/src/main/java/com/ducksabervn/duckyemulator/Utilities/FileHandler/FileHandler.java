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
package com.ducksabervn.duckyemulator.Utilities.FileHandler;

import com.ducksabervn.duckyemulator.UIs.Navigator;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public final class FileHandler {

    public static final String userPath = System.getProperty("user.home");

    public static final String programFolderName = "DuckyEmulator";

    public static final String programLogFolderName = "DuckyEmulator/logs";

    public static final String programImgSrcFolderName = "DuckyEmulator/imgsrc";

    public static final String programImgDeletedSrcFolderName = "DuckyEmulator/deletedImg";

    public static void initialize(){
        new File(userPath,programFolderName).mkdir();
        new File(userPath, programLogFolderName).mkdirs();
        new File(userPath, programImgSrcFolderName).mkdirs();
        new File(userPath, programImgDeletedSrcFolderName).mkdirs();
    }

    public static String returnImgPath(FileChooser fileChooser){
        File selectedFile = fileChooser.showOpenDialog(Navigator.getInstance().getStage());
        if (selectedFile != null) return selectedFile.getAbsolutePath();
        return null;
    }

    public static String addNewImage(String imgPath, int qId) throws IOException {
        if(checkExistingPath(imgPath)) return imgPath;
        Path originalAbsPath = Paths.get(imgPath);
        Path fileName = originalAbsPath.getFileName();
        String newFileName = "";
        String originalFileName = fileName.toString();
        String imgExtension =
                    originalFileName.
                            substring(originalFileName.lastIndexOf('.'));
        newFileName = NameGenerator.fileNameGenerator(qId) + imgExtension;
        Path newPath = Paths.get(userPath + "/" + programImgSrcFolderName + "/" + newFileName);
        Files.copy(originalAbsPath, newPath, StandardCopyOption.REPLACE_EXISTING);
        return programImgSrcFolderName + "/" + newFileName;
    }

    private static boolean checkExistingPath(String inputPath){
        File img = new File(System.getProperty("user.home") + "/" + inputPath);
        return img.exists();
    }

    public static void removeImage(String imgPath) throws IOException {
        Path delPath = Paths.get(userPath + "/" + imgPath);
        Files.delete(delPath);
    }

    public static String deleteAndReplaceExistingImg(String relativePath) throws IOException {
        String fileNameExtracted = relativePath.substring(relativePath.lastIndexOf("/") + 1);
        Path newPath = Paths.get(userPath + "/" + programImgDeletedSrcFolderName + "/" + fileNameExtracted);
        Path oldPath = Paths.get(userPath + "/" + relativePath);
        Files.copy(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
        return programImgDeletedSrcFolderName + "/" + fileNameExtracted;
    }
}
