package dao;

import domain.Project;

/**
 * Created by mmwaikar on 02-12-2015.
 */
public class ProjectDao extends GraphDao<Project> {
    public ProjectDao(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public Class<Project> getEntityType() {
        return Project.class;
    }
}
