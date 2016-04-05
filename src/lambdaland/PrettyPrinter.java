package lambdaland;

import com.sun.tools.javac.parser.Tokens;

import java.util.List;

/**
 * Created by rikkigibson on 4/5/16.
 */
public class PrettyPrinter {
    static void print(List<Tokens.Token> program) {
        for (Tokens.Token token : program) {
            String name = VariationParser.getName(token);
            if (name != null) {
                System.out.println(name);
            } else {
                if (token.comments != null) {
                    for (Tokens.Comment comment : token.comments) {
                        System.out.println(comment.getText());
                    }
                }
                System.out.println(token.kind);
            }
        }
    }
}
