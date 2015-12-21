package domain;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mmwaikar on 02-12-2015.
 */
@NodeEntity
public class Group extends Entity {
    private String name;

    @Relationship(type = "IS_IN", direction = Relationship.OUTGOING)
    private Set<Project> projects = new HashSet<>();

    public String getName() {
        return name;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    public void removeProject(Project project) {
        this.projects.remove(project);
    }

    public Group() {}

    public Group(String name) {
        this.name = name;
    }
}
