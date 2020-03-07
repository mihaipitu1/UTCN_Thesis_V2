package commons.ui;

import utilities.FileUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar {
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu compileMenu;
    private JMenu tutorialMenu;
    private JMenu helpMenu;
    private JMenuItem newFileMenuItem;
    private JMenuItem openFileMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem compileMenuItem;
    private JMenuItem tutorialsListMenuItem;
    private JMenuItem helpListMenuItem;
    private JMenuItem aboutMenuItem;
    private JFileChooser fileChooser;
    private FileUtility fileUtility;

    public MenuBar() {
        initComponents();
    }

    private void initComponents(){
        fileUtility = new FileUtility();

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        compileMenu = new JMenu("Compile");
        tutorialMenu = new JMenu("Tutorial");
        helpMenu = new JMenu("Help");
        newFileMenuItem = new JMenuItem("New...");
        openFileMenuItem = new JMenuItem("Open...");
        exitMenuItem = new JMenuItem("Exit");
        compileMenuItem = new JMenuItem("Compile File...");
        tutorialsListMenuItem = new JMenuItem("Tutorial List");
        helpListMenuItem = new JMenuItem("Help List");
        aboutMenuItem = new JMenuItem("About");

        fileMenu.add(newFileMenuItem);
        fileMenu.add(openFileMenuItem);
        fileMenu.add(exitMenuItem);
        compileMenu.add(compileMenuItem);
        tutorialMenu.add(tutorialsListMenuItem);
        tutorialMenu.add(helpListMenuItem);
        helpMenu.add(aboutMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(compileMenu);
        menuBar.add(tutorialMenu);
        menuBar.add(helpMenu);

        fileChooser = new JFileChooser();

        newFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileChooser.setDialogTitle("Select a place to create a new file");
                JFrame newFileFrame = new JFrame();

                int userSelect = fileChooser.showOpenDialog(newFileFrame);

                if(userSelect == JFileChooser.APPROVE_OPTION) {
                    fileUtility.createFile(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

    }

    public JMenuBar getMenuBar(){
        return this.menuBar;
    }

}
