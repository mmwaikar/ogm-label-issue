package dao.specs;

import dao.GroupDao;
import dao.ProjectDao;
import domain.Group;
import domain.Project;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by mmwaikar on 02-12-2015.
 */
public class GroupDaoSpecs {

    public static final long PID = 8L;
    public static final long P1_ID = 9L;
    public static final long P2_ID = 10L;
    public static final long ADMIN_ID = 6L;
    public static final long USER_ID = 7L;

    private GroupDao groupDao;
    private ProjectDao projectDao;

    @Before
    public void setUp() throws Exception {
        groupDao = new GroupDao("http://localhost:7474", "neo4j", "neo4j123");
        projectDao = new ProjectDao("http://localhost:7474", "neo4j", "neo4j123");
    }

    @Test
    public void should_be_able_to_add() {
        Group admin = new Group("admin");
        save(admin);

        Group users = new Group("users");
        save(users);
    }

    @Test
    public void should_be_able_to_add_project() {
        Project p1 = projectDao.findOne(P1_ID);
        assertThat("p1 is null", p1, is(notNullValue()));

        Project p2 = projectDao.findOne(P2_ID);
        assertThat("p2 is null", p2, is(notNullValue()));

        Group admin = groupDao.findOne(ADMIN_ID);
        assertThat("admin is null", admin, is(notNullValue()));

        Group users = groupDao.findOne(USER_ID);
        assertThat("users is null", users, is(notNullValue()));

        admin.addProject(p1);
        users.addProject(p2);

        save(admin);
        save(users);
    }

    private void save(Group group) {
        Group saved = groupDao.createOrUpdate(group);
        assertThat("group is null", saved, is(notNullValue()));
        assertThat("group name does not match", saved.getName(), is(group.getName()));
    }
}
