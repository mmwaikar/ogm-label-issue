package domain;

import org.neo4j.ogm.annotation.GraphId;

/**
 * Created by mmwaikar on 27-11-2015.
 */
public abstract class Entity {
    @GraphId
    private Long pkId;

    public Long getPkId() {
        return pkId;
    }

    public void setPkId(Long pkId) {
        this.pkId = pkId;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "pkId=" + pkId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || pkId == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;
        if (!pkId.equals(entity.pkId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (pkId == null) ? -1 : pkId.hashCode();
    }
}
