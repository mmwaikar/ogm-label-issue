package dao;

import domain.State;

/**
 * Created by mmwaikar on 18-12-2015.
 */
public class StateDao extends GraphDao<State> {
    public StateDao(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public Class<State> getEntityType() {
        return State.class;
    }
}
