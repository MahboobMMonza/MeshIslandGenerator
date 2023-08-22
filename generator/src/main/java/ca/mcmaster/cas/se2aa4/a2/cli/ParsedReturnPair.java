package ca.mcmaster.cas.se2aa4.a2.cli;

/**
 * ParsedReturnPair
 */
public class ParsedReturnPair {

    public final int returnVal;
    public boolean needsHelp;

    public ParsedReturnPair(int returnVal, boolean needsHelp) {
        this.returnVal = returnVal;
        this.needsHelp = needsHelp;
    }
}