package lambdaland;

import java.util.List;

/**
 * Created by rikkigibson on 4/4/16.
 */
public class Dimension extends ProgramElement {
    public final String id;
    public final List<Alternative> alternatives;
    public Dimension(String id, List<Alternative> alternatives) {
        this.id = id;
        this.alternatives = alternatives;
    }
}
