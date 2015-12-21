package domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.RelationshipEntity;

/**
 * Created by mmwaikar on 09-12-2015.
 */
@RelationshipEntity(type = "CONNECTION")
public class Connection {
    private String name;

    @JsonManagedReference
    private Artifact source;

    @JsonManagedReference
    private Artifact target;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Artifact getSource() {
        return source;
    }

    public void setSource(Artifact source) {
        this.source = source;
    }

    public Artifact getTarget() {
        return target;
    }

    public void setTarget(Artifact target) {
        this.target = target;
    }

    public Connection() {}

    public Connection(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "name='" + name + '\'' +
                ", source=" + source.toString() +
                ", target=" + target.toString() +
                '}';
    }
}
