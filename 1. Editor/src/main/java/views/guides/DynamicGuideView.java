package views.guides;

import commons.logger.LoggerConfig;
import commons.ui.events.CompilerEvents;
import commons.ui.events.FileEvents;
import commons.ui.events.TutorialEvents;
import commons.ui.menu.CompileMenu;
import commons.ui.menu.FileMenu;
import commons.ui.menu.TutorialMenu;
import models.Guide;
import views.BaseView;
import views.FileView;
import views.tutorials.DynamicTutorialView;
import views.tutorials.TutorialView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DynamicGuideView extends BaseView {
    private FileMenu fileMenu;
    private CompileMenu compileMenu;
    private TutorialMenu tutorialMenu;

    private TutorialEvents tutorialEvents;
    private FileEvents fileEvents;
    private CompilerEvents compilerEvents;

    private List<Guide> guides;

    private JPanel titlePanel;
    private JPanel descriptionPanel;

    private JButton backButton;
    private JButton nextButton;
    private JButton finishButton;

    private JLabel titleLbl;
    private JLabel descriptionLbl;

    private JTextArea codeArea;

    private Guide currentGuide;

    private static final String TAG = "Dynamic Guide View";

    public DynamicGuideView(Guide g) {
        super();
        LoggerConfig.infoLog(TAG,"Guide {0} - {1} >> GuideView() ",new Object[] {g.getId(),g.getTitle()});
        initComponents(g);
    }

    private void initComponents(Guide g) {
        this.setTitle("SueC - Tutorials : " + g.getTitle());
        LoggerConfig.infoLog(TAG,"initComponents()");
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing current tutorial");
        currentGuide = new Guide();
        currentGuide = g;

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing events >> tutorialEvents");
        tutorialEvents = new TutorialEvents();
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing events >> fileEvents");
        fileEvents = new FileEvents();
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing events >> compilerEvents");
        compilerEvents = new CompilerEvents();

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing menus >> fileMenu");
        fileMenu = new FileMenu();
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing menus >> compileMenu");
        compileMenu =  new CompileMenu();
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing menus >> tutorialMenu");
        tutorialMenu = new TutorialMenu();

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing tutorials list >> tutorials");
        guides = tutorialEvents.loadGuidesEvent();

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JPanels >> titlePanel");
        titlePanel = new JPanel(new GridLayout(1,3,15,15));
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JPanels >> descriptionPanel");
        descriptionPanel = new JPanel(new GridLayout(1, 2,15,15));
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JPanels >> codePanel");

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JButtons >> backButton");
        backButton = new JButton("Back");
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JButtons >> nextButton");
        nextButton = new JButton( "Next");
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JButtons >> finishButton");
        finishButton = new JButton("Finish");
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JButtons >> compileButton");

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JLabel >> titleLbl");
        titleLbl = new JLabel(g.getTitle());
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JLabel >> descriptionLbl");
        descriptionLbl = new JLabel(g.getDescription());

        titlePanel.add(backButton);
        titlePanel.add(titleLbl);
        titlePanel.add(nextButton);

        if(g.getId() == 1) {
            backButton.setVisible(false);
        }

        if(g.getId() == guides.size()) {
            titlePanel.remove(nextButton);
            titlePanel.add(finishButton);
        }

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JTextArea >> codeArea");
        codeArea = new JTextArea();
        codeArea.setText(g.getExample());
        codeArea.setEditable(false);

        descriptionPanel.add(descriptionLbl);
        descriptionPanel.add(codeArea);
        
        this.getMainPanel().setLayout(new GridLayout(3,1,20,20));

        this.getMainMenuBar().addMenuItemAt(fileMenu.getMenu(),0);
        this.getMainMenuBar().addMenuItemAt(compileMenu.getMenu(),1);
        this.getMainMenuBar().addMenuItemAt(tutorialMenu.getMenu(),2);

        this.getMainPanel().add(titlePanel);
        this.getMainPanel().add(descriptionPanel);

        LoggerConfig.infoLog(TAG,"initComponents() >> Set Action Events");
        setActionEvents();
    }

    private void setActionEvents() {
        LoggerConfig.infoLog(TAG, "setActionEvents()");
        fileMenu.getNewMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG, "setActionEvents() >> fileMenu.getNewMenuItem() Action Listener");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("New File...");

                int userSelection = fileChooser.showSaveDialog(new JFrame());

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String path = fileEvents.newFileEvent(fileChooser.getSelectedFile().getAbsolutePath());
                    DynamicGuideView.this.setVisible(false);
                    FileView fileView = new FileView(path);
                    fileView.setVisible(true);
                }
            }
        });

        fileMenu.getOpenMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG, "setActionEvents() >> fileMenu.getOpenMenuItem() Action Listener");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open File...");
                int userSelection = fileChooser.showOpenDialog(new JFrame());

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    DynamicGuideView.this.setVisible(false);
                    FileView fileView = new FileView(path);
                    fileView.setVisible(true);
                }
            }
        });

        fileMenu.getExitMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG, "setActionEvents() >> fileMenu.getExitMenuItem() Action Listener");
                DynamicGuideView.super.dispose();
            }
        });

        tutorialMenu.getTutorialListMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG, "setActionEvents() >> tutorialMenu.getTutorialListMenuItem() Action Listener");
                TutorialView tutorialView = new TutorialView();
                DynamicGuideView.this.setVisible(false);
                tutorialView.setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG, "setActionEvents() >> backButton Action Listener");
                currentGuide = guides.get(getGuideIndex(currentGuide) - 1);
                DynamicGuideView newView = new DynamicGuideView(currentGuide);
                DynamicGuideView.this.setVisible(false);
                newView.setVisible(true);
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG, "setActionEvents() >> nextButton Action Listener");
                currentGuide = guides.get(getGuideIndex(currentGuide) + 1);
                DynamicGuideView newView = new DynamicGuideView(currentGuide);
                DynamicGuideView.this.setVisible(false);
                newView.setVisible(true);
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG, "setActionEvents() >> finishButton Action Listener");
                JOptionPane.showMessageDialog(new JFrame(), "You did it!");
            }
        });
    }

    private int getGuideIndex(Guide g) {
        int i;
        for(i = 0; i<guides.size();i++) {
            if(g.isEqual(guides.get(i))) {
                return i;
            }
        }
        return -1;
    }
}
