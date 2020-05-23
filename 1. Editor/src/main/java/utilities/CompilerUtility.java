package utilities;

import commons.logger.LoggerConfig;

import java.io.IOException;

public class CompilerUtility {

    private static final String outputFileURL = "./output/result/suec.output";
    private static final String compilerFileURL = "./resources/compiler/suec.out";
    private static final String TAG = "Compiler Utility";
    private FileUtility fileUtility;

    public CompilerUtility() {
        LoggerConfig.infoLog(TAG,"CompilerUtility()");
        fileUtility = new FileUtility();
    }

    public String compileFile(String fileName) {
        LoggerConfig.infoLog(TAG,"compileFile() - fileName: {0}",new Object[]{fileName});
        ProcessBuilder builder = new ProcessBuilder(compilerFileURL,fileName);

        try {
            LoggerConfig.infoLog(TAG,"Starting to compile file {0} with compilerFile - {1}", new Object[] {fileName,compilerFileURL});
            Process proc = builder.start();
            proc.waitFor();
            proc.destroy();
        } catch (IOException | InterruptedException e) {
            LoggerConfig.errorLog(TAG, "compileFile() >> Error: " + e.getStackTrace());
        }
        LoggerConfig.infoLog(TAG, "Returning output content from {0}", new Object[]{outputFileURL});
        return fileUtility.loadFile(outputFileURL);
    }
}
