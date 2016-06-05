package com.salestock.common.rest;

import java.net.URI;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.salestock.common.core.DomainServiceBase;
import com.salestock.common.core.EntityBase;

public abstract class RestControllerBase<E extends EntityBase> {
    private final ResourceAssemblerSupport entityAssembler;
    private final DomainServiceBase<E> service;

    protected RestControllerBase(DomainServiceBase<E> service) {
        this.service = service;
        this.entityAssembler = new HateoasAssembler<>(this.getClass());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResourceSupport get(@PathVariable int id) {
        E entity = id == 0? service.create() :
            this.service.getById(id)
            .orElseThrow(() -> new NotFoundException(id));
        return entityAssembler.toResource(entity);
    }

    @RequestMapping(method = RequestMethod.GET)
    PagedResources<E> getAll(@RequestParam(required = false) String search,
                             Pageable page, PagedResourcesAssembler assembler) {
        Page entities = service.getAll(search, page);
        return assembler.toResource(entities, entityAssembler);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.PUT)
    ResponseEntity put(@RequestBody E entity) {
        HttpHeaders headers = save(entity);
        return new ResponseEntity(headers, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(method=RequestMethod.POST)
    ResponseEntity post(@RequestBody E entity) {
        if (!entity.isTransient())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id should 0");
        HttpHeaders headers = save(entity);
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    HttpHeaders save(E entity) {
        int id = entity.getId();
        E saved = service.save(entity);
        Link link = entityAssembler.toResource(saved).getLink("self");
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(link.getHref()));
        return headers;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity delete(@PathVariable int id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
