package com.fererlab.city.serviceengine;


import com.fererlab.city.serviceengine.dto.CityDTO;
import com.fererlab.core.interceptor.ContractException;

import javax.ejb.Local;

@Local
public interface CityServiceEngine {

    CityDTO create(String name) throws ContractException;

    CityDTO update(Integer id, String name) throws ContractException;

}
