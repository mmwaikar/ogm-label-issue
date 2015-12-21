package dao.specs;

import com.google.common.collect.Lists;
import dao.StationDao;
import dao.TrainDao;
import domain.Station;
import domain.Train;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by mmwaikar on 10-12-2015.
 */
public class TrainDaoSpecs {
    private final long PUNE_ID = 17l;
    private final long AJMER_ID = 18l;
    private final long TRAIN_ID = 12l;

    private TrainDao trainDao;
    private StationDao stationDao;

    @Before
    public void setUp() throws Exception {
        trainDao = new TrainDao("http://localhost:7474", "neo4j", "neo4j123");
        stationDao = new StationDao("http://localhost:7474", "neo4j", "neo4j123");
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
        assertThat("train target state is null", train.getTarget().getState(), is(notNullValue()));
    }

    private void save(Train train, Station source, Station dest) {
        Train saved = trainDao.createOrUpdate(train);
        assertThat("train is null", saved, is(notNullValue()));
        assertThat("train name does not match", saved.getName(), is(train.getName()));
        assertThat("train source station does not match", saved.getSource().getName(), is(source.getName()));
        assertThat("train dest station does not match", saved.getTarget().getName(), is(dest.getName()));
    }
}
