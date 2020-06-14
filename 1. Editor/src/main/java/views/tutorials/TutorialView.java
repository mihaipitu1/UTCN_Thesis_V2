package views.tutorials;

import commons.logger.LoggerConfig;
import commons.ui.events.FileEvents;
import commons.ui.events.TutorialEvents;
import models.Tutorial;
import views.BaseView;
import views.FileView;
import views.guides.GuideView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TutorialView extends BaseView {
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

    private static final String TAG = "Tutorial View";

    public TutorialView() {
        super();
        LoggerConfig.infoLog(TAG,"TutorialView()");
        initComponents();
    }

    private void initComponents() {
        this.setTitle("SueC - Tutorials");
        LoggerConfig.infoLog(TAG,"initComponents()");
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing events >> tutorialEvents");
        tutorialEvents = new TutorialEvents();
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing events >> fileEvents");
        fileEvents = new FileEvents();

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing tutorials list >> tutorials");
        tutorials = tutorialEvents.loadTutorialsEvent();

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JPanels >> topPanel");
        topPanel = new JPanel();
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JPanels >> bottomPanel");
        bottomPanel = new JPanel();
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JPanels >> bottomLeftPanel");
        bottomLeftPanel = new JPanel();

        topPanel.setLayout(new GridLayout(2,1));
        bottomPanel.setLayout(new GridLayout(1,2));
        bottomLeftPanel.setLayout(new GridLayout(2,1));

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JLabels >> titleLbl");
        titleLbl = new JLabel("SueC - Tutorial List",SwingConstants.CENTER);
        titleLbl.setFont(this.getCommonElements().getTitleFont());
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JPanels >> subtitleLbl");
        subtitleLbl = new JLabel("Main Menu",SwingConstants.CENTER);
        subtitleLbl.setFont(this.getCommonElements().getSubtitleFont());

        topPanel.add(titleLbl);
        topPanel.add(subtitleLbl);

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JList >> tutorialList");
        tutorialList = new JList(tutorials.toArray());
        tutorialList.setFont(this.getCommonElements().getInfoFont());
        tutorialList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JButtons >> startTutorialButton");
        startTutorialButton = new JButton("Start Tutorials");
        startTutorialButton.setFont(this.getCommonElements().getInfoFont());

        bottomLeftPanel.add(startTutorialButton);

        bottomPanel.add(bottomLeftPanel);
        bottomPanel.add(tutorialList);

        this.getMainPanel().add(topPanel);
        this.getMainPanel().add(bottomPanel);

        LoggerConfig.infoLog(TAG,"initComponents() >> Set Action Events");
        setActionEvents();
    }

    private void setActionEvents() {
        LoggerConfig.infoLog(TAG,"setActionEvents()");
        this.getCommonElements().getFileMenu().getNewMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)  {
                LoggerConfig.infoLog(TAG,"setActionEvents() >> fileMenu.getNewMenuItem() Action Listener");
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

        this.getCommonElements().getFileMenu().getOpenMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG,"setActionEvents() >> fileMenu.getOpenMenuItem() Action Listener");
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

        this.getCommonElements().getTutorialMenu().getHelpListMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG, "setActionEvents() >> tutorialMenu.getHelpListMenuItem() Action Listener");
                GuideView guideView = new GuideView();
                TutorialView.this.setVisible(false);
                guideView.setVisible(true);
            }
        });

        this.getCommonElements().getFileMenu().getExitMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent actionEvent){
                LoggerConfig.infoLog(TAG,"setActionEvents() >> fileMenu.getExitMenuItem() Action Listener");
                TutorialView.super.dispose();
            }
        });

        tutorialList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                LoggerConfig.infoLog(TAG,"setActionEvents() >> tutorialList.addListSelectionListener() List Selection Listener");
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
                LoggerConfig.infoLog(TAG,"setActionEvents() >> startTutorialButton Action Listener");
                DynamicTutorialView dynamicTutorialView = new DynamicTutorialView(tutorials.get(0));
                TutorialView.this.setVisible(false);
                dynamicTutorialView.setVisible(true);
            }
        });
    }
}
