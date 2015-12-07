package com.fererlab.city.serviceengine.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class BaseDTO {

    private StatusEnum statusEnum = StatusEnum.SUCCESS;

    public BaseDTO(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

    public StatusEnum getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

}
