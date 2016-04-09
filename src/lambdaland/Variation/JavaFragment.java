package lambdaland.Variation;

import com.sun.tools.javac.parser.Tokens;

import java.util.List;

/**
 * Created by rikkigibson on 4/4/16.
 */
public class JavaFragment extends ProgramElement {
    public final List<VJavaToken> tokens;

    public JavaFragment(List<VJavaToken> tokens) {
        this.tokens = tokens;
    }
}
