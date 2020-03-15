package views;



import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import commons.ui.MainMenuBar;
import commons.ui.menu.HelpMenu;

public class BaseView extends JFrame {
    private MainMenuBar menuBar;
    private JPanel mainPanel;
    private HelpMenu helpMenu;

    public BaseView() {
        super();
        initComponents();
    }

    private void initComponents() {
        this.setSize(600,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);


        menuBar = new MainMenuBar();
        helpMenu = new HelpMenu();

        menuBar.addMenuItem(helpMenu.getMenu());

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2,1,2,2));
        this.menuBar.getMenuBar().setSize(new Dimension(600,20));

        this.add(menuBar.getMenuBar(), BorderLayout.NORTH);
        this.add(mainPanel,BorderLayout.CENTER);
    }

    public MainMenuBar getMainMenuBar() {
        return this.menuBar;
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }

}
