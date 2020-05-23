package views;

import commons.logger.LoggerConfig;
import commons.ui.events.FileEvents;
import commons.ui.menu.CompileMenu;
import commons.ui.menu.FileMenu;
import commons.ui.menu.TutorialMenu;
import views.guides.GuideView;
import views.tutorials.TutorialView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends BaseView {
    private FileMenu fileMenu;
    private CompileMenu compileMenu;
    private TutorialMenu tutorialMenu;

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

        LoggerConfig.infoLog(TAG,"initComponents() >> Initiate menus >> fileMenu");
        fileMenu = new FileMenu();
        LoggerConfig.infoLog(TAG,"initComponents() >> Initiate menus >> compileMenu");
        compileMenu = new CompileMenu();
        LoggerConfig.infoLog(TAG,"initComponents() >> Initiate menus >> tutorialMenu");
        tutorialMenu = new TutorialMenu();

        this.getMainMenuBar().addMenuItemAt(fileMenu.getMenu(),0);
        this.getMainMenuBar().addMenuItemAt(compileMenu.getMenu(),1);
        this.getMainMenuBar().addMenuItemAt(tutorialMenu.getMenu(),2);

        LoggerConfig.infoLog(TAG,"initComponents() >> Set action events");
        setActionEvents();
    }

    private void setActionEvents() {
        LoggerConfig.infoLog(TAG,"setActionEvents()");
        fileMenu.getNewMenuItem().addActionListener(new ActionListener() {
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

        fileMenu.getOpenMenuItem().addActionListener(new ActionListener() {
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

        fileMenu.getExitMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent actionEvent){
                LoggerConfig.infoLog(TAG,"setActionEvents() >> fileMenu.getOpenMenuItem() Action Listener");
                MainView.super.dispose();
            }
        });

        tutorialMenu.getTutorialListMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG,"setActionEvents() >> tutorialMenu.getTutorialListMenuItem() Action Listener");
                TutorialView tutorialView = new TutorialView();
                MainView.this.setVisible(false);
                tutorialView.setVisible(true);
            }
        });

        tutorialMenu.getHelpListMenuItem().addActionListener(new ActionListener() {
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

