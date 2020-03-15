package commons.ui.menu;


import javax.swing.*;

public class TutorialMenu {
    private JMenu menu;
    private JMenuItem tutorialListMenuItem;
    private JMenuItem helpListMenuItem;

    public TutorialMenu() {
        initComponents();
    }

    private void initComponents() {
        menu = new JMenu("Tutorial");

       tutorialListMenuItem = new JMenuItem("Tutorial List...");
       helpListMenuItem = new JMenuItem("Help List...");

       menu.add(tutorialListMenuItem);
       menu.add(helpListMenuItem);
    }

    public JMenu getMenu() {
        return this.menu;
    }

    public JMenuItem getTutorialListMenuItem() {
        return this.tutorialListMenuItem;
    }

    public JMenuItem getHelpListMenuItem() {
        return this.helpListMenuItem;
    }
}
