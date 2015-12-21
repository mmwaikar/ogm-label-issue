package domain;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by mmwaikar on 18-12-2015.
 */
@NodeEntity
public class State extends Entity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State() {
    }

    public State(String name) {
        this.name = name;
    }
}
