package lambdaland;

import com.sun.tools.javac.parser.Scanner;
import com.sun.tools.javac.parser.ScannerFactory;
import com.sun.tools.javac.util.Context;
import lambdaland.Variation.ProgramElement;
import lambdaland.Variation.VJavaToken;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

/**
 * Created by rikkigibson on 4/11/16.
 */
public class VariationCompiler {
    public static void compile(PrintStream outStream, String input, Map<String, String> choices) {

        Context context = new Context();
        ScannerFactory scannerFactory = ScannerFactory.instance(context);
        Scanner scanner = scannerFactory.newScanner(input, false);

        List<ProgramElement> program = new VariationParser(scanner).parseProgram();

        List<VJavaToken> reducedTokens = VariationReducer.reduceProgram(program, choices);
        PrettyPrinter myPrinter = new PrettyPrinter(outStream);
        myPrinter.print(reducedTokens);
    }
}
