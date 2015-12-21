package dao;

import domain.Train;

/**
 * Created by mmwaikar on 10-12-2015.
 */
public class TrainDao extends GraphDao<Train> {
    public TrainDao(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public Class<Train> getEntityType() {
        return Train.class;
    }
}
