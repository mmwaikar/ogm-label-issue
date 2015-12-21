package dao;

import domain.Person;

/**
 * Created by mmwaikar on 27-11-2015.
 */
public class PersonDao extends GraphDao<Person> {
    public PersonDao(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public Class<Person> getEntityType() {
        return Person.class;
    }
}
