package views;


import commons.logger.LoggerConfig;
import commons.ui.events.CompilerEvents;
import commons.ui.events.FileEvents;
import commons.ui.menu.CompileMenu;
import commons.ui.menu.FileMenu;
import commons.ui.menu.TutorialMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileView extends BaseView {
    private FileMenu fileMenu;
    private CompileMenu compileMenu;
    private TutorialMenu tutorialMenu;

    private FileEvents fileEvents;
    private CompilerEvents compilerEvents;

    private JTextArea codeArea;
    private JTextArea outputArea;

    private String filePath;

    public FileView(String fileName) {
        super();
        LoggerConfig.infoLog("File View >> FileView() >> fileName : {0}",new Object[]{fileName});
        initComponents(fileName);
    }

    private void initComponents(String fileName){
        this.setTitle("SueC - " + fileName);
        LoggerConfig.infoLog("File View >> initComponents()");
        LoggerConfig.infoLog("File View >> initComponents() >> fileName : {0}",new Object[]{fileName});
        filePath = fileName;
        LoggerConfig.infoLog("File View >> initComponents() >> filePath : {0}",new Object[]{filePath});
        LoggerConfig.infoLog("File View >> initComponents() >> Initialize events >> fileEvents");
        fileEvents = new FileEvents();
        LoggerConfig.infoLog("File View >> initComponents() >> Initialize events >> compilerEvents");
        compilerEvents = new CompilerEvents();

        LoggerConfig.infoLog("File View >> initComponents() >> Initialize text areas >> codeArea");
        codeArea = new JTextArea();
        LoggerConfig.infoLog("File View >> initComponents() >> Initialize text areas >> outputArea");
        outputArea = new JTextArea();

        codeArea.setText(fileEvents.openFileEvent(fileName));
        outputArea.setEditable(false);

        this.getMainPanel().add(codeArea);
        this.getMainPanel().add(outputArea);

        LoggerConfig.infoLog("File View >> initComponents() >> Initialize menus >> fileMenu");
        fileMenu = new FileMenu();
        LoggerConfig.infoLog("File View >> initComponents() >> Initialize menus >> compileMenu");
        compileMenu = new CompileMenu();
        LoggerConfig.infoLog("File View >> initComponents() >> Initialize menus >> tutorialMenu");
        tutorialMenu = new TutorialMenu();

        this.getMainMenuBar().addMenuItemAt(fileMenu.getMenu(),0);
        this.getMainMenuBar().addMenuItemAt(compileMenu.getMenu(),1);
        this.getMainMenuBar().addMenuItemAt(tutorialMenu.getMenu(),2);

        LoggerConfig.infoLog("File View >> initComponents() >> Set action events");
        setActionEvents();
    }

    private void setActionEvents() {
        LoggerConfig.infoLog("File View >> setActionEvents()");
        fileMenu.getNewMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog("File View >> setActionEvents() >> fileMenu.getNewMenuItem() Action Listener");
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

        fileMenu.getOpenMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog("File View >> setActionEvents() >> fileMenu.getOpenMenuItem() Action Listener");
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

        fileMenu.getSaveMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog("File View >> setActionEvents() >> fileMenu.getSaveMenuItem() Action Listener");
                fileEvents.saveFileEvent(filePath,codeArea.getText());
            }
        });

        fileMenu.getExitMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent actionEvent){
                LoggerConfig.infoLog("File View >> setActionEvents() >> fileMenu.getExitMenuItem() Action Listener");
                FileView.super.dispose();
            }
        });

        compileMenu.getCompileMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog("File View >> setActionEvents() >> compileMenu.getCompileMenuItem() Action Listener");
                fileEvents.saveFileEvent(filePath,codeArea.getText());
                String compileResult = compilerEvents.compilerFileEvent(filePath);
                outputArea.setText(compileResult);
            }
        });
    }
}

