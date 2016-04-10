package lambdaland.Variation;

import com.sun.tools.javac.parser.Tokens;

/**
 * Created by Miles on 4/9/2016.
 */
public class VToken {
    public enum VTokenType {
        DIMENSION, ALTERNATIVE, END
    }

    public VToken(VTokenType type, String id) {
        this.type = type;
    }

    public VToken(Tokens.Token token) {
        if(token.name() != null && token.name().toString().equals("dimension")) {
            this.type = VTokenType.DIMENSION;
        } else if(token.name() != null && token.name().toString().equals("alternative")) {
            this.type = VTokenType.ALTERNATIVE;
        } else if(token.name() != null && token.name().toString().equals("end")) {
            this.type = VTokenType.END;
        }
    }

    private VTokenType type;

    public String name() {
        if(this.type == VTokenType.ALTERNATIVE) return "alternative";
        if(this.type == VTokenType.DIMENSION) return "dimension";
        if(this.type == VTokenType.END) return "end";
        return null;
    }

    public String toString() {
        return this.name();
    }
}
