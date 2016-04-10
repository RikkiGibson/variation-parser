package lambdaland;

import com.sun.tools.javac.parser.Tokens;
import lambdaland.Variation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rikkigibson on 4/4/16.
 */
public class VariationReducer {
    public static List<VJavaToken> reduceProgram(List<ProgramElement> program, Map<String, String> selections) {
        List<VJavaToken> tokens = new ArrayList<>();
        for (ProgramElement programElement : program) {
            if (programElement instanceof JavaFragment) {
                tokens.addAll(((JavaFragment)programElement).tokens);
            } else {
                List<VJavaToken> body = reduceDimension((Dimension)programElement, selections);
                if (body != null) {
                    tokens.addAll(body);
                }
            }
        }
        return tokens;
    }

    public static List<VJavaToken> reduceDimension(Dimension dimension, Map<String, String> selections) {
        //if the user has made a selection for this dimension, then reduceProgram it
        if(selections.containsKey(dimension.id)) {
            String selectedAlternative = selections.get(dimension.id);
            //find the alternative that the user selected
            for (Alternative alternative : dimension.alternatives) {
                if (alternative.id.equals(selectedAlternative)) {
                    //return that alternative's contents, recursively
                    return reduceProgram(alternative.body, selections);
                }
            }
        }
        //otherwise, return the tokens corresponding to the full dimension-alternative syntax
        else {
            List<VJavaToken> tokens = new ArrayList<>();
            tokens.add(new VJavaToken("dimension"));
            tokens.add(new VJavaToken("end"));
            return tokens;

        }
        return null;
    }
}
