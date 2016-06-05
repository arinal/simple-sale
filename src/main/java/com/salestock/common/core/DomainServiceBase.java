package com.salestock.common.core;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class DomainServiceBase<E extends EntityBase>  {
    private Class<E> entityClass;
    protected Repository<E> repository;

    public DomainServiceBase(Class<E> entityClass, Repository<E> repository) {
        this.entityClass = entityClass;
        this.repository = repository;
    }

    public E create() {
        try {
            return entityClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<E> getById(int id) {
        return repository.getById(id);
    }

    public Page<E> getAll(String search, Pageable page) {
        return search == null || search.isEmpty()?
                repository.findAll(page) : repository.findAll(search, page);
    }

    public E save(E entity) {
        return repository.save(entity);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}
