package com.fererlab.city.service;

import com.fererlab.city.model.City;
import com.fererlab.city.serviceengine.CityServiceEngine;
import com.fererlab.city.serviceengine.dto.CityDTO;
import com.fererlab.core.annotation.Contracted;
import com.fererlab.core.annotation.Ensures;
import com.fererlab.core.annotation.Invariant;
import com.fererlab.core.annotation.Requires;
import com.fererlab.core.interceptor.DBCInterceptor;
import com.fererlab.core.service.GenericService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

@Contracted
@Invariant({
        "bean.shouldReturnEmptyCityDTO() != null",
        "bean.shouldReturnEmptyCityDTO().name == null"
})
@Interceptors({DBCInterceptor.class})
@Stateless(name = "CityServiceEngineImpl", mappedName = "CityServiceEngineImpl")
public class CityServiceEngineImpl implements CityServiceEngine {

    public CityDTO shouldReturnEmptyCityDTO() {
        return new CityDTO();
    }

    @EJB(name = "CityCopyStrategy")
    private CityCopyStrategy copyStrategy;

    @EJB(name = "GenericServiceImpl")
    private GenericService<City, Integer> cityService;

    @Requires({
            "params[0]!=null",
            "params[0]!=\"\""
    })
    @Ensures({
            "result!=null",
            "result.name==params[0]"
    })
    @Override
    public CityDTO create(String name) throws Exception {

        // create city object
        City city = new City();
        city.setName(name);
        // persist object
        cityService.create(city);
        // create DTO and copy entity
        CityDTO dto = new CityDTO();
        copyStrategy.copy(city, dto);

        return dto;
    }

    @Requires({
            "params[0]!=null",
            "params[0]>0",
            "params[1]!=null",
            "params[1]!=\"\""
    })
    @Ensures({
            "result!=null",
            "result.id==params[0]",
            "result.name==params[1]"
    })
    @Override
    public CityDTO update(Integer id, String name) throws Exception {
        City city = cityService.findById(City.class, id);
        city.setName(name);
        cityService.update(city);
        CityDTO dto = new CityDTO();
        copyStrategy.copy(city, dto);
        return dto;
    }

}
