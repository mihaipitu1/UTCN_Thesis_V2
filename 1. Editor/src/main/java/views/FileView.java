package views;


import commons.logger.LoggerConfig;
import commons.ui.events.CompilerEvents;
import commons.ui.events.FileEvents;
import views.guides.GuideView;
import views.tutorials.TutorialView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileView extends BaseView {
    private FileEvents fileEvents;
    private CompilerEvents compilerEvents;

    private JTextArea codeArea;
    private JTextArea outputArea;

    private String filePath;

    private static final String TAG = "File View";

    public FileView(String fileName) {
        super();
        LoggerConfig.infoLog(TAG,"FileView() >> fileName : {0}",new Object[]{fileName});
        initComponents(fileName);
    }

    private void initComponents(String fileName){
        this.setTitle("SueC - " + fileName);
        LoggerConfig.infoLog(TAG,"initComponents()");
        LoggerConfig.infoLog(TAG,"initComponents() >> fileName : {0}",new Object[]{fileName});
        filePath = fileName;
        LoggerConfig.infoLog(TAG,"initComponents() >> filePath : {0}",new Object[]{filePath});
        LoggerConfig.infoLog(TAG,"initComponents() >> Initialize events >> fileEvents");
        fileEvents = new FileEvents();
        LoggerConfig.infoLog(TAG,"initComponents() >> Initialize events >> compilerEvents");
        compilerEvents = new CompilerEvents();

        LoggerConfig.infoLog(TAG,"initComponents() >> Initialize text areas >> codeArea");
        codeArea = new JTextArea();
        LoggerConfig.infoLog(TAG,"initComponents() >> Initialize text areas >> outputArea");
        outputArea = new JTextArea();

        codeArea.setText(fileEvents.openFileEvent(fileName));
        outputArea.setEditable(false);

        this.getMainPanel().add(codeArea);
        this.getMainPanel().add(outputArea);

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
                    FileView.this.setVisible(false);
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
                    FileView.this.setVisible(false);
                    FileView fileView = new FileView(path);
                    fileView.setVisible(true);
                }
            }
        });

        this.getCommonElements().getFileMenu().getSaveMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG,"setActionEvents() >> fileMenu.getSaveMenuItem() Action Listener");
                fileEvents.saveFileEvent(filePath,codeArea.getText());
            }
        });

        this.getCommonElements().getFileMenu().getExitMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent actionEvent){
                LoggerConfig.infoLog(TAG,"setActionEvents() >> fileMenu.getExitMenuItem() Action Listener");
                FileView.super.dispose();
            }
        });

        this.getCommonElements().getCompileMenu().getCompileMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG,"setActionEvents() >> compileMenu.getCompileMenuItem() Action Listener");
                fileEvents.saveFileEvent(filePath,codeArea.getText());
                String compileResult = compilerEvents.compilerFileEvent(filePath);
                outputArea.setText(compileResult);
            }
        });

        this.getCommonElements().getTutorialMenu().getTutorialListMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileEvents.saveFileEvent(filePath,codeArea.getText());
                TutorialView tutorialView = new TutorialView();
                FileView.this.setVisible(false);
                tutorialView.setVisible(true);
            }
        });

        this.getCommonElements().getTutorialMenu().getHelpListMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileEvents.saveFileEvent(filePath,codeArea.getText());
                GuideView guideView = new GuideView();
                FileView.this.setVisible(false);
                guideView.setVisible(true);
            }
        });
    }
}

