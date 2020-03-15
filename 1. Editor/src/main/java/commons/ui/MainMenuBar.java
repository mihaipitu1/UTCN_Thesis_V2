package commons.ui;

import utilities.FileUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuBar {
    private JMenuBar menuBar;

    public MainMenuBar() {
        initComponents();
    }

    private void initComponents(){
        menuBar = new JMenuBar();
    }

    public JMenuBar getMenuBar(){
        return this.menuBar;
    }

    public void addMenuItem(JMenu menu) {
        this.menuBar.add(menu);
    }

    public void addMenuItemAt(JMenu menu, int index) {
        this.menuBar.add(menu,index);
    }
}
