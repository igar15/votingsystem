package ru.igar15.rest_voting_system.model;

public abstract class AbstractNamedEntity extends AbstractBaseEntity {

    private String name;

    public AbstractNamedEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
