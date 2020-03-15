package utilities;

import java.io.IOException;

public class CompilerUtility {

    private static final String outputFileURL = "./src/compiler/suec.output";
    private static final String compilerFileURL = "./src/compiler/suec.out";

    public void compileFile(String fileName) {
        ProcessBuilder builder = new ProcessBuilder(compilerFileURL,fileName);

        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
