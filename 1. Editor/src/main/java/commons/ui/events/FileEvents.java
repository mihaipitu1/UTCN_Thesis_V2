package commons.ui.events;

import utilities.FileUtility;

public class FileEvents {
    private FileUtility fileUtility;

    public FileEvents() {
        fileUtility = new FileUtility();
    }

    public String newFileEvent(String fileName) {
        return fileUtility.createFile(fileName);
    }

    public String openFileEvent(String fileName) {
        return fileUtility.loadFile(fileName);
    }

    public void saveFileEvent(String fileName, String content) {
        fileUtility.saveFile(fileName, content);
    }
}
