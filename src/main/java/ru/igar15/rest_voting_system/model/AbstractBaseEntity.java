package ru.igar15.rest_voting_system.model;

public abstract class AbstractBaseEntity {

    private Integer id;

    public AbstractBaseEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
