package utilities;

import java.io.IOException;

public class CompilerUtility {

    private static final String outputFileURL = "./output/result/suec.output";
    private static final String compilerFileURL = "./resources/compiler/suec.out";
    private FileUtility fileUtility;

    public CompilerUtility() {
        fileUtility = new FileUtility();
    }

    public String compileFile(String fileName) {
        ProcessBuilder builder = new ProcessBuilder(compilerFileURL,fileName);

        try {
            Process proc = builder.start();
            proc.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileUtility.loadFile(outputFileURL);
    }
}
