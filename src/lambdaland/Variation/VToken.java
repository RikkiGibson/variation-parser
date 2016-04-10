package lambdaland.Variation;

import com.sun.tools.javac.parser.Tokens;

/**
 * Created by Miles on 4/9/2016.
 */
public class VToken {
    public enum VTokenType {
        DIMENSION, ALTERNATIVE, END
    }

    private VTokenType type;

    public VToken(Tokens.Token token) {
        if(token.name() != null && token.name().toString().equals("dimension")) {
            this.type = VTokenType.DIMENSION;
        } else if(token.name() != null && token.name().toString().equals("alternative")) {
            this.type = VTokenType.ALTERNATIVE;
        } else if(token.name() != null && token.name().toString().equals("end")) {
            this.type = VTokenType.END;
        }
    }

    public VToken(String vjavaType) {
        if(vjavaType.equals("dimension")) {
            this.type = VTokenType.DIMENSION;
        }
        if(vjavaType.equals("alternative")) {
            this.type = VTokenType.ALTERNATIVE;
        }
        if(vjavaType.equals("end")) {
            this.type = VTokenType.END;
        }
    }

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
