package views;


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
        initComponents(fileName);
    }

    private void initComponents(String fileName){
        this.setTitle("SueC - " + fileName);
        filePath = fileName;
        fileEvents = new FileEvents();
        compilerEvents = new CompilerEvents();

        codeArea = new JTextArea();
        outputArea = new JTextArea();

        codeArea.setText(fileEvents.openFileEvent(fileName));
        outputArea.setEditable(false);


        this.getMainPanel().add(codeArea);
        this.getMainPanel().add(outputArea);





        fileMenu = new FileMenu();
        compileMenu = new CompileMenu();
        tutorialMenu = new TutorialMenu();

        this.getMainMenuBar().addMenuItemAt(fileMenu.getMenu(),0);
        this.getMainMenuBar().addMenuItemAt(compileMenu.getMenu(),1);
        this.getMainMenuBar().addMenuItemAt(tutorialMenu.getMenu(),2);

        setActionEvents();
    }

    private void setActionEvents() {
        fileMenu.getNewMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
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
                fileEvents.saveFileEvent(filePath,codeArea.getText());
            }
        });

        fileMenu.getExitMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent actionEvent){
                System.out.println("Exiting...");
                FileView.super.dispose();
            }
        });

        compileMenu.getCompileMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Compiling ... " + filePath);
                compilerEvents.compilerFileEvent(filePath);
            }
        });
    }
}

