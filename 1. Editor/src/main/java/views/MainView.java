package views;

import commons.logger.LoggerConfig;
import commons.ui.events.FileEvents;
import commons.ui.menu.CompileMenu;
import commons.ui.menu.FileMenu;
import commons.ui.menu.HelpMenu;
import commons.ui.menu.TutorialMenu;
import main.Main;
import models.Tutorial;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends BaseView {
    private FileMenu fileMenu;
    private CompileMenu compileMenu;
    private TutorialMenu tutorialMenu;

    private FileEvents fileEvents;

    public MainView() {
        super();
        LoggerConfig.infoLog("Main View >> MainView()");
        initComponents();
    }

    private void initComponents() {
        this.setTitle("Main View");
        LoggerConfig.infoLog("Main View >> initComponents()");
        LoggerConfig.infoLog("Main View >> initComponents() >> Initiate events >> fileEvents");
        fileEvents = new FileEvents();

        LoggerConfig.infoLog("Main View >> initComponents() >> Initiate menus >> fileMenu");
        fileMenu = new FileMenu();
        LoggerConfig.infoLog("Main View >> initComponents() >> Initiate menus >> compileMenu");
        compileMenu = new CompileMenu();
        LoggerConfig.infoLog("Main View >> initComponents() >> Initiate menus >> tutorialMenu");
        tutorialMenu = new TutorialMenu();

        this.getMainMenuBar().addMenuItemAt(fileMenu.getMenu(),0);
        this.getMainMenuBar().addMenuItemAt(compileMenu.getMenu(),1);
        this.getMainMenuBar().addMenuItemAt(tutorialMenu.getMenu(),2);

        LoggerConfig.infoLog("Main View >> initComponents() >> Set action events");
        setActionEvents();
    }

    private void setActionEvents() {
        LoggerConfig.infoLog("Main View >> setActionEvents()");
        fileMenu.getNewMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog("Main View >> setActionEvents() >> fileMenu.getNewMenuItem() Action Listener");
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
                LoggerConfig.infoLog("Main View >> setActionEvents() >> fileMenu.getOpenMenuItem() Action Listener");
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
                LoggerConfig.infoLog("Main View >> setActionEvents() >> fileMenu.getOpenMenuItem() Action Listener");
                MainView.super.dispose();
            }
        });

        tutorialMenu.getTutorialListMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog("Main View >> setActionEvents() >> tutorialMenu.getTutorialListMenuItem() Action Listener");
                TutorialView tutorialView = new TutorialView();
                MainView.this.setVisible(false);
                tutorialView.setVisible(true);
            }
        });
    }
}

