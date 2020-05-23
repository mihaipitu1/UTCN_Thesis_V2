package commons.ui.events;

import commons.logger.LoggerConfig;
import utilities.FileUtility;

public class FileEvents {
    private FileUtility fileUtility;
    private static final String TAG = "File Events";

    public FileEvents() {
        LoggerConfig.infoLog(TAG,"FileEvents() - initializing necessary utilities");
        fileUtility = new FileUtility();
    }

    public String newFileEvent(String fileName) {
        LoggerConfig.infoLog(TAG,"newFileEvent() - fileName: {0}", new Object[] {fileName});
        return fileUtility.createFile(fileName);
    }

    public String openFileEvent(String fileName) {
        LoggerConfig.infoLog(TAG,"openFileEvent() - fileName: {0}", new Object[] {fileName});
        return fileUtility.loadFile(fileName);
    }

    public void saveFileEvent(String fileName, String content) {
        LoggerConfig.infoLog(TAG,"saveFileEvent() - fileName: {0}, content: {1}", new Object[] {fileName, content});

        fileUtility.saveFile(fileName, content);
    }
}
