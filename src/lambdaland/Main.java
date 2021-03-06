package lambdaland;

import com.sun.tools.javac.parser.*;
import com.sun.tools.javac.tree.Pretty;
import com.sun.tools.javac.util.Context;
import lambdaland.Variation.ProgramElement;
import lambdaland.Variation.VJavaToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Usage: variation-parser <filename>");
            System.exit(1);
        }

        List<String> contents = Files.readAllLines(Paths.get(args[0]));
        StringBuilder builder = new StringBuilder();
        for (String line : contents) {
            builder.append(line);
        }
        String source = builder.toString();

        Map<String, String> choices = new HashMap<>();
//        choices.put("X", "r");
        choices.put("Y", "u");

        VariationCompiler.compile(System.out, source, choices);
    }
}
