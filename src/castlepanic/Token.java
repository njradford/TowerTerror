package castlepanic;

/**
 * The common token superclass. Encompasses all token types, monsters and
 * effects. Supplies only a token name, subclasses provide additional
 * information such as monster type, effect type, and logic.
 *
 * @author Adam Whitley
 * @author Gregory Loftis
 * @author Dipesh Dave
 * @author John Fenwick
 */
public class Token {

    protected String tokenName;

    /**
     * A no-argument constructor for a token. Creates a null token.
     *
     */
    public Token() {
        tokenName = null;

    }

    /**
     * A one-argument constructor for a token. Gives the token a name.
     * Subclasses of token will supply additional information such as the type
     * of token, effects, and monster type.
     *
     * @param name The name of this token.
     */
    public Token(String name) {
        tokenName = name;

    }

    /**
     * Returns the name of this token.
     *
     * @return a string containing the name of this token.
     */
    public String getTokenName() {
        return tokenName;
    }
}
