package domain;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by mmwaikar on 02-12-2015.
 */
@NodeEntity
public class Project extends Entity {
    private String name;

    public String getName() {
        return name;
    }

    public Project() {}

    public Project(String name) {
        this.name = name;
    }
}
