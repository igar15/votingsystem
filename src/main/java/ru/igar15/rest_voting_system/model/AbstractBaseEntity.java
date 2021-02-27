package ru.igar15.rest_voting_system.model;

public abstract class AbstractBaseEntity {

    private Long id;

    public AbstractBaseEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
