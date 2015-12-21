package domain;

/**
 * Created by mmwaikar on 04-12-2015.
 */
public class Team extends Entity implements ITeam {
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Team() {}

    public Team(String name) {
        this.name = name;
    }
}
