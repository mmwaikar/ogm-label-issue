package domain;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mmwaikar on 10-12-2015.
 */
@NodeEntity
public class Station extends Entity {
    private String name;
    private State state;

    @Relationship(type = "TRAINS")
    private Set<Train> trains = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<Train> getTrains() {
        return trains;
    }

    public void addTrain(Train train) {
        train.getSource().getTrains().add(train);
        train.getTarget().getTrains().add(train);
    }

    public Station() {
    }

    public Station(String name) {
        this.name = name;
    }
}
