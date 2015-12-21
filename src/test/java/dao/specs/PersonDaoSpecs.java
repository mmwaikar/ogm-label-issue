package dao.specs;

import com.google.common.collect.Iterables;
import dao.GroupDao;
import dao.PersonDao;
import dao.TeamDao;
import domain.Group;
import domain.ITeam;
import domain.Person;
import domain.Team;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by mmwaikar on 27-11-2015.
 */
public class PersonDaoSpecs {

    public static final long PID = 6L;
    public static final long ADMIN_ID = 7L;
    public static final long USER_ID = 8L;
    public static final long CRICKET_ID = 0L;
    public static final long FOOTBALL_ID = 1L;

    private Person person;
    private Person withLabel;
    private TeamDao teamDao;
    private GroupDao groupDao;
    private PersonDao personDao;

    @Before
    public void setUp() throws Exception {
        teamDao = new TeamDao("http://localhost:7474", "neo4j", "neo4j123");
        groupDao = new GroupDao("http://localhost:7474", "neo4j", "neo4j123");
        personDao = new PersonDao("http://localhost:7474", "neo4j", "neo4j123");
        person = new Person("carl", "lewis");
        withLabel = new Person("roger", "federer");
    }

    @Test
    public void should_be_able_to_add() {
        Person saved = personDao.createOrUpdate(person);
        assertPerson(saved, person);
    }

    @Test
    public void should_be_able_to_add_with_label() {
        String setLabelQuery = "MATCH (n: Person) WHERE n.firstName = {firstName} SET n :SWISS RETURN n;";
        Person saved = personDao.createOrUpdate(withLabel);
        assertPerson(saved, withLabel);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("firstName", withLabel.getFirstName());

        Iterable<Person> persons = personDao.addLabels(setLabelQuery, parameters);
        assertThat("person with label is null", persons, is(notNullValue()));
        assertThat("person with label count is zero", Iterables.size(persons), is(not(0)));
    }

    @Test
    public void should_be_able_to_add_group() {
        Person person = personDao.findOne(PID);
        assertThat("person is null", person, is(notNullValue()));

        Group admin = groupDao.findOne(ADMIN_ID);
        assertThat("admin is null", admin, is(notNullValue()));

        Group users = groupDao.findOne(USER_ID);
        assertThat("users is null", admin, is(notNullValue()));

        person.addGroup(admin);
        person.addGroup(users);

        Person saved = personDao.createOrUpdate(person);
        assertThat("saved is null", saved, is(notNullValue()));
        assertThat("saved group count does not match", saved.getGroups().size(), is(2));
    }

    @Test
    public void should_be_able_to_remove_group() {
        Person person = personDao.findOne(PID);
        assertThat("person is null", person, is(notNullValue()));
        assertThat("group count does not match", person.getGroups().size(), is(2));

        Group admin = groupDao.findOne(ADMIN_ID);
        assertThat("admin is null", admin, is(notNullValue()));

        person.removeGroup(admin);
        Person saved = personDao.createOrUpdate(person);
        assertThat("saved is null", saved, is(notNullValue()));
        assertThat("saved group count does not match", saved.getGroups().size(), is(1));
    }

    @Test
    public void should_be_able_to_add_team() {
        Person person = personDao.findOne(PID);
        assertThat("person is null", person, is(notNullValue()));

        Team cricket = teamDao.findOne(CRICKET_ID);
        assertThat("cricket is null", cricket, is(notNullValue()));

        Team football = teamDao.findOne(FOOTBALL_ID);
        assertThat("football is null", cricket, is(notNullValue()));

        person.addTeam(cricket);
        person.addTeam(football);

        Person saved = personDao.createOrUpdate(person);
        assertThat("saved is null", saved, is(notNullValue()));
        assertThat("saved team count does not match", saved.getTeams().size(), is(2));
    }

    @Test
    public void should_be_able_to_remove_team() {
        Person person = personDao.findOne(PID);
        assertThat("person is null", person, is(notNullValue()));
        assertThat("team count does not match", person.getTeams().size(), is(2));

        List<ITeam> teams = person.getTeams().stream().filter(t -> t.getName().equals("crikcet")).collect(Collectors.toList());
        Team cricket = (Team) teams.get(0);
//        Team cricket = teamDao.findOne(CRICKET_ID);
        assertThat("cricket is null", cricket, is(notNullValue()));

        person.removeTeam(cricket);
        Person saved = personDao.createOrUpdate(person);
        assertThat("saved is null", saved, is(notNullValue()));
        assertThat("saved team count does not match", saved.getTeams().size(), is(1));
    }

    private void assertPerson(Person saved, Person expected) {
        assertThat("person is null", saved, is(notNullValue()));
        assertThat("first name does not match", saved.getFirstName(), is(expected.getFirstName()));
        assertThat("last name does not match", saved.getLastName(), is(expected.getLastName()));
    }
}
