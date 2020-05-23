package utilities;

import commons.logger.LoggerConfig;

import java.io.File;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtility {
    private static final String TAG = "File Utility";

    public String createFile(String fileName) {
        LoggerConfig.infoLog(TAG, "createFile() - fileName: {0}", new Object[] {fileName});
        File newFile = new File(fileName);
        try {
            LoggerConfig.infoLog(TAG, "createFile() >> Creating a new file at {0}", new Object[] {fileName});
            newFile.createNewFile();
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write("write \"Hello World\";");
            LoggerConfig.infoLog(TAG, "createFile() >> File Created");
            fileWriter.close();
        } catch (IOException e) {
            LoggerConfig.errorLog(TAG, "createFile() >> Error: " + e.getStackTrace());
        }
        return fileName;
    }

    public String loadFile(String fileName) {
        LoggerConfig.infoLog(TAG, "loadFile() - fileName: {0}", new Object[] {fileName});
        String outputContent = null;
        try {
            outputContent = new String(Files.readAllBytes(Paths.get(fileName)));
            LoggerConfig.infoLog(TAG, "loadFile() - content to be loaded: {0}", new Object[] {outputContent});
        } catch (IOException e) {
            LoggerConfig.errorLog(TAG, "loadFile() >> Error: " + e.getStackTrace());
        }
        return outputContent;
    }

    public void saveFile(String fileName, String content) {
        LoggerConfig.infoLog(TAG, "saveFile() - fileName: {0} ; content: {1}", new Object[]{fileName, content});

        File file = new File(fileName);

        LoggerConfig.infoLog(TAG, "saveFile() >> Deleting file - {0}", new Object[] {fileName});
        deleteFile(fileName);
        try {
            LoggerConfig.infoLog(TAG, "saveFile() >> Recreating file - {0}", new Object[]{fileName});
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(fileName);
            LoggerConfig.infoLog(TAG, "saveFile() >> Updating file {0} with content: {1}", new Object[] {fileName, content});
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            LoggerConfig.errorLog(TAG, "saveFile() >> Error: " + e.getStackTrace());
        }
    }

    public void deleteFile(String fileName) {
        LoggerConfig.infoLog(TAG, "deleteFile() fileName: {0}", new Object[] {fileName});
        File file = new File(fileName);

        if(file.exists()) {
            file.delete();
        }
    }
}
