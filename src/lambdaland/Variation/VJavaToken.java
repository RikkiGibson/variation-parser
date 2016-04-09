package lambdaland.Variation;

import com.sun.tools.javac.parser.Tokens;

/**
 * Created by Miles on 4/9/2016.
 * Container for our token
 */
public class VJavaToken {
    public enum TokenType {
        VARIATIONAL, BASEJAVA
    }

    private TokenType type;

    private Tokens.Token baseToken;
    private VToken vtoken;

    public String name() {
        if(this.type == TokenType.VARIATIONAL) {
            return vtoken.name();
        } else {
            if(baseToken.name() != null) {
                return baseToken.name().toString();
            } else {
                return "";
            }
        }
    }


}
