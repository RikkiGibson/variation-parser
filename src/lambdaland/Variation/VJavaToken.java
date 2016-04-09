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

    public boolean isVariational() {
        return this.type == TokenType.VARIATIONAL;
    }

    public boolean isVariational(Tokens.Token token) {
        return token.name() != null
                && (token.name().toString().equals("dimension") || token.name().toString().equals("alternative"));
    }

    public VJavaToken(Tokens.Token token) {
        if (isVariational(token)) {
            this.type = TokenType.VARIATIONAL;
            this.vtoken = new VToken(token);
        }
        this.baseToken = token;
        this.type = TokenType.BASEJAVA;
    }

    public boolean isKind(Tokens.TokenKind kind) {
        //if this isn't a base java token, then it certainly doesn't match one of the base java kinds
        if(this.type == TokenType.VARIATIONAL) return false;
        //otherwise, perform the test
        else return this.baseToken.kind == kind;
    }

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
