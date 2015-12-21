package dao;

import domain.Entity;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mmwaikar on 27-11-2015.
 */
public abstract class GraphDao<T> {
    /*
     * Variable depth persistence means you can vary the depth of fetches
     * depending on the shape of your data and application.
     *
     * The default save depth is -1, or everything that has been modified and
     * can be reached from the entity up to an infinite depth. This means we can
     * persist all our changes in one go.
     *
     * Loading relationships is not required however when listing all entities
     * of a type. We merely require the id and name of the entity, and so a
     * depth of 0 is used by findAll to only load simple properties of the
     * entity but skip its relationships.
     *
     */
    private static final int DEPTH_LIST = 1;

    /*
     * The default depth is 1, which loads simple properties of the entity and
     * its immediate relations. This is sufficient for the find method, which is
     * used in the application to present a create or edit form for an entity.
     *
     */
    private static final int DEPTH_ENTITY = 1;

    protected Session session;

    public GraphDao(String url, String username, String password) {
        super();
        session = Neo4jSessionFactory.getInstance().getNeo4jSession(url, username, password);
    }

    /**
     * This method finds all the entities of a particular type T in the Neo4j
     * data store.
     *
     * @return all the records of a particular (node or relationship) type as an
     *         Iterable.
     */
    public Iterable<T> findAll() {
        return findAll(DEPTH_LIST);
    }

    public Iterable<T> findAll(int depthList) {
        return session.loadAll(getEntityType(), depthList);
    }

    /**
     * This method finds a particular (node or relationship) type based in its
     * primary key id.
     *
     * @param id
     *            The primary key value.
     * @return A node or relationship type.
     */
    public T findOne(Long id) {
        return findOne(id, DEPTH_ENTITY);
    }

    public T findOne(Long id, int depthEntity) {
        return session.load(getEntityType(), id, depthEntity);
    }

    /**
     * Using this method, one can directly fire a Cypher query against the Neo4j
     * data store to find some nodes or relationships.
     *
     * For ex, if the Cypher query is "MATCH (n: domain.Person) where n.name = {name}
     * RETURN n;
     *
     * then the parameters map should contain a key called name and the value
     * should represent the name we are searching for.
     *
     * @param searchQuery
     *            The Cypher query to be run.
     * @param parameters
     *            The parameters to the Cypher query.
     * @return A list of all matching nodes (and / or relationships) as an
     *         Iterable.
     */
    public Iterable<T> findAllByQuery(String searchQuery, Map<String, String> parameters) {
        return session.query(getEntityType(), searchQuery, parameters);
    }

    /**
     * Deletes a (node or relationship) type based in its primary key id.
     *
     * @param entity
     *            Uses this entity's id value to delete that particular (node or
     *            relationship) type.
     */
    public void delete(T entity) {
        session.delete(session.load(getEntityType(), ((Entity) entity).getPkId()));
    }

    /**
     * Using this method, one can directly fire a Cypher query against the Neo4j
     * data store to delete some nodes or relationships.
     *
     * @param searchQuery
     *            The Cypher query to be run.
     * @param parameters
     *            The parameters to the Cypher query.
     * @return A list of all deleted nodes (and / or relationships) as an
     *         Iterable.
     */
    public long deleteAllByQuery(String searchQuery, Map<String, String> parameters) {
        List<T> deletedEntities = new ArrayList<T>();
        Iterable<T> entities = findAllByQuery(searchQuery, parameters);
        Transaction tx = session.beginTransaction();

        try {
            entities.forEach(e -> {
                session.delete(session.load(getEntityType(), ((Entity) e).getPkId()));
                deletedEntities.add(e);
            });
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
        tx.close();

        return deletedEntities.size();
    }

    /**
     * This method saves an object if it was not already saved in the Neo4j data
     * store i.e. if the object is a new object and it doesn't have a primary
     * key value set for the id property. If the object's primary key value for
     * the id property is already set, then this method updates it.
     *
     * @param entity
     *            The entity to save or update.
     * @return The saved or updated entity.
     */
    public T createOrUpdate(T entity) {
        session.save(entity, DEPTH_ENTITY);
        return findOne(((Entity) entity).getPkId());
    }

    /**
     * This method saves or updates the entities in the Neo4j data store.
     *
     * @param entities
     *            The entities to save or update.
     * @return The list of saved or updated entities.
     */
    public List<T> createOrUpdateAll(List<T> entities) {
		/*
		 * NOTE: The session.save method below creates an implicit transaction.
		 * Hence while saving multiple nodes (or relationships), create a
		 * transaction and commit only when all of them have been successfully
		 * saved, or roll it back in case of any problems.
		 */
        List<T> savedEntities = new ArrayList<T>();
        Transaction tx = session.beginTransaction();

        try {
            entities.forEach(e -> {
                session.save(e, DEPTH_ENTITY);
                T savedEntity = findOne(((Entity) e).getPkId());
                savedEntities.add(savedEntity);
            });
            tx.commit();
        } catch (Exception e) {
            savedEntities.clear();
            tx.rollback();
        }
        tx.close();

        return savedEntities;
    }

    public Iterable<T> addLabels(String cypherQuery, Map<String, String> parameters) {
        Iterable<T> savedEntities = new ArrayList<T>();
        Transaction tx = session.beginTransaction();

        try {
            savedEntities = session.query(getEntityType(), cypherQuery, parameters);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
        tx.close();

        return savedEntities;
    }

    public abstract Class<T> getEntityType();
}
