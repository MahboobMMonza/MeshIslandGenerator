package ca.mcmaster.cas.se2aa4.a4.pathfinder.components;

/**
 * NodeInterface
 */
public interface Node {

    int getIndex();

    void setIndex(int index);

    void setProperty(String key, Object value);

    Object getProperty(String key);
}
