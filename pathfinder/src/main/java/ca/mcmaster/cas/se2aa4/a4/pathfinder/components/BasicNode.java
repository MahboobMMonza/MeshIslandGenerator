package ca.mcmaster.cas.se2aa4.a4.pathfinder.components;

import java.util.*;

/**
 * BasicNode
 */
public class BasicNode implements Node {

    int index;
    Map<String, Object> properties;

    public BasicNode() {
        properties = new HashMap<>();
        index = -1;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index of a node cannot be set to a negative number.");
        }
        this.index = index;
    }

    @Override
    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }

    @Override
    public Object getProperty(String key) {
        return properties.get(key);
    }

}
