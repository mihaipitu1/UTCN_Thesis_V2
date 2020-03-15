package commons.ui.menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileMenu {
    private JMenu menu;
    private JMenuItem newMenuItem;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem exitMenuItem;

    public FileMenu() {
        initComponents();
    }

    private void initComponents() {
        menu = new JMenu("File");

        newMenuItem = new JMenuItem("New");
        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save");
        exitMenuItem = new JMenuItem("Exit");

        menu.add(newMenuItem);
        menu.add(openMenuItem);
        menu.add(saveMenuItem);
        menu.add(exitMenuItem);
    }

    public JMenu getMenu() {
        return this.menu;
    }

    public JMenuItem getNewMenuItem() {
        return this.newMenuItem;
    }

    public JMenuItem getOpenMenuItem() {
        return this.openMenuItem;
    }

    public JMenuItem getSaveMenuItem() {
        return this.saveMenuItem;
    }

    public JMenuItem getExitMenuItem() {
        return this.exitMenuItem;
    }
}
