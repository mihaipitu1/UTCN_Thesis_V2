package commons;

import commons.ui.MainMenuBar;
import commons.ui.events.CompilerEvents;
import commons.ui.events.FileEvents;
import commons.ui.events.TutorialEvents;
import commons.ui.menu.CompileMenu;
import commons.ui.menu.FileMenu;
import commons.ui.menu.HelpMenu;
import commons.ui.menu.TutorialMenu;
import lombok.Data;

import java.awt.*;

@Data
public class CommonElements {
    //Events
    private FileEvents fileEvents;
    private TutorialEvents tutorialEvents;
    private CompilerEvents compilerEvents;

    //Menu Items
    private MainMenuBar mainMenuBar;
    private FileMenu fileMenu;
    private CompileMenu compileMenu;
    private TutorialMenu tutorialMenu;
    private HelpMenu helpMenu;

    public CommonElements() {
        initElements();
    }

    private void initElements() {
        fileEvents = new FileEvents();
        tutorialEvents = new TutorialEvents();
        compilerEvents = new CompilerEvents();

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
    }
}
