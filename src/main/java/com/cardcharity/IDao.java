package com.cardcharity;

import com.cardcharity.exception.QueryException;

import java.util.List;

public interface IDao<Entity> {

    public void save(Entity entity) throws QueryException;

    public void update(Entity entity) throws QueryException;

    public Entity findById(Long entityId);

    public List<Entity> findAll();


}
