package utilities;

import java.io.File;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtility {
    public String createFile(String fileName) {
        File newFile = new File(fileName);
        try {
            newFile.createNewFile();
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write("write \"Hello World\";");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public String loadFile(String fileName) {
        String outputContent = null;
        try {
            outputContent = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputContent;
    }

    public void saveFile(String fileName, String content) {
        File file = new File(fileName);

        if(file.exists())
        {
            file.delete();
        }
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
