package dao.specs;

import dao.TeamDao;
import domain.Team;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by mmwaikar on 04-12-2015.
 */
public class TeamDaoSpecs {
    private TeamDao teamDao;

    @Before
    public void setUp() throws Exception {
        teamDao = new TeamDao("http://localhost:7474", "neo4j", "neo4j123");
    }

    @Test
    public void should_be_able_to_add() {
        Team crikcet = new Team("cricket");
        save(crikcet);

        Team football = new Team("football");
        save(football);
    }

    private void save(Team team) {
        Team saved = teamDao.createOrUpdate(team);
        assertThat("team is null", saved, is(notNullValue()));
        assertThat("team name does not match", saved.getName(), is(team.getName()));
    }
}
