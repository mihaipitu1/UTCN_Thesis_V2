package commons.ui.events;

import models.Tutorial;
import utilities.CompilerUtility;
import utilities.FileUtility;
import utilities.TutorialUtility;

import java.util.List;

public class TutorialEvents {
    private TutorialUtility tutorialUtility;
    private CompilerUtility compilerUtility;
    private FileUtility fileUtility;
    private static final String tempFileURL = "./resources/tempData/temp.suc";

    public TutorialEvents() {
        tutorialUtility = new TutorialUtility();
        compilerUtility = new CompilerUtility();
        fileUtility = new FileUtility();
    }

    public List<Tutorial> loadTutorialsEvent() {
        return tutorialUtility.getTutorials();
    }

    public String compileTutorialEvent(String content) {
        fileUtility.createFile(tempFileURL);
        fileUtility.saveFile(tempFileURL,content);
        String returnedOutput = compilerUtility.compileFile(tempFileURL);
        fileUtility.deleteFile(tempFileURL);
        return returnedOutput;
    }
}
