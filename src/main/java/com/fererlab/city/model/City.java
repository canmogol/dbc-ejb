package com.fererlab.city.model;

import com.fererlab.core.model.BaseModelID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "CITY_TABLE")
public class City extends BaseModelID<Integer> {

    private static final long serialVersionUID = 3095959829066806345L;

    private String name;

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    @Column(name = "CT_NAME", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
