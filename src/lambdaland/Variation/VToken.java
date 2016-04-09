package lambdaland.Variation;

/**
 * Created by Miles on 4/9/2016.
 */
public class VToken {
    public enum VTokenType {
        DIMENSION, ALTERNATIVE
    }

    public VToken(VTokenType type, String id) {
        this.id = id;
        this.type = type;
    }
    public String id;
    private VTokenType type;

    public String name() {
        return id;
    }
}
