package commons.ui.events;

import commons.logger.LoggerConfig;
import utilities.CompilerUtility;

public class CompilerEvents {
    private CompilerUtility compilerUtility;
    private static final String TAG = "Compiler Events";

    public CompilerEvents() {
        LoggerConfig.infoLog(TAG, "CompilerEvents() - initializing with the required utilities");
        compilerUtility = new CompilerUtility(); }

    public String compilerFileEvent(String fileName) {
        LoggerConfig.infoLog(TAG,"compilerFileEvent() - fileName: {0}", new Object[] {fileName});
        return compilerUtility.compileFile(fileName);
    }
}
