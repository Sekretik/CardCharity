package com.cardcharity.base;

import java.util.List;
import java.util.Optional;

public interface IDao<E> {

    public Optional<E> findById(long id);

    public List<E> findAll();

    public void update(E object) throws Exception;

    public void save(E object) throws Exception;
}
