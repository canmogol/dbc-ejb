package com.fererlab.city.serviceengine.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CityDTO extends BaseDTO {

    private Integer id;
    private String name;

    public CityDTO() {
        super(StatusEnum.SUCCESS);
    }

    public CityDTO(StatusEnum statusEnum) {
        super(statusEnum);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
