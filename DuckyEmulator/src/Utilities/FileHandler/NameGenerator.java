package Utilities.FileHandler;

import java.util.UUID;

public final class NameGenerator {
    public static String fileNameGenerator(int qId){
        return UUID.randomUUID() + "_diagram_" + "qId" + qId;
    }
}
