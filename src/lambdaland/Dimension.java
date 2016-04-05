package lambdaland;

import java.util.List;

/**
 * Created by rikkigibson on 4/4/16.
 */
public class Dimension extends ProgramElement {
    public final String id;
    public final List<Choice> choices;
    public Dimension(String id, List<Choice> choices) {
        this.id = id;
        this.choices = choices;
    }
}
