package Utilities.FileHandler;

import UIs.Navigator;
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

    public static void initialize(){
        new File(userPath,programFolderName).mkdir();
        new File(userPath, programLogFolderName).mkdirs();
        new File(userPath, programImgSrcFolderName).mkdirs();
    }

    public static String returnImgPath(FileChooser fileChooser){
        File selectedFile = fileChooser.showOpenDialog(Navigator.getInstance().getStage());
        if (selectedFile != null) return selectedFile.getAbsolutePath();
        return null;
    }

    public static String addNewImage(String imgAbsPath, int qId) throws IOException {
        if(checkExistingPath(imgAbsPath)) return imgAbsPath;
        Path originalAbsPath = Paths.get(imgAbsPath);
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
}
