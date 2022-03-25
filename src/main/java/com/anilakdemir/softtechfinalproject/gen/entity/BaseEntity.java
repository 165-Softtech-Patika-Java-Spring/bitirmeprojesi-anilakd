package com.anilakdemir.softtechfinalproject.gen.entity;

import lombok.Data;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author anilakdemir
 */
@MappedSuperclass
@Data
public abstract class BaseEntity implements BaseModel, Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Embedded
    private BaseAdditionalFields baseAdditionalFields;
}
