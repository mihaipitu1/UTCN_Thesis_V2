package views;

import commons.logger.LoggerConfig;
import commons.ui.events.FileEvents;
import views.guides.GuideView;
import views.tutorials.TutorialView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends BaseView {

    private static final String TAG = "Main View";

    private FileEvents fileEvents;

    public MainView() {
        super();
        LoggerConfig.infoLog(TAG,"MainView()");
        initComponents();
    }

    private void initComponents() {
        this.setTitle("Main View");
        LoggerConfig.infoLog(TAG,"initComponents()");
        LoggerConfig.infoLog(TAG,"initComponents() >> Initiate events >> fileEvents");
        fileEvents = new FileEvents();

        LoggerConfig.infoLog(TAG,"initComponents() >> Set action events");
        setActionEvents();
    }

    private void setActionEvents() {
        LoggerConfig.infoLog(TAG,"setActionEvents()");
        this.getCommonElements().getFileMenu().getNewMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG,"setActionEvents() >> fileMenu.getNewMenuItem() Action Listener");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("New File...");

                int userSelection = fileChooser.showSaveDialog(new JFrame());

                if(userSelection == JFileChooser.APPROVE_OPTION) {
                    String path = fileEvents.newFileEvent(fileChooser.getSelectedFile().getAbsolutePath());
                    MainView.this.setVisible(false);
                    FileView fileView = new FileView(path);
                    fileView.setVisible(true);
                }
            }
        });

        this.getCommonElements().getFileMenu().getOpenMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG,"setActionEvents() >> fileMenu.getOpenMenuItem() Action Listener");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open File...");
                int userSelection = fileChooser.showOpenDialog(new JFrame());

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    MainView.this.setVisible(false);
                    FileView fileView = new FileView(path);
                    fileView.setVisible(true);
                }
            }
        });

        this.getCommonElements().getFileMenu().getExitMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent actionEvent){
                LoggerConfig.infoLog(TAG,"setActionEvents() >> fileMenu.getOpenMenuItem() Action Listener");
                MainView.super.dispose();
            }
        });

        this.getCommonElements().getTutorialMenu().getTutorialListMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG,"setActionEvents() >> tutorialMenu.getTutorialListMenuItem() Action Listener");
                TutorialView tutorialView = new TutorialView();
                MainView.this.setVisible(false);
                tutorialView.setVisible(true);
            }
        });

        this.getCommonElements().getTutorialMenu().getHelpListMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG, "setActionEvents() >> tutorialMenu.getHelpListMenuItem() Action Listener");
                GuideView guideView = new GuideView();
                MainView.this.setVisible(false);
                guideView.setVisible(true);
            }
        });
    }
}

