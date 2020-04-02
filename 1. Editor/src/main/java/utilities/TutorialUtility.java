package utilities;

import com.google.gson.Gson;
import models.Tutorial;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TutorialUtility {
    private List<Tutorial> tutorials;
    private static final String storageFileURL = "./resources/storage/tutorials.json";

    public TutorialUtility() {
        tutorials = new ArrayList<Tutorial>();
        loadTutorials();
    }

    private void loadTutorials() {
        try {
            String jsonFile = new String(Files.readAllBytes(Paths.get(storageFileURL)));
            Gson gson = new Gson();

            Tutorial[] tutorialArray = gson.fromJson(jsonFile,Tutorial[].class);

            for(Tutorial t:tutorialArray)
            {
                tutorials.add(t);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Tutorial> getTutorials() {
        return this.tutorials;
    }
}
