package utilities;

import com.google.gson.Gson;
import commons.logger.LoggerConfig;
import models.Guide;
import models.Tutorial;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TutorialUtility {
    private List<Tutorial> tutorials;
    private List<Guide> guides;

    private static final String tutorialsFileURL = "./resources/storage/tutorials.json";
    private static final String guidesFileURL = "./resources/storage/guides.json";
    private static final String TAG = "Tutorial Utility";

    public TutorialUtility() {
        LoggerConfig.infoLog(TAG, "TutorialUtility()");
        tutorials = new ArrayList<Tutorial>();
        guides = new ArrayList<Guide>();
        loadTutorials();
        loadGuides();
    }

    private void loadTutorials() {
        LoggerConfig.infoLog(TAG, "loadTutorials()");
        try {
            LoggerConfig.infoLog(TAG, "loadTutorials() from {0}", new Object[] {tutorialsFileURL});
            String jsonFile = new String(Files.readAllBytes(Paths.get(tutorialsFileURL)));
            LoggerConfig.infoLog(TAG, "loadTutorials() - tutorials.json result: {0}", new Object[] {jsonFile});
            Gson gson = new Gson();

            Tutorial[] tutorialArray = gson.fromJson(jsonFile,Tutorial[].class);

            for(Tutorial t:tutorialArray)
            {
                tutorials.add(t);
            }
            LoggerConfig.infoLog(TAG, "loadTutorials() >> Updated tutorials list");
        } catch (IOException e) {
            LoggerConfig.errorLog(TAG, "loadTutorials() >> Error: " + e.getStackTrace());
        }
    }

    private void loadGuides() {
        LoggerConfig.infoLog(TAG, "loadGuides()");
        try {
            LoggerConfig.infoLog(TAG, "loadGuides() from {0}", new Object[]{guidesFileURL});
            String jsonFile = new String(Files.readAllBytes(Paths.get(guidesFileURL)));
            LoggerConfig.infoLog(TAG, "loadGuides() - guides.json result: {0}", new Object[]{jsonFile});
            Gson gson = new Gson();

            Guide[] guidesArray = gson.fromJson(jsonFile, Guide[].class);

            for (Guide g : guidesArray) {
                guides.add(g);
            }
            LoggerConfig.infoLog(TAG, "loadGuides() >> Updated guides list");
        } catch (IOException e) {
            LoggerConfig.errorLog(TAG, "loadGuides() >> Error: " + e.getStackTrace());

        }
    }

    public List<Tutorial> getTutorials() {
        return this.tutorials;
    }

    public List<Guide> getGuides() {
        return this.guides;
    }

}
