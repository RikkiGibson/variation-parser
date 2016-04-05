package lambdaland;

import com.sun.tools.javac.parser.Scanner;
import com.sun.tools.javac.parser.Tokens;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                        choice X.l
                            System.out.println("You chose left");
                        end
                        choice X.r
                            System.out.println("You chose right");
                        end
                    end
                }
            }

            Grammar:
            program : javatokens program | dimension program | epsilon
            javatokens : javatoken javatokens | epsilon
            javatoken : (any of the tokens defined in java)
            dimension : DIMENSION identifier choices END
            choices : choice choices | epsilon
            choice : CHOICE identifier javatokens END
         */
//    }

    List<ProgramElement> parseProgram() {
        // for reasons, a thing called bad-symbol that sounds bad always comes up first
        scanner.nextToken();
        List<ProgramElement> program = new ArrayList<>();
        Tokens.Token token;
        do {
            token = scanner.token();
            if ("dimension".equals(getName(token))) {
                program.add(parseDimension());
            } else if (token.kind != Tokens.TokenKind.EOF) {
                program.add(parseJavaFragment());
            }
        } while (token.kind != Tokens.TokenKind.EOF);

        return program;
    }

    boolean isVariational(Tokens.Token token) {
        return "end".equals(getName(token)) || "dimension".equals(getName(token));
    }

    public static String getName(Tokens.Token token) {
        try {
            if (token.kind == Tokens.TokenKind.STRINGLITERAL) {
                return token.stringVal();
            } else {
                return token.name().toString();
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    JavaFragment parseJavaFragment() {
        List<Tokens.Token> tokens = new ArrayList<>();
        Tokens.Token token;
        do {
            token = scanner.token();
            if (!isVariational(token) && token.kind != Tokens.TokenKind.EOF) {
                tokens.add(token);
                scanner.nextToken();
            }
        } while (!isVariational(token) && token.kind != Tokens.TokenKind.EOF);

        return new JavaFragment(tokens);
    }

    Dimension parseDimension() {
        assert "dimension".equals(getName(scanner.token()));
        scanner.nextToken();

        String id = getName(scanner.token());
        scanner.nextToken();

        List<Choice> choices = new ArrayList<>();
        Tokens.Token token;
        do {
            token = scanner.token();
            if ("choice".equals(getName(token))) {
                choices.add(parseChoice());
            } else {
                assert "end".equals(getName(token));
            }
        } while (!"end".equals(getName(token)));

        scanner.nextToken();

        return new Dimension(id, choices);
    }

    Choice parseChoice() {
        assert "choice".equals(getName(scanner.token()));
        scanner.nextToken();

        String id = getName(scanner.token());
        scanner.nextToken();

        JavaFragment javaFragment = parseJavaFragment();

        assert "end".equals(getName(scanner.token()));
        scanner.nextToken();

        return new Choice(id, javaFragment);
    }
}
