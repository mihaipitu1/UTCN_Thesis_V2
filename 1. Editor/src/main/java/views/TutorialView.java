package views;

import commons.ui.events.TutorialEvents;
import commons.ui.menu.CompileMenu;
import commons.ui.menu.FileMenu;
import commons.ui.menu.TutorialMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TutorialView extends BaseView {
    private FileMenu fileMenu;
    private CompileMenu compileMenu;
    private TutorialMenu tutorialMenu;

    private TutorialEvents tutorialEvents;

    public TutorialView() {
        super();
        initComponents();
    }

    private void initComponents() {
        this.setTitle("SueC - Tutorials");
        tutorialEvents = new TutorialEvents();

        fileMenu = new FileMenu();
        compileMenu new CompileMenu();
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
                    TutorialView.this.setVisible(false);
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
                    TutorialView.this.setVisible(false);
                    FileView fileView = new FileView(path);
                    fileView.setVisible(true);
                }
            }
        });

        fileMenu.getExitMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent actionEvent){
                System.out.println("Exiting...");
                TutorialView.super.dispose();
            }
        });
    }
}
