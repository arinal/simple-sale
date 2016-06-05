package com.salestock.common.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.salestock.common.core.EntityBase;

import static com.salestock.common.core.IterableCommon.*;

public class HateoasAssembler<E extends EntityBase> extends ResourceAssemblerSupport<E, Resource> {

    Class<?> controllerClass;

    public HateoasAssembler(Class<?> controllerClass) {
        super(controllerClass, Resource.class);
        this.controllerClass = controllerClass;
    }

    public List<Resource> toResources(Iterable<? extends E> entities) {
        return stream(entities)
            .map(e -> new Resource(e, createResourceWithId(e.getId(), e).getLink("self")))
            .collect(Collectors.toList());
    }

    @Override
    public Resource toResource(E entity) {
        return new Resource(entity, ControllerLinkBuilder.linkTo(controllerClass)
                            .slash(Integer.valueOf(entity.getId()))
                            .withSelfRel());
    }
}
