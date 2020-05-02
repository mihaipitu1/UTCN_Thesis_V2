package views;

import commons.logger.LoggerConfig;
import commons.ui.events.CompilerEvents;
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

public class DynamicTutorialView extends BaseView{
    private FileMenu fileMenu;
    private CompileMenu compileMenu;
    private TutorialMenu tutorialMenu;

    private TutorialEvents tutorialEvents;
    private FileEvents fileEvents;
    private CompilerEvents compilerEvents;

    private List<Tutorial> tutorials;

    private JPanel titlePanel;
    private JPanel descriptionPanel;
    private JPanel codePanel;
    private JPanel answerPanel;

    private JButton backButton;
    private JButton nextButton;
    private JButton compileButton;
    private JButton finishButton;

    private JLabel titleLbl;
    private JLabel descriptionLbl;
    private JLabel taskLbl;

    private JTextArea codeArea;
    private JTextArea answerArea;

    private Tutorial currentTutorial;

    public DynamicTutorialView(Tutorial t) {
        super();
        LoggerConfig.infoLog("Dynamic Tutorial View >> Tutorial {0} - {1} >> TutorialView() ",new Object[] {t.getId(),t.getTitle()});
        initComponents(t);
    }

    private void initComponents(Tutorial t) {
        this.setTitle("SueC - Tutorials : " + t.getTitle());
        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents()");
        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing current tutorial");
        currentTutorial = new Tutorial();
        currentTutorial = t;

        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing events >> tutorialEvents");
        tutorialEvents = new TutorialEvents();
        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing events >> fileEvents");
        fileEvents = new FileEvents();
        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing events >> compilerEvents");
        compilerEvents = new CompilerEvents();

        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing menus >> fileMenu");
        fileMenu = new FileMenu();
        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing menus >> compileMenu");
        compileMenu =  new CompileMenu();
        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing menus >> tutorialMenu");
        tutorialMenu = new TutorialMenu();

        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing tutorials list >> tutorials");
        tutorials = tutorialEvents.loadTutorialsEvent();

        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing JPanels >> titlePanel");
        titlePanel = new JPanel(new GridLayout(1,3,15,15));
        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing JPanels >> descriptionPanel");
        descriptionPanel = new JPanel(new GridLayout(1, 2,15,15));
        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing JPanels >> codePanel");
        codePanel = new JPanel(new GridLayout(1,2,5,5));
        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing JPanels >> answerPanel");
        answerPanel = new JPanel(new GridLayout(2,1));

        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing JButtons >> backButton");
        backButton = new JButton("Back");
        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing JButtons >> nextButton");
        nextButton = new JButton( "Next");
        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing JButtons >> finishButton");
        finishButton = new JButton("Finish");
        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing JButtons >> compileButton");
        compileButton = new JButton("Compile Tutorial");

        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing JLabel >> titleLbl");
        titleLbl = new JLabel(t.getTitle());
        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing JLabel >> descriptionLbl");
        descriptionLbl = new JLabel(t.getDescription());
        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing JLabel >> taskLbl");
        taskLbl = new JLabel(t.getTask());

        titlePanel.add(backButton);
        titlePanel.add(titleLbl);
        titlePanel.add(nextButton);

        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Adding visibility rules for nextButton/finishButton");
        nextButton.setVisible(false);

        if(t.getId() == 1) {
            backButton.setVisible(false);
        }

        if(t.getId() == tutorials.size()) {
            titlePanel.remove(nextButton);
            titlePanel.add(finishButton);
            finishButton.setVisible(false);
        }
        descriptionPanel.add(descriptionLbl);
        descriptionPanel.add(taskLbl);

        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing JTextArea >> codeArea");
        codeArea = new JTextArea();
        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Initializing JTextArea >> answerArea");
        answerArea = new JTextArea();
        answerArea.setEditable(false);

        answerPanel.add(compileButton);
        answerPanel.add(answerArea);

        codePanel.add(codeArea);
        codePanel.add(answerPanel);

        this.getMainPanel().setLayout(new GridLayout(3,1,20,20));

        this.getMainMenuBar().addMenuItemAt(fileMenu.getMenu(),0);
        this.getMainMenuBar().addMenuItemAt(compileMenu.getMenu(),1);
        this.getMainMenuBar().addMenuItemAt(tutorialMenu.getMenu(),2);

        this.getMainPanel().add(titlePanel);
        this.getMainPanel().add(descriptionPanel);
        this.getMainPanel().add(codePanel);

        LoggerConfig.infoLog("Dynamic Tutorial View >> initComponents() >> Set Action Events");
        setActionEvents();
    }

