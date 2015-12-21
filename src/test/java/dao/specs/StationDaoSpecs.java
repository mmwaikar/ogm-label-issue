package dao.specs;

/**
 * Created by mmwaikar on 10-12-2015.
 */

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
 * Created by mmwaikar on 10-12-2015.
 */
public class StationDaoSpecs {
    private final long MH_ID = 15l;
    private final long RJ_ID = 16l;

    private StateDao stateDao;
    private StationDao stationDao;

    @Before
    public void setUp() throws Exception {
        stateDao = new StateDao("http://localhost:7474", "neo4j", "neo4j123");
        stationDao = new StationDao("http://localhost:7474", "neo4j", "neo4j123");
    }

    @Test
    public void should_be_able_to_add() {
        State mh = stateDao.findOne(MH_ID);
        Station pune = new Station("pune");
        pune.setState(mh);
        save(pune);

        State rj = stateDao.findOne(RJ_ID);
        Station ajmer = new Station("ajmer");
        ajmer.setState(rj);
        save(ajmer);
    }

    private void save(Station station) {
        Station saved = stationDao.createOrUpdate(station);
        assertThat("station is null", saved, is(notNullValue()));
        assertThat("station name does not match", saved.getName(), is(station.getName()));
    }
}

