package views;

import commons.ui.MenuBar;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BaseView extends JFrame {
    private MenuBar menuBar;
    private JPanel mainPanel;

    public BaseView() {
        super();
        initComponents();
    }

    private void initComponents() {
        this.setSize(600,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);


        menuBar = new MenuBar();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2,1,2,2));
        this.menuBar.getMenuBar().setSize(new Dimension(600,20));

        this.add(menuBar.getMenuBar(), BorderLayout.NORTH);
        this.add(mainPanel,BorderLayout.CENTER);
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }

}
