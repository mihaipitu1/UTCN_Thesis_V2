package commons;

import commons.ui.MainMenuBar;
import commons.ui.menu.CompileMenu;
import commons.ui.menu.FileMenu;
import commons.ui.menu.HelpMenu;
import commons.ui.menu.TutorialMenu;
import lombok.Data;

import java.awt.*;

@Data
public class CommonElements {
    private MainMenuBar mainMenuBar;
    private FileMenu fileMenu;
    private CompileMenu compileMenu;
    private TutorialMenu tutorialMenu;
    private HelpMenu helpMenu;

    private Font titleFont;
    private Font subtitleFont;
    private Font infoFont;

    public CommonElements() {
        initElements();
    }

    private void initElements() {
        mainMenuBar = new MainMenuBar();
        fileMenu = new FileMenu();
        compileMenu = new CompileMenu();
        tutorialMenu = new TutorialMenu();
        helpMenu = new HelpMenu();

        mainMenuBar.getMenuBar().setSize(new Dimension(600,20));

        mainMenuBar.addMenuItem(fileMenu.getMenu());
        mainMenuBar.addMenuItem(compileMenu.getMenu());
        mainMenuBar.addMenuItem(tutorialMenu.getMenu());
        mainMenuBar.addMenuItem(helpMenu.getMenu());

        titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 36);
        subtitleFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        infoFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);
    }
}
