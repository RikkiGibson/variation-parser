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
        for (Tokens.Token token : program) {
            indentLevel += printToken(token, indentLevel);
        }
    }

    private static int printToken(Tokens.Token token, int indentLevel) {
        String name = VariationParser.getName(token);
        if (name != null) {
            System.out.print(name);
        } else {
            if (token.comments != null) {
                for (Tokens.Comment comment : token.comments) {
                    System.out.print(comment.getText());
                }
            }
            System.out.print(token.kind.toString().replace("'", ""));
        }

        int deltaIndent = indentChange(token);
        // slap down a new-line when appropriate
        if(isEndOfLine(token)) {
            System.out.println();
            if(token.kind == Tokens.TokenKind.RBRACE) indentLevel -= 1;
            for(int i = 0; i < deltaIndent + indentLevel; i++) System.out.print(indentChar);
        } else if(needsSpace(token)){
            System.out.print(" ");
        }

        return deltaIndent;
    }

    private static boolean isEndOfLine(Tokens.Token token) {
        return token.kind == Tokens.TokenKind.SEMI || token.kind == Tokens.TokenKind.LBRACE || token.kind == Tokens.TokenKind.RBRACE;
    }

    private static boolean needsSpace(Tokens.Token token) {
        return  token.kind != Tokens.TokenKind.LPAREN &&
                token.kind != Tokens.TokenKind.LBRACKET &&
                token.kind != Tokens.TokenKind.IDENTIFIER;
    }

    private static int indentChange(Tokens.Token token) {
        if(token.kind == Tokens.TokenKind.LBRACE) return 1;
        if(token.kind == Tokens.TokenKind.RBRACE) return -1;
        return 0;
    }
}
