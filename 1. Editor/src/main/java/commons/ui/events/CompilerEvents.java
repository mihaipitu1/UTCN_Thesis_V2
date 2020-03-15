package commons.ui.events;

import utilities.CompilerUtility;

public class CompilerEvents {
    private CompilerUtility compilerUtility;

    public CompilerEvents() { compilerUtility = new CompilerUtility(); }

    public void compilerFileEvent(String fileName) {
        compilerUtility.compileFile(fileName);
    }
}
