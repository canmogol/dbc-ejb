package com.fererlab.city.service;

import com.fererlab.city.model.City;
import com.fererlab.city.serviceengine.dto.CityDTO;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless(name = "CityCopyStrategy", mappedName = "CityCopyStrategy")
public class CityCopyStrategy {

    public void copy(City from, CityDTO to) {
        to.setId(from.getId());
        to.setName(from.getName());
    }

}
