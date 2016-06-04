package com.salestock.common;

public abstract class EntityBase {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
