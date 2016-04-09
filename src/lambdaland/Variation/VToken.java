package lambdaland.Variation;

import com.sun.tools.javac.parser.Tokens;

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

    public VToken(Tokens.Token token) {
        if(token.name() != null && token.name().equals("dimension")) {
            this.type = VTokenType.DIMENSION;
            this.id = token.toString();
        }
    }

    public String id;
    private VTokenType type;

    public String name() {
        return id;
    }

    public String toString() {
        String ret = "";
        if(this.type == VTokenType.DIMENSION) {
            ret += "dimension ";
        } else {
            ret += "alternative ";
        }
        ret += this.id;
        return ret;
    }
}
