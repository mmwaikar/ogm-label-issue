package dao.specs;

import dao.StateDao;
import dao.StationDao;
import domain.State;
import domain.Station;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by mmwaikar on 18-12-2015.
 */
public class StateDaoSpecs {
    private StateDao stateDao;

    @Before
    public void setUp() throws Exception {
        stateDao = new StateDao("http://localhost:7474", "neo4j", "neo4j123");
    }

    @Test
    public void should_be_able_to_add() {
        State mh = new State("maharashtra");
        save(mh);

        State rj = new State("rajasthan");
        save(rj);
    }

    private void save(State station) {
        State saved = stateDao.createOrUpdate(station);
        assertThat("state is null", saved, is(notNullValue()));
        assertThat("state name does not match", saved.getName(), is(station.getName()));
    }
}
