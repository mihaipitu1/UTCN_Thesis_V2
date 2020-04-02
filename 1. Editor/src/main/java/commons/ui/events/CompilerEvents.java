package commons.ui.events;

import utilities.CompilerUtility;

public class CompilerEvents {
    private CompilerUtility compilerUtility;

    public CompilerEvents() { compilerUtility = new CompilerUtility(); }

    public String compilerFileEvent(String fileName) {
        return compilerUtility.compileFile(fileName);
    }
}
