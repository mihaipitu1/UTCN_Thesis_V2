package views;

import commons.logger.LoggerConfig;
import commons.ui.events.FileEvents;
import commons.ui.events.TutorialEvents;
import commons.ui.menu.CompileMenu;
import commons.ui.menu.FileMenu;
import commons.ui.menu.TutorialMenu;
import models.Tutorial;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class TutorialView extends BaseView {
    private FileMenu fileMenu;
    private CompileMenu compileMenu;
    private TutorialMenu tutorialMenu;

    private TutorialEvents tutorialEvents;
    private FileEvents fileEvents;

    private List<Tutorial> tutorials;

    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel bottomLeftPanel;

    private JButton startTutorialButton;

    private JLabel titleLbl;
    private JLabel subtitleLbl;
    private JList tutorialList;

    public TutorialView() {
        super();
        LoggerConfig.infoLog("Tutorial View >> TutorialView()");
        initComponents();
    }

    private void initComponents() {
        this.setTitle("SueC - Tutorials");
        LoggerConfig.infoLog("Tutorial View >> initComponents()");
        LoggerConfig.infoLog("Tutorial View >> initComponents() >> Initializing events >> tutorialEvents");
        tutorialEvents = new TutorialEvents();
        LoggerConfig.infoLog("Tutorial View >> initComponents() >> Initializing events >> fileEvents");
        fileEvents = new FileEvents();

        LoggerConfig.infoLog("Tutorial View >> initComponents() >> Initializing menus >> fileMenu");
        fileMenu = new FileMenu();
        LoggerConfig.infoLog("Tutorial View >> initComponents() >> Initializing menus >> compileMenu");
        compileMenu =  new CompileMenu();
        LoggerConfig.infoLog("Tutorial View >> initComponents() >> Initializing menus >> tutorialMenu");
        tutorialMenu = new TutorialMenu();

        LoggerConfig.infoLog("Tutorial View >> initComponents() >> Initializing tutorials list >> tutorials");
        tutorials = tutorialEvents.loadTutorialsEvent();

        this.getMainMenuBar().addMenuItemAt(fileMenu.getMenu(),0);
        this.getMainMenuBar().addMenuItemAt(compileMenu.getMenu(),1);
        this.getMainMenuBar().addMenuItemAt(tutorialMenu.getMenu(),2);

        LoggerConfig.infoLog("Tutorial View >> initComponents() >> Initializing JPanels >> topPanel");
        topPanel = new JPanel();
        LoggerConfig.infoLog("Tutorial View >> initComponents() >> Initializing JPanels >> bottomPanel");
        bottomPanel = new JPanel();
        LoggerConfig.infoLog("Tutorial View >> initComponents() >> Initializing JPanels >> bottomLeftPanel");
        bottomLeftPanel = new JPanel();

        topPanel.setLayout(new GridLayout(1,2));
        bottomPanel.setLayout(new GridLayout(1,2));
        bottomLeftPanel.setLayout(new GridLayout(2,1));

        LoggerConfig.infoLog("Tutorial View >> initComponents() >> Initializing JLabels >> titleLbl");
        titleLbl = new JLabel("Title Label");
        LoggerConfig.infoLog("Tutorial View >> initComponents() >> Initializing JPanels >> subtitleLbl");
        subtitleLbl = new JLabel("SubTitle Label");

        topPanel.add(titleLbl);
        topPanel.add(subtitleLbl);

        LoggerConfig.infoLog("Tutorial View >> initComponents() >> Initializing JList >> tutorialList");
        tutorialList = new JList(tutorials.toArray());
        tutorialList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        LoggerConfig.infoLog("Tutorial View >> initComponents() >> Initializing JButtons >> startTutorialButton");
        startTutorialButton = new JButton("Start Tutorials");

        bottomLeftPanel.add(titleLbl);
        bottomLeftPanel.add(startTutorialButton);

        bottomPanel.add(bottomLeftPanel);
        bottomPanel.add(tutorialList);

        this.getMainPanel().add(topPanel);
        this.getMainPanel().add(bottomPanel);

        LoggerConfig.infoLog("Tutorial View >> initComponents() >> Set Action Events");
        setActionEvents();
    }

    private void setActionEvents() {
        LoggerConfig.infoLog("Tutorial View >> setActionEvents()");
        fileMenu.getNewMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)  {
                LoggerConfig.infoLog("Tutorial View >> setActionEvents() >> fileMenu.getNewMenuItem() Action Listener");
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
                LoggerConfig.infoLog("Tutorial View >> setActionEvents() >> fileMenu.getOpenMenuItem() Action Listener");
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
                LoggerConfig.infoLog("Tutorial View >> setActionEvents() >> fileMenu.getExitMenuItem() Action Listener");
                TutorialView.super.dispose();
            }
        });

        tutorialList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                LoggerConfig.infoLog("Tutorial View >> setActionEvents() >> tutorialList.addListSelectionListener() List Selection Listener");
                if(!listSelectionEvent.getValueIsAdjusting()){
                    DynamicTutorialView dynamicTutorialView = new DynamicTutorialView((Tutorial)tutorialList.getSelectedValue());
                    TutorialView.this.setVisible(false);
                    dynamicTutorialView.setVisible(true);
                }
            }
        });

        startTutorialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog("Tutorial View >> setActionEvents() >> startTutorialButton Action Listener");
                DynamicTutorialView dynamicTutorialView = new DynamicTutorialView(tutorials.get(0));
                TutorialView.this.setVisible(false);
                dynamicTutorialView.setVisible(true);
            }
        });
    }
}
