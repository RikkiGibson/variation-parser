package lambdaland.Variation;

import com.sun.tools.javac.parser.Tokens;

import java.util.List;


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

    public List<Tokens.Comment> comments() {
        if(this.type == TokenType.BASEJAVA) {
            return baseToken.comments;
        } else return null;
    }

    public boolean isVariational() {
        return this.type == TokenType.VARIATIONAL;
    }


    private boolean isVariational(Tokens.Token token) {
        try {
            return token.name() != null
                && (token.name().toString().equals("dimension")
                    || token.name().toString().equals("alternative")
                    || token.name().toString().equals("end"));

        }
        catch (Exception e) {
            return false;
        }
    }

    public VJavaToken(Tokens.Token token) {
        if (isVariational(token)) {
            this.type = TokenType.VARIATIONAL;
            this.vtoken = new VToken(token);
        } else {
            this.baseToken = token;
            this.type = TokenType.BASEJAVA;
        }
    }

    public VJavaToken(String vjavaType) {
        this.type = TokenType.VARIATIONAL;
        this.vtoken = new VToken(vjavaType);
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
            try {
                return baseToken.name().toString();
            } catch (Exception e) {
                return "";
            }
        }
    }

    public VJavaToken(String declaration, String identifier) {
        this.type = TokenType.VARIATIONAL;
        VToken.VTokenType tokentype;
        if(declaration.equals("alternative")) {
            tokentype = VToken.VTokenType.ALTERNATIVE;
        } else if(declaration.equals("dimension")) {
            tokentype = VToken.VTokenType.DIMENSION;
        } else {
            //explode
            throw new IllegalArgumentException();
        }
        this.vtoken = new VToken(identifier, tokentype);

    }

    public VJavaToken(Tokens.Token dimdec, Tokens.Token dimid) {
        this(dimdec.name().toString(), dimid.name().toString());
    }

    public String toString() {
        if(this.isVariational()) {
            return vtoken.toString();
        }
        else {
            //if this token is a string literal, access the special string literal storage location
            if(this.isKind(Tokens.TokenKind.STRINGLITERAL)) {
                return "\"" + this.baseToken.stringVal() + "\"";
            }
            String ret = "";
            if (this.baseToken.comments != null) {
                for (Tokens.Comment comment : this.baseToken.comments) {
                    ret += comment.getText();
                }
            }
            if(this.name().equals("")) {

                ret += this.baseToken.kind.toString().replace("'", "");
            } else {
                //for most tokens
                ret += this.name();
            }

            return ret;
        }
    }


}
