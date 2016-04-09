package lambdaland;

import com.sun.tools.javac.parser.Tokens;
import lambdaland.Variation.VJavaToken;

import java.util.List;

/**
 * Created by rikkigibson on 4/5/16.
 */
public class PrettyPrinter {
    final static String indentChar = "\t";

    static void print(List<VJavaToken> program) {
        int indentLevel = 0;
        for (int i = 0; i < program.size() - 1; i++) {
            indentLevel += printToken(program.get(i), program.get(i+1), indentLevel);
        }
        printToken(program.get(program.size()-1), null, indentLevel);
    }

    private static int printToken(VJavaToken cur, VJavaToken next, int indentLevel) {
        System.out.print(cur.toString());

        int deltaIndent = indentChange(cur);

        System.out.print(getWhitespace(cur, next, indentLevel + deltaIndent));

        return deltaIndent;
    }

    private static boolean isEndOfLine(VJavaToken token) {
        return token.isKind(Tokens.TokenKind.SEMI) || token.isKind(Tokens.TokenKind.LBRACE) || token.isKind(Tokens.TokenKind.RBRACE);
    }

    private static boolean needsSpace(VJavaToken token, VJavaToken next) {
        if(next != null) {
              if (next.isKind(Tokens.TokenKind.RPAREN) || next.isKind(Tokens.TokenKind.SEMI)){
                  return false;
              }
              if (next.isKind(Tokens.TokenKind.LBRACE)) {
                  return true;
              }
        }

        return token.isKind(Tokens.TokenKind.LPAREN) &&
                token.isKind(Tokens.TokenKind.LBRACKET) &&
                token.isKind(Tokens.TokenKind.IDENTIFIER) &&
                token.isKind(Tokens.TokenKind.DOT);
    }

    private static int indentChange(VJavaToken token) {
        if(token.isKind(Tokens.TokenKind.LBRACE)) return 1;
        if(token.isKind(Tokens.TokenKind.RBRACE)) return -1;
        return 0;
    }

    private static String getWhitespace(VJavaToken cur, VJavaToken next, int indentLevel) {
        String result = "";
        if (needsSpace(cur, next)) {
            result = " ";
        }
        if (isEndOfLine(cur)) {
            if (next != null && next.isKind(Tokens.TokenKind.RBRACE)) {
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
