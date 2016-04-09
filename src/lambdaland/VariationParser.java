package lambdaland;

import com.sun.tools.javac.parser.Scanner;
import com.sun.tools.javac.parser.Tokens;

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
                        choice l
                            System.out.println("You chose left");
                        end
                        choice r
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
            alternatives : choice alternatives | epsilon
            choice : CHOICE identifier program END
         */
//    }

    public List<ProgramElement> parseProgram() {
        // for reasons, a thing called bad-symbol that sounds bad always comes up first
        scanner.nextToken();

        // Parse everything that's not bad :-)
        return parseVJava();
    }

    boolean isEndOfProgram() {
        Tokens.Token token = scanner.token();
        if (token.kind == Tokens.TokenKind.EOF) {
            return true;
        }
        String name = getName(token);
        if (name != null) {
            return name.equals("end");
        }
        return false;
    }

    List<ProgramElement> parseVJava() {
        List<ProgramElement> program = new ArrayList<>();
        while (!isEndOfProgram()) {
            String name = getName(scanner.token());
            // if the next token is a dimension
            if (name != null && name.equals("dimension")) {
                program.add(parseDimension());
            }
            // otherwise, we have more non-variational java tokens to add
            else {
                program.add(parseJavaFragment());
            }
        }

        return program;
    }

    public static String getName(Tokens.Token token) {
        try {
            return token.name().toString();
        }
        catch (Exception e) {
            return null;
        }
    }

    boolean isEndOfJavaFragment() {
        Tokens.Token token = scanner.token();
        if (token.kind == Tokens.TokenKind.EOF) {
            return true;
        }
        String name = getName(token);
        return name != null && (name.equals("end") || name.equals("dimension"));
    }

    JavaFragment parseJavaFragment() {
        List<Tokens.Token> tokens = new ArrayList<>();
        Tokens.Token token;
        do {
            token = scanner.token();
            if (!isEndOfJavaFragment()) {
                tokens.add(token);
                scanner.nextToken();
            }
        } while (!isEndOfJavaFragment());

        return new JavaFragment(tokens);
    }

    Dimension parseDimension() {
        String dimensionName = getName(scanner.token());
        assert dimensionName != null && dimensionName.equals("dimension");
        scanner.nextToken();

        String id = getName(scanner.token());
        scanner.nextToken();

        List<Alternative> alternatives = new ArrayList<>();
        Tokens.Token token;
        String name;
        do {
            alternatives.add(parseChoice());
            token = scanner.token();
            name = getName(token);
        } while (token.kind != Tokens.TokenKind.EOF && name != null && name.equals("choice"));
        assert name != null && name.equals("end");
        scanner.nextToken();

        return new Dimension(id, alternatives);
    }

    Alternative parseChoice() {
        String name = getName(scanner.token());
        assert name != null && name.equals("choice");
        scanner.nextToken();

        String id = getName(scanner.token());
        scanner.nextToken();

        List<ProgramElement> body = parseVJava();

        name = getName(scanner.token());
        assert name != null && name.equals("end");
        scanner.nextToken();

        return new Alternative(id, body);
    }
}
