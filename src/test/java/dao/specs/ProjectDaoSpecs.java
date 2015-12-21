package dao.specs;

import dao.ProjectDao;
import domain.Project;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by mmwaikar on 02-12-2015.
 */
public class ProjectDaoSpecs {
    private ProjectDao projectDao;

    @Before
    public void setUp() throws Exception {
        projectDao = new ProjectDao("http://localhost:7474", "neo4j", "neo4j123");
    }

    @Test
    public void should_be_able_to_add() {
        Project p1 = new Project("p1");
        save(p1);

        Project p2 = new Project("p2");
        save(p2);
    }

    private void save(Project project) {
        Project saved = projectDao.createOrUpdate(project);
        assertThat("project is null", saved, is(notNullValue()));
        assertThat("project name does not match", saved.getName(), is(project.getName()));
    }
}
