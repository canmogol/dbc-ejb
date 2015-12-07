package com.fererlab.core.dao;

import com.fererlab.core.model.BaseModel;
import com.fererlab.core.session.UserBean;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;


public abstract class AbstractDAO<T extends Serializable, PK> implements BaseDAO<T, PK> {
    @EJB
    private UserBean userBean;

    private Class<T> entityClass;

    public abstract EntityManager getEntityManager();

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        if (this instanceof GenericDAO) {
            entityClass = (Class<T>) BaseModel.class;
        } else {
            entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void create(T t) {
        getEntityManager().persist(t);
    }

    @Override
    public void update(T t) {
        getEntityManager().merge(t);
    }

    @Override
    public void delete(T t) {
        getEntityManager().remove(t);
    }

    @Override
    public void delete(PK id) {
        T t = getEntityManager().getReference(entityClass, id);
        getEntityManager().remove(t);
    }

    @Override
    public T findById(PK id) {
        T t = getEntityManager().find(entityClass, id);
        return t;
    }

    @Override
    public List<T> list() {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        List<T> list = getEntityManager().createQuery(criteriaQuery).getResultList();
        return list;
    }


    public List<T> list(Integer index, Integer numberOfRecords) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        List<T> list = getEntityManager().createQuery(criteriaQuery).setFirstResult(index).setMaxResults(numberOfRecords)
                .getResultList();
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PK count() {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<PK> criteriaQuery = (CriteriaQuery<PK>) builder.createQuery();
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select((Selection<? extends PK>) builder.count(root));
        PK count = getEntityManager().createQuery(criteriaQuery).getSingleResult();
        return count;
    }
}