    private void setActionEvents() {
        LoggerConfig.infoLog("Dynamic Tutorial View >> setActionEvents()");
        fileMenu.getNewMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)  {
                LoggerConfig.infoLog("Dynamic Tutorial View >> setActionEvents() >> fileMenu.getNewMenuItem() Action Listener");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("New File...");

                int userSelection = fileChooser.showSaveDialog(new JFrame());

                if(userSelection == JFileChooser.APPROVE_OPTION) {
                    String path = fileEvents.newFileEvent(fileChooser.getSelectedFile().getAbsolutePath());
                    DynamicTutorialView.this.setVisible(false);
                    FileView fileView = new FileView(path);
                    fileView.setVisible(true);
                }
            }
        });

        fileMenu.getOpenMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog("Dynamic Tutorial View >> setActionEvents() >> fileMenu.getOpenMenuItem() Action Listener");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open File...");
                int userSelection = fileChooser.showOpenDialog(new JFrame());

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    DynamicTutorialView.this.setVisible(false);
                    FileView fileView = new FileView(path);
                    fileView.setVisible(true);
                }
            }
        });

        fileMenu.getExitMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent actionEvent){
                LoggerConfig.infoLog("Dynamic Tutorial View >> setActionEvents() >> fileMenu.getExitMenuItem() Action Listener");
                DynamicTutorialView.super.dispose();
            }
        });

        tutorialMenu.getTutorialListMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog("Dynamic Tutorial View >> setActionEvents() >> tutorialMenu.getTutorialListMenuItem() Action Listener");
                TutorialView tutorialView = new TutorialView();
                DynamicTutorialView.this.setVisible(false);
                tutorialView.setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog("Dynamic Tutorial View >> setActionEvents() >> backButton Action Listener");
                currentTutorial = tutorials.get(getTutorialIndex(currentTutorial) - 1);
                DynamicTutorialView newView = new DynamicTutorialView(currentTutorial);
                DynamicTutorialView.this.setVisible(false);
                newView.setVisible(true);
            }
        });

        nextButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    LoggerConfig.infoLog("Dynamic Tutorial View >> setActionEvents() >> nextButton Action Listener");
                    currentTutorial = tutorials.get(getTutorialIndex(currentTutorial) + 1);
                    DynamicTutorialView newView = new DynamicTutorialView(currentTutorial);
                    DynamicTutorialView.this.setVisible(false);
                    newView.setVisible(true);
                }
            });

        finishButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    LoggerConfig.infoLog("Dynamic Tutorial View >> setActionEvents() >> finishButton Action Listener");
                    JOptionPane.showMessageDialog(new JFrame(), "You did it!"); }
            });


        compileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog("Dynamic Tutorial View >> setActionEvents() >> compileButton Action Listener");
                String answer = tutorialEvents.compileTutorialEvent(codeArea.getText());
                answerArea.setText(answer);
                if(answer.equals(currentTutorial.getAnswer())) {
                    if(currentTutorial.getId() == tutorials.size())
                    {
                        finishButton.setVisible(true);
                    }
                    else {
                        nextButton.setVisible(true);
                    }
                }
                else {
                    answerArea.append("\n Wrong! Do it again!");
                    codeArea.setText("");
                }
            }
        });
    }

    private int getTutorialIndex(Tutorial tut) {
        int i;
        for(i = 0; i<tutorials.size();i++) {
            if(tut.isEqual(tutorials.get(i))) {
                return i;
            }
        }
        return -1;
    }
}
