package com.salestock.common.core;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class EntityBase {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTransient() {
        return id == 0;
    }

    @Override
    public boolean equals(Object that) {
        if (!that.getClass().equals(this.getClass()))
            return false;
        EntityBase thatEntity = (EntityBase) that;
        return this.id == thatEntity.id;
    }

    @Override
    public int hashCode() {
        return (getClass().toString() + id).hashCode();
    }
}
