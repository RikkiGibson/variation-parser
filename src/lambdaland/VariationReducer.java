package lambdaland;

import com.sun.tools.javac.parser.Tokens;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rikkigibson on 4/4/16.
 */
public class VariationReducer {
    public static List<Tokens.Token> reduce(List<ProgramElement> program, Map<String, String> choices) {
        List<Tokens.Token> tokens = new ArrayList<>();
        for (ProgramElement programElement : program) {
            if (programElement instanceof JavaFragment) {
                tokens.addAll(((JavaFragment)programElement).tokens);
            } else {
                JavaFragment fragment = getMatchingFragment((Dimension)programElement, choices);
                if (fragment != null) {
                    tokens.addAll(fragment.tokens);
                }
            }
        }
        return tokens;
    }

    public static JavaFragment getMatchingFragment(Dimension dimension, Map<String, String> choices) {
        String choiceIdentifier = choices.get(dimension.id);
        for (Choice choice : dimension.choices) {
            if (choice.id.equals(choiceIdentifier)) {
                return choice.body;
            }
        }
        return null;
    }
}
