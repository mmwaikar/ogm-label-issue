package dao.specs;

import com.google.common.collect.Lists;
import dao.StateDao;
import dao.StationDao;
import dao.TrainDao;
import domain.State;
import domain.Station;
import domain.Train;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by mmwaikar on 10-12-2015.
 */
public class TrainDaoSpecs {
    private long PUNE_ID;
    private long AJMER_ID;
    private long TRAIN_ID;
    private boolean insertData;

    private StateDao stateDao;
    private TrainDao trainDao;
    private StationDao stationDao;

    @Before
    public void setUp() throws Exception {
        stateDao = new StateDao("http://localhost:7474", "neo4j", "neo4j123");
        trainDao = new TrainDao("http://localhost:7474", "neo4j", "neo4j123");
        stationDao = new StationDao("http://localhost:7474", "neo4j", "neo4j123");

        State mh = new State("maharashtra");
        save(mh);

        State rj = new State("rajasthan");
        save(rj);

        Station pune = new Station("pune");
        pune.setState(mh);
        save(pune);

        PUNE_ID = pune.getPkId();

        Station ajmer = new Station("ajmer");
        ajmer.setState(rj);
        save(ajmer);

        AJMER_ID = ajmer.getPkId();

        Train duronto = new Train("duronto");
        duronto.addStations(ajmer, pune);
        save(duronto, ajmer, pune);
    }

    @Test
    public void should_be_able_to_add() {
        Station pune = stationDao.findOne(PUNE_ID);
        assertThat("station is null", pune, is(notNullValue()));
        Station ajmer = stationDao.findOne(AJMER_ID);
        assertThat("station is null", ajmer, is(notNullValue()));

//        Train rajdhani = new Train("rajdhani");
//        rajdhani.addStations(ajmer, pune);
//        save(rajdhani, ajmer, pune);

        Train duronto = new Train("duronto");
        duronto.addStations(ajmer, pune);
        save(duronto, ajmer, pune);
    }

    @Test
    public void should_find_by_id() {
        Train train = trainDao.findOne(TRAIN_ID, 2);
        assertThat("train source is null", train.getSource(), is(notNullValue()));
        assertThat("train target is null", train.getTarget(), is(notNullValue()));
        assertThat("train source state is null", train.getSource().getState(), is(notNullValue()));
        assertThat("train target state is null", train.getTarget().getState(), is(notNullValue()));
    }

    @Test
    public void should_find_all() {
        List<Train> trains = Lists.newArrayList(trainDao.findAll(6));
        assertThat("trains is null", trains, is(notNullValue()));
        assertThat("trains size is zero",  trains.size(), is(not(0)));

        Train train = trains.get(0);
        assertThat("train source is null", train.getSource(), is(notNullValue()));
        assertThat("train target is null", train.getTarget(), is(notNullValue()));
        assertThat("train source state is null", train.getSource().getState(), is(notNullValue()));
        System.out.println(train.getSource().getState());
        assertThat("train target state is null", train.getTarget().getState(), is(notNullValue()));
        System.out.println(train.getTarget().getState());
    }

    @Test
    public void should_find_all_by_query() {
        String cypher = "MATCH (r1)<-[]-()-[r: TRAINS]->()-[]->(r2) where ID(r) > 0 RETURN r, r1, r2;";
        Map<String, String> params = new HashMap<>();
        List<Train> trains = Lists.newArrayList(trainDao.findAllByQuery(cypher, params));

        assertThat("trains is null", trains, is(notNullValue()));
        assertThat("trains size is zero",  trains.size(), is(not(0)));

        Train train = trains.get(0);
        assertThat("train source is null", train.getSource(), is(notNullValue()));
        assertThat("train target is null", train.getTarget(), is(notNullValue()));
        assertThat("train source state is null", train.getSource().getState(), is(notNullValue()));
        System.out.println(train.getSource().getState());
        assertThat("train target state is null", train.getTarget().getState(), is(notNullValue()));
        System.out.println(train.getTarget().getState());
    }

    private void save(Train train, Station source, Station dest) {
        Train saved = trainDao.createOrUpdate(train);
        assertThat("train is null", saved, is(notNullValue()));
        assertThat("train name does not match", saved.getName(), is(train.getName()));
        assertThat("train source station does not match", saved.getSource().getName(), is(source.getName()));
        assertThat("train dest station does not match", saved.getTarget().getName(), is(dest.getName()));
    }

    private void save(State state) {
        State saved = stateDao.createOrUpdate(state);
        assertThat("state is null", saved, is(notNullValue()));
        assertThat("state name does not match", saved.getName(), is(state.getName()));
    }

    private void save(Station station) {
        Station saved = stationDao.createOrUpdate(station);
        assertThat("station is null", saved, is(notNullValue()));
        assertThat("station name does not match", saved.getName(), is(station.getName()));
    }
}
