package dao;

import domain.Team;

/**
 * Created by mmwaikar on 04-12-2015.
 */
public class TeamDao extends GraphDao<Team> {
    public TeamDao(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public Class<Team> getEntityType() {
        return Team.class;
    }
}
