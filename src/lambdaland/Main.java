package lambdaland;

import com.sun.tools.javac.parser.*;
import com.sun.tools.javac.util.Context;
import lambdaland.Variation.ProgramElement;

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

        Context context = new Context();
        ScannerFactory scannerFactory = ScannerFactory.instance(context);
        Scanner scanner = scannerFactory.newScanner(source, false);

        List<ProgramElement> program = new VariationParser(scanner).parseProgram();

        Map<String, String> choices = new HashMap<>();
        choices.put("X", "r");
        choices.put("Y", "u");

        List<Tokens.Token> reducedTokens = VariationReducer.reduceProgram(program, choices);
        PrettyPrinter.print(reducedTokens);
    }
}
