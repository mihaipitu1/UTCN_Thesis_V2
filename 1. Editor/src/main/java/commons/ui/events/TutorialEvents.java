package commons.ui.events;

import commons.logger.LoggerConfig;
import models.Guide;
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
    private static final String TAG = "Tutorial Events";

    public TutorialEvents() {
        LoggerConfig.infoLog(TAG, "TutorialEvents() >> initializing utility classes");
        tutorialUtility = new TutorialUtility();
        compilerUtility = new CompilerUtility();
        fileUtility = new FileUtility();
    }

    public List<Tutorial> loadTutorialsEvent() {
        LoggerConfig.infoLog(TAG,"loadTutorialsEvent() >> returning the tutorials");
        return tutorialUtility.getTutorials();
    }

    public List<Guide> loadGuidesEvent() {
        LoggerConfig.infoLog(TAG,"loadTutorialsEvent() >> returning the guides");
        return tutorialUtility.getGuides();
    }

    public String compileTutorialEvent(String content) {
        LoggerConfig.infoLog(TAG, "compileTutorialEvent() - content: {0}",new Object[]{content});
        fileUtility.createFile(tempFileURL);
        fileUtility.saveFile(tempFileURL,content);
        LoggerConfig.infoLog(TAG, "compileTutorialEvent() >> tempFile {0} created and loaded with {1}", new Object[] {tempFileURL,content});
        String returnedOutput = compilerUtility.compileFile(tempFileURL);
        String lines[] = returnedOutput.split("\n");
        LoggerConfig.infoLog(TAG, "compileTutorialEvent() >> Output returned: {0} and tempFile deleted", new Object[]{lines[0]});
        fileUtility.deleteFile(tempFileURL);
        return lines[0];
    }
}
