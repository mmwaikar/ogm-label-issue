package domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Created by mmwaikar on 09-12-2015.
 */
@NodeEntity
public class Artifact extends Entity {
    private String name;

    /*
     * we need multiple connections
     * why need a connection object when connection already knows source and target
     *
     */
    @JsonBackReference
    @Relationship(type = "CONNECTION", direction = Relationship.UNDIRECTED)
    private Connection connection;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Artifact() {}

    public Artifact(String name) {
        this.name = name;
    }

//    @Override
//    public String toString() {
//        return "Artifact{" +
//                "name='" + name + '\'' +
//                ", connection=" + connection.toString() +
//                '}';
//    }
}
