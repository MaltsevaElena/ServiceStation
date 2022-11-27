package com.maltseva.servicestation.project.service;

import java.util.List;

/**
 * @author Maltseva
 * @version 1.0
 * @since 27.11.2022
 */
public abstract class GenericService<T, N> {

    public abstract T updateFromEntity(T object);

    public abstract T updateFromDTO(N object, Long objectId);

    public abstract T createFromEntity(T newObject);

    public abstract T createFromDTO(N newObject);

    public abstract T getOne(final Long objectId);

    public abstract List<T> listAll();
}