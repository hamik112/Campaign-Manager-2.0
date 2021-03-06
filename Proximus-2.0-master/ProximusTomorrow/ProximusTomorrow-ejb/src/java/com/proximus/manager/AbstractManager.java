/**
 * Copyright (c) 2010-2012 Proximus Mobility LLC
 */
package com.proximus.manager;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Ronald
 */
public abstract class AbstractManager<T>{

    private Class<T> entityClass;

    public AbstractManager(Class<T> entityClass)
    {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();
    
    public abstract void setEntityManager(EntityManager em);

    public void create(T entity)
    {
        getEntityManager().persist(entity);
    }

    /**
     * 
     * @param entity
     * @return The updated/created instance of the object
     */
    public T update(T entity)
    {
        return getEntityManager().merge(entity);
    }


    public void delete(T entity)
    {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id)
    {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll()
    {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range)
    {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count()
    {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
