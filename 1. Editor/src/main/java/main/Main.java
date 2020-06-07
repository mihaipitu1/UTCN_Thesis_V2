package main;

import commons.logger.LoggerConfig;
import views.MainView;

public class Main {
    private static final String TAG = "Main";


    public static void main(String args[]) {
        LoggerConfig loggerConfig = new LoggerConfig();
        LoggerConfig.infoLog(TAG,"Started Application");
        MainView mW = new MainView();
        mW.setVisible(true);
    }
}
