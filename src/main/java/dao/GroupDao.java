package dao;

import domain.Group;

/**
 * Created by mmwaikar on 02-12-2015.
 */
public class GroupDao extends GraphDao<Group> {
    public GroupDao(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public Class<Group> getEntityType() {
        return Group.class;
    }
}
