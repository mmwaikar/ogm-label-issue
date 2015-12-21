package domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Created by mmwaikar on 10-12-2015.
 */
@RelationshipEntity(type = "TRAINS")
public class Train extends Entity {
    private String name;

    @StartNode
    private Station source;

    @EndNode
    private Station target;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Station getSource() {
        return source;
    }

    public void setSource(Station source) {
        this.source = source;
    }

    public Station getTarget() {
        return target;
    }

    public void setTarget(Station target) {
        this.target = target;
    }

    public void addStations(Station source, Station target) {
        this.setSource(source);
        this.getSource().getTrains().add(this);
        this.setTarget(target);
        this.getTarget().getTrains().add(this);
    }

    public Train() {
    }

    public Train(String name) {
        this.name = name;
    }
}
