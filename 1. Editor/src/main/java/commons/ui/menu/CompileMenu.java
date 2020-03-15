package commons.ui.menu;

import javax.swing.*;

public class CompileMenu {
    private JMenu menu;

    private JMenuItem compileMenuItem;

    public CompileMenu() {
        initComponents();
    }

    private void initComponents() {
        menu = new JMenu("Compile");

        compileMenuItem = new JMenuItem("Compile File...");

        menu.add(compileMenuItem);
    }

    public JMenu getMenu() {
        return this.menu;
    }

    public JMenuItem getCompileMenuItem() {
        return this.compileMenuItem;
    }
}
