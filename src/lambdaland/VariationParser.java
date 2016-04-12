package lambdaland;

import com.sun.tools.javac.parser.Scanner;
import com.sun.tools.javac.parser.Tokens;
import lambdaland.Variation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rikkigibson on 4/4/16.
 */
public class VariationParser {
    private final Scanner scanner;

    public VariationParser(Scanner scanner) {
        this.scanner = scanner;
    }

//    void scan(Scanner scanner) {
        // A variational program is defined as follows:
        /*
            public class Main {
                public static void main(String[] args) {
                    dimension X
                        alternative l
                            System.out.println("You chose left");
                        end
                        alternative r
                            System.out.println("You chose right");
                        end
                    end
                }
            }

            Grammar:
            program : javatokens program | dimension program | epsilon
            javatokens : javatoken javatokens | epsilon
            javatoken : (any of the tokens defined in java)
            dimension : DIMENSION identifier branches END
            alternatives : alternative alternatives | epsilon
            alternative : CHOICE identifier program END
         */
//    }

    public List<ProgramElement> parseProgram() {
        // for reasons, a thing called bad-symbol that sounds bad always comes up first
        scanner.nextToken();

        // Parse everything that's not bad :-)
        return parseVJava();
    }

    boolean isEndOfProgram() {
        VJavaToken token = new VJavaToken(scanner.token());
        //the end of a variational segment is the 'end' keyword
        if (token.isVariational()) {
            return token.name().equals("end");
        }
        //otherwise, the end of a program must be the EOF token
        else {
            return token.isKind(Tokens.TokenKind.EOF);
        }
    }

    List<ProgramElement> parseVJava() {
        List<ProgramElement> program = new ArrayList<>();
        while (!isEndOfProgram()) {
            VJavaToken token = new VJavaToken(scanner.token());

            // if the token is a dimension
            if (token.isVariational()) {
                program.add(parseDimension());
            }
            // otherwise, we have more non-variational java tokens to add
            else {
                program.add(parseJavaFragment());
            }
        }

        return program;
    }

    boolean isEndOfJavaFragment() {
        VJavaToken token = new VJavaToken(scanner.token());
        //if we have arrived at the end of a variation segment, or the beginning of a new one
        //then the java segment is terminated
        if (token.isVariational()) {
            return true;
        }
        //otherwise, the end of a java segment must be the EOF token
        else {
            return token.isKind(Tokens.TokenKind.EOF);
        }
    }

    JavaFragment parseJavaFragment() {
        List<VJavaToken> tokens = new ArrayList<>();
        VJavaToken token;
        do {
            token = new VJavaToken(scanner.token());
            if (!isEndOfJavaFragment()) {
                tokens.add(token);
                scanner.nextToken();
            }
        } while (!isEndOfJavaFragment());

        return new JavaFragment(tokens);
    }

    Dimension parseDimension() {
        Tokens.Token dimdec = scanner.token();
        scanner.nextToken();
        Tokens.Token dimid = scanner.token();

        assert dimdec.name().toString().equals("dimension");
        scanner.nextToken();

        List<Alternative> alternatives = new ArrayList<>();
        Tokens.Token token;
        do {
            alternatives.add(parseChoice());
            token = scanner.token();
        } while (token.kind != Tokens.TokenKind.EOF && token.name().toString().equals("alternative"));
        assert token.name().toString().equals("end");
        scanner.nextToken();

        return new Dimension(dimid.name().toString(), alternatives);
    }

    Alternative parseChoice() {
        Tokens.Token dimToken = scanner.token();
        assert dimToken.name().toString().equals("alternative");
        scanner.nextToken();

        VJavaToken altIdToken = new VJavaToken(scanner.token());
        String id = altIdToken.name();
        scanner.nextToken();

        List<ProgramElement> body = parseVJava();

        VJavaToken endToken = new VJavaToken(scanner.token());
        assert endToken.name().equals("end");
        scanner.nextToken();

        return new Alternative(id, body);
    }
}
