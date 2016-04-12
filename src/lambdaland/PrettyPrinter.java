package lambdaland;

import com.sun.tools.javac.parser.Tokens;
import lambdaland.Variation.VJavaToken;

import java.io.PrintStream;
import java.util.List;

/**
 * Created by rikkigibson on 4/5/16.
 */
public class PrettyPrinter {
    private final PrintStream outStream;

    public PrettyPrinter(PrintStream outStream) {
        this.outStream = outStream;
    }

    final static String indentChar = "    ";

    void print(List<VJavaToken> program) {
        int indentLevel = 0;
        for (int i = 0; i < program.size() - 1; i++) {
            indentLevel += printToken(program.get(i), program.get(i+1), indentLevel);
        }
        printToken(program.get(program.size()-1), null, indentLevel);
    }

    private int printToken(VJavaToken cur, VJavaToken next, int indentLevel) {
        outStream.print(cur.toString());

        int deltaIndent = indentChange(cur);

        outStream.print(getWhitespace(cur, next, indentLevel + deltaIndent));

        return deltaIndent;
    }

    private static boolean isEndOfLine(VJavaToken token) {
        return token.isKind(Tokens.TokenKind.SEMI)
                || token.isKind(Tokens.TokenKind.LBRACE)
                || token.isKind(Tokens.TokenKind.RBRACE)
                || token.isVariational();
    }

    private static boolean needsSpace(VJavaToken token, VJavaToken next) {
        //if the next token is a special syntax token, spaces have special rules
        if(next != null) {
              if (next.isKind(Tokens.TokenKind.RPAREN)
                  || next.isKind(Tokens.TokenKind.LPAREN)
                  || next.isKind(Tokens.TokenKind.LBRACKET)
                  || next.isKind(Tokens.TokenKind.SEMI)
                  || next.isKind(Tokens.TokenKind.DOT)) {

                  return false;
              }
              if (next.isKind(Tokens.TokenKind.LBRACE)) {
                  return true;
              }
        }

        //never insert a space after an accessor or left paren/bracket
        if (token.isKind(Tokens.TokenKind.DOT)
            || token.isKind(Tokens.TokenKind.LPAREN)
            || token.isKind(Tokens.TokenKind.LBRACKET)) return false;


        //if this token is an identifier, we want a space
        if (token.isKind(Tokens.TokenKind.IDENTIFIER)) return true;

        //default for edge cases I didn't think of
        return true;
    }

    private static int indentChange(VJavaToken token) {
        if(token.isKind(Tokens.TokenKind.LBRACE)) return 1;
        if(token.isKind(Tokens.TokenKind.RBRACE)) return -1;
        if(token.isVariational()) {
            if(token.name().equals("end")) return -1;
            else return 1;
        }
        return 0;
    }

    private static String getWhitespace(VJavaToken cur, VJavaToken next, int indentLevel) {
        String result = "";
        if (needsSpace(cur, next)) {
            result = " ";
        }
        if (isEndOfLine(cur)) {
            if (next != null && (next.isKind(Tokens.TokenKind.RBRACE)
                || next.name().equals("end"))) {
                indentLevel -= 1;
            }

            result = "\n";
            for (int i = 0; i < indentLevel; i++) {
                result += indentChar;
            }
        }
        return result;
    }
}
