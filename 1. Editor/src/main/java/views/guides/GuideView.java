package views.guides;

import commons.logger.LoggerConfig;
import commons.ui.events.FileEvents;
import commons.ui.events.TutorialEvents;
import models.Guide;
import views.BaseView;
import views.FileView;
import views.tutorials.TutorialView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GuideView extends BaseView {
    private TutorialEvents tutorialEvents;
    private FileEvents fileEvents;

    private List<Guide> guideList;
    
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel bottomLeftPanel;
    
    private JButton startGuideButton;
    
    private JLabel titleLbl;
    private JLabel subtitleLbl;
    private JList guidesList;
    
    private static final String TAG = "Guide View";
    
    public GuideView() {
        super();
        LoggerConfig.infoLog(TAG, "GuideView()");
        initComponents();
    }
    
    private void initComponents() {
        this.setTitle("SueC - Guides");
        LoggerConfig.infoLog(TAG,"initComponents()");
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing events >> tutorialEvents");
        tutorialEvents = new TutorialEvents();
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing events >> fileEvents");
        fileEvents = new FileEvents();

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing tutorials list >> tutorials");
        guideList = tutorialEvents.loadGuidesEvent();

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
        titleLbl = new JLabel("SueC - Guide Book", SwingConstants.CENTER);
        titleLbl.setFont(this.getCommonElements().getTitleFont());
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JPanels >> subtitleLbl");
        subtitleLbl = new JLabel("Main Menu", SwingConstants.CENTER);
        subtitleLbl.setFont(this.getCommonElements().getSubtitleFont());

        topPanel.add(titleLbl);
        topPanel.add(subtitleLbl);

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JList >> tutorialList");
        guidesList = new JList(guideList.toArray());
        guidesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JButtons >> startTutorialButton");
        startGuideButton = new JButton("Start Tutorials");

        bottomLeftPanel.add(startGuideButton);

        bottomPanel.add(bottomLeftPanel);
        bottomPanel.add(guidesList);

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
                    GuideView.this.setVisible(false);
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
                    GuideView.this.setVisible(false);
                    FileView fileView = new FileView(path);
                    fileView.setVisible(true);
                }
            }
        });

        this.getCommonElements().getFileMenu().getExitMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent actionEvent){
                LoggerConfig.infoLog(TAG,"setActionEvents() >> fileMenu.getExitMenuItem() Action Listener");
                GuideView.super.dispose();
            }
        });

        this.getCommonElements().getTutorialMenu().getTutorialListMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG, "setActionEvents() >> tutorialMenu.getHelpListMenuItem() Action Listener");
                TutorialView tutorialView = new TutorialView();
                GuideView.this.setVisible(false);
                tutorialView.setVisible(true);
            }
        });

        this.getCommonElements().getTutorialMenu().getHelpListMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG, "setActionEvents() >> tutorialMenu.getHelpListMenuItem() Action Listener");
                GuideView guideView = new GuideView();
                GuideView.this.setVisible(false);
                guideView.setVisible(true);
            }
        });

        guidesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                LoggerConfig.infoLog(TAG,"setActionEvents() >> tutorialList.addListSelectionListener() List Selection Listener");
                if(!listSelectionEvent.getValueIsAdjusting()){
                    DynamicGuideView dynamicGuideView = new DynamicGuideView((Guide)guidesList.getSelectedValue());
                    GuideView.this.setVisible(false);
                    dynamicGuideView.setVisible(true);
                }
            }
        });

        startGuideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG,"setActionEvents() >> startTutorialButton Action Listener");
                DynamicGuideView dynamicGuideView = new DynamicGuideView(guideList.get(0));
                GuideView.this.setVisible(false);
                dynamicGuideView.setVisible(true);
            }
        });
    }
}
