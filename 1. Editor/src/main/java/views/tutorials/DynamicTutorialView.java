package views.tutorials;

import commons.logger.LoggerConfig;
import commons.ui.TextAreaElement;
import commons.ui.events.CompilerEvents;
import commons.ui.events.FileEvents;
import commons.ui.events.TutorialEvents;
import models.Tutorial;
import views.BaseView;
import views.FileView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DynamicTutorialView extends BaseView {
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

    private TextAreaElement titleArea;
    private TextAreaElement descriptionArea;
    private TextAreaElement taskArea;

    private JTextArea codeArea;
    private JTextArea answerArea;

    private Tutorial currentTutorial;

    private static final String TAG = "Dynamic Tutorial View";

    public DynamicTutorialView(Tutorial t) {
        super();
        LoggerConfig.infoLog(TAG,"Tutorial {0} - {1} >> TutorialView() ",new Object[] {t.getId(), t.getTitle()});
        initComponents(t);
    }

    private void initComponents(Tutorial t) {
        this.setTitle("SueC - Tutorials : " + t.getTitle());
        LoggerConfig.infoLog(TAG,"initComponents()");
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing current tutorial");
        currentTutorial = new Tutorial();
        currentTutorial = t;

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing events >> tutorialEvents");
        tutorialEvents = new TutorialEvents();
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing events >> fileEvents");
        fileEvents = new FileEvents();
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing events >> compilerEvents");
        compilerEvents = new CompilerEvents();

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing tutorials list >> tutorials");
        tutorials = tutorialEvents.loadTutorialsEvent();

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JPanels >> titlePanel");
        titlePanel = new JPanel(new GridLayout(1,3,15,15));
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JPanels >> descriptionPanel");
        descriptionPanel = new JPanel(new GridLayout(1, 2,15,15));
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JPanels >> codePanel");
        codePanel = new JPanel(new GridLayout(1,2,5,5));
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JPanels >> answerPanel");
        answerPanel = new JPanel(new GridLayout(2,1));

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JButtons >> backButton");
        backButton = new JButton("Back");
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JButtons >> nextButton");
        nextButton = new JButton( "Next");
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JButtons >> finishButton");
        finishButton = new JButton("Finish");
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JButtons >> compileButton");
        compileButton = new JButton("Compile Tutorial");

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JLabel >> titleLbl");
        titleArea = new TextAreaElement(t.getTitle());
        titleArea.setFont(this.getCommonElements().getSubtitleFont());
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JLabel >> descriptionLbl");
        descriptionArea = new TextAreaElement(t.getDescription());
        descriptionArea.setFont(this.getCommonElements().getInfoFont());
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JLabel >> taskLbl");
        taskArea = new TextAreaElement(t.getTask());
        taskArea.setFont(this.getCommonElements().getInfoFont());

        titlePanel.add(backButton);
        titlePanel.add(titleArea);
        titlePanel.add(nextButton);

        LoggerConfig.infoLog(TAG,"initComponents() >> Adding visibility rules for nextButton/finishButton");
        nextButton.setVisible(false);

        if(t.getId() == 1) {
            backButton.setVisible(false);
        }

        if(t.getId() == tutorials.size()) {
            titlePanel.remove(nextButton);
            titlePanel.add(finishButton);
            finishButton.setVisible(false);
        }
        descriptionPanel.add(descriptionArea);
        descriptionPanel.add(taskArea);

        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JTextArea >> codeArea");
        codeArea = new JTextArea();
        LoggerConfig.infoLog(TAG,"initComponents() >> Initializing JTextArea >> answerArea");
        answerArea = new JTextArea();
        answerArea.setEditable(false);

        answerPanel.add(compileButton);
        answerPanel.add(answerArea);

        codePanel.add(codeArea);
        codePanel.add(answerPanel);

        this.getMainPanel().setLayout(new GridLayout(3,1,20,20));

        this.getMainPanel().add(titlePanel);
        this.getMainPanel().add(descriptionPanel);
        this.getMainPanel().add(codePanel);

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
                    DynamicTutorialView.this.setVisible(false);
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
                    DynamicTutorialView.this.setVisible(false);
                    FileView fileView = new FileView(path);
                    fileView.setVisible(true);
                }
            }
        });

        this.getCommonElements().getFileMenu().getExitMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent actionEvent){
                LoggerConfig.infoLog(TAG,"setActionEvents() >> fileMenu.getExitMenuItem() Action Listener");
                DynamicTutorialView.super.dispose();
            }
        });

        this.getCommonElements().getTutorialMenu().getTutorialListMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG,"setActionEvents() >> tutorialMenu.getTutorialListMenuItem() Action Listener");
                TutorialView tutorialView = new TutorialView();
                DynamicTutorialView.this.setVisible(false);
                tutorialView.setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG,"setActionEvents() >> backButton Action Listener");
                currentTutorial = tutorials.get(getTutorialIndex(currentTutorial) - 1);
                DynamicTutorialView newView = new DynamicTutorialView(currentTutorial);
                DynamicTutorialView.this.setVisible(false);
                newView.setVisible(true);
            }
        });

        nextButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    LoggerConfig.infoLog(TAG,"setActionEvents() >> nextButton Action Listener");
                    currentTutorial = tutorials.get(getTutorialIndex(currentTutorial) + 1);
                    DynamicTutorialView newView = new DynamicTutorialView(currentTutorial);
                    DynamicTutorialView.this.setVisible(false);
                    newView.setVisible(true);
                }
            });

        finishButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    LoggerConfig.infoLog(TAG,"setActionEvents() >> finishButton Action Listener");
                    JOptionPane.showMessageDialog(new JFrame(), "You did it!");
                    TutorialView tutorialView = new TutorialView();
                    DynamicTutorialView.this.setVisible(false);
                    tutorialView.setVisible(true);
                }
            });


        compileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoggerConfig.infoLog(TAG,"setActionEvents() >> compileButton Action Listener");
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
