package views;


import commons.CommonElements;

import javax.swing.*;
import java.awt.*;

public class BaseView extends JFrame {

    private JPanel mainPanel;
    private CommonElements commonElements;

    public BaseView() {
        super();
        initComponents();
    }

    private void initComponents() {
        this.setSize(600,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        commonElements = new CommonElements();

        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2,1,10,10));

        this.add(commonElements.getMainMenuBar().getMenuBar(), BorderLayout.NORTH);
        this.add(mainPanel,BorderLayout.CENTER);
    }

    public CommonElements getCommonElements() {
        return this.commonElements;
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }
}
