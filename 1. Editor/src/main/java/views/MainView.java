package views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends BaseView {

    private JTextArea codeArea;
    private JTextArea resultArea;

    public MainView() {
        super();
        initComponents();
    }

    private void initComponents() {
        this.setTitle("Main View");

    }
}

