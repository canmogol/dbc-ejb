package com.fererlab.city.serviceengine;


import com.fererlab.city.serviceengine.dto.CityDTO;

import javax.ejb.Local;

@Local
public interface CityServiceEngine {

    CityDTO create(String name) throws Exception;

    CityDTO update(Integer id, String name) throws Exception;

}
