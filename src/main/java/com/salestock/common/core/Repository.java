package com.salestock.common.core;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface Repository<E extends EntityBase> extends PagingAndSortingRepository<E, Integer> {
    Optional<E> getById(int id);
    Page<E> findAll(String search, Pageable page);
}
