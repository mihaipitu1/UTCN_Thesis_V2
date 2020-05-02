package main;

import commons.logger.LoggerConfig;
import models.Tutorial;
import utilities.TutorialUtility;
import views.MainView;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String args[]) {
        LoggerConfig loggerConfig = new LoggerConfig();
        LoggerConfig.infoLog("Started Application");
        MainView mW = new MainView();
        mW.setVisible(true);
    }
}
