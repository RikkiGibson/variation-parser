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
                List<ProgramElement> body = getMatchingProgram((Dimension)programElement, choices);
                if (body != null) {
                    tokens.addAll(reduce(body, choices));
                }
            }
        }
        return tokens;
    }

    public static List<ProgramElement> getMatchingProgram(Dimension dimension, Map<String, String> choices) {
        String choiceIdentifier = choices.get(dimension.id);
        for (Alternative alternative : dimension.alternatives) {
            if (alternative.id.equals(choiceIdentifier)) {
                return alternative.body;
            }
        }
        return null;
    }
}
