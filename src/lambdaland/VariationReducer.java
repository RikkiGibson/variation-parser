package lambdaland;

import com.sun.tools.javac.parser.Tokens;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rikkigibson on 4/4/16.
 */
public class VariationReducer {
    public static List<Tokens.Token> reduceProgram(List<ProgramElement> program, Map<String, String> choices) {
        List<Tokens.Token> tokens = new ArrayList<>();
        for (ProgramElement programElement : program) {
            if (programElement instanceof JavaFragment) {
                tokens.addAll(((JavaFragment)programElement).tokens);
            } else {
                List<Tokens.Token> body = reduceDimension((Dimension)programElement, choices);
                if (body != null) {
                    tokens.addAll(body);
                }
            }
        }
        return tokens;
    }

    public static List<Tokens.Token> reduceDimension(Dimension dimension, Map<String, String> choices) {
        //if the user has made a selection for this dimension, then reduceProgram it
        if(choices.containsKey(dimension.id)) {
            String selectedAlternative = choices.get(dimension.id);
            //find the alternative that the user selected
            for (Alternative alternative : dimension.alternatives) {
                if (alternative.id.equals(selectedAlternative)) {
                    //return that alternative's contents, recursively
                    return reduceProgram(alternative.body, choices);
                }
            }
        }
        //otherwise, return the tokens corresponding to the full dimension-alternative syntax
        else {

        }
        return null;
    }
}
