package commons.ui.menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpMenu {
    private JMenu menu;
    private JMenuItem aboutMenuItem;

    public HelpMenu() {
        initComponents();
    }

    private void initComponents() {
        menu = new JMenu("Help");
        aboutMenuItem = new JMenuItem("About");

        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(new JFrame(), "This is the about menu.");
            }
        });

        menu.add(aboutMenuItem);
    }

    public JMenu getMenu() {
        return this.menu;
    }

    public JMenuItem getAboutMenuItem() {
        return this.aboutMenuItem;
    }
}
