package dao;

import domain.Station;

/**
 * Created by mmwaikar on 10-12-2015.
 */
public class StationDao extends GraphDao<Station> {
    public StationDao(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public Class<Station> getEntityType() {
        return Station.class;
    }
}
