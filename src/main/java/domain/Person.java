package domain;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.RelationshipEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mmwaikar on 27-11-2015.
 */
@NodeEntity
public class Person extends Entity {
    private String firstName;
    private String lastName;

    @Relationship(type = "BELONGS_TO", direction = Relationship.OUTGOING)
    private Set<Group> groups = new HashSet<>();

    @Relationship(type = "PLAYS", direction = Relationship.OUTGOING)
    private Set<ITeam> teams = new HashSet<>();

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public void removeGroup(Group group) {
        this.groups.remove(group);
    }

    public Set<ITeam> getTeams() {
        return teams;
    }

    public void addTeam(ITeam team) {
        this.teams.add(team);
    }

    public void removeTeam(ITeam team) {
        this.teams.remove(team);
    }

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
