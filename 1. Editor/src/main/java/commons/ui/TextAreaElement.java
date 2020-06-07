package commons.ui;

import javax.swing.*;

public class TextAreaElement extends JTextArea {
    public TextAreaElement(String content) {
        super();
        initElement(content);
    }

    private void initElement(String content) {
        this.setText(content);
        this.setWrapStyleWord(false);
        this.setLineWrap(true);
        this.setOpaque(false);
        this.setEditable(false);
    }
}
