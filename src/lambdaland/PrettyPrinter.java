package lambdaland;

import com.sun.tools.javac.parser.Tokens;

import java.util.List;

/**
 * Created by rikkigibson on 4/5/16.
 */
public class PrettyPrinter {
    final static String indentChar = "\t";

    static void print(List<Tokens.Token> program) {
        int indentLevel = 0;
        for (int i = 0; i < program.size() - 1; i++) {
            indentLevel += printToken(program.get(i), program.get(i+1), indentLevel);
        }
        printToken(program.get(program.size()-1), null, indentLevel);
    }

    private static int printToken(Tokens.Token cur, Tokens.Token next, int indentLevel) {
        String name = VariationParser.getName(cur);
        if (name != null) {
            System.out.print(name);
        } else {
            if (cur.comments != null) {
                for (Tokens.Comment comment : cur.comments) {
                    System.out.print(comment.getText());
                }
            }
            System.out.print(cur.kind.toString().replace("'", ""));
        }
        int deltaIndent = indentChange(cur);

        System.out.print(getWhitespace(cur, next, indentLevel + deltaIndent));

        return deltaIndent;
    }

    private static boolean isEndOfLine(Tokens.Token token) {
        return token.kind == Tokens.TokenKind.SEMI || token.kind == Tokens.TokenKind.LBRACE || token.kind == Tokens.TokenKind.RBRACE;
    }

    private static boolean needsSpace(Tokens.Token token, Tokens.Token next) {
        if(next != null) {
              if (next.kind == Tokens.TokenKind.RPAREN || next.kind == Tokens.TokenKind.SEMI){
                  return false;
              }
              if (next.kind == Tokens.TokenKind.LBRACE) {
                  return true;
              }
        }

        return token.kind != Tokens.TokenKind.LPAREN &&
                token.kind != Tokens.TokenKind.LBRACKET &&
                token.kind != Tokens.TokenKind.IDENTIFIER &&
                token.kind != Tokens.TokenKind.DOT;
    }

    private static int indentChange(Tokens.Token token) {
        if(token.kind == Tokens.TokenKind.LBRACE) return 1;
        if(token.kind == Tokens.TokenKind.RBRACE) return -1;
        return 0;
    }

    private static String getWhitespace(Tokens.Token cur, Tokens.Token next, int indentLevel) {
        String result = "";
        if (needsSpace(cur, next)) {
            result = " ";
        }
        if (isEndOfLine(cur)) {
            if (next != null && next.kind == Tokens.TokenKind.RBRACE) {
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
