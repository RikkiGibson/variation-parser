package lambdaland;

import java.util.List;

/**
 * Created by rikkigibson on 4/4/16.
 */
public class Alternative {
    public final String id;
    public final List<ProgramElement> body;

    public Alternative(String id, List<ProgramElement> body) {
        this.id = id;
        this.body = body;
    }
}
