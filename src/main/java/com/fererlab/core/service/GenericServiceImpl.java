package com.fererlab.core.service;

import com.fererlab.core.annotation.Contracted;
import com.fererlab.core.annotation.Ensures;
import com.fererlab.core.annotation.Invariant;
import com.fererlab.core.annotation.Requires;
import com.fererlab.core.dao.GenericDAO;
import com.fererlab.core.interceptor.DBCInterceptor;
import com.fererlab.core.model.BaseModel;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.List;

@Contracted
@Invariant({
        "bean.getBaseDAO() != null"
})
@Interceptors({DBCInterceptor.class})
@Stateless(name = "GenericServiceImpl", mappedName = "GenericServiceImpl")
public class GenericServiceImpl<T extends BaseModel<?>, PK> implements GenericService<T, PK> {

    @EJB(beanName = "GenericDAOImpl")
    private GenericDAO<T, PK> genericDAO;

    @Override
    public GenericDAO<T, PK> getBaseDAO() {
        return genericDAO;
    }

    @Requires({
            "params[0]!=null"
    })
    @Override
    public void create(T t) {
        getBaseDAO().create(t);
    }

    @Requires({
            "params[0]!=null"
    })
    @Override
    public void update(T t) {
        getBaseDAO().update(t);
    }

    @Override
    public void delete(T t) {
        getBaseDAO().delete(t);
    }

    @Override
    public void delete(Class<T> t, PK id) {
        getBaseDAO().delete(t, id);
    }

    @Requires({
            "params[0]!=null",
            "params[1]!=null"
    })
    @Ensures({
            "result!=null",
            "result.id!=null"
    })
    @Override
    public T findById(Class<T> t, PK id) {
        return getBaseDAO().findById(t, id);
    }

    @Override
    public List<T> list(Class<T> t) {
        return getBaseDAO().list(t);
    }

    @Override
    public List<T> list(Class<T> t, Integer index, Integer numberOfRecords) {
        return getBaseDAO().list(t, index, numberOfRecords);
    }

    @Override
    public PK count(Class<T> t) {
        return getBaseDAO().count(t);
    }


}
