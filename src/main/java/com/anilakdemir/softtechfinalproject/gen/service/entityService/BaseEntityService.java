package com.anilakdemir.softtechfinalproject.gen.service.entityService;

import com.anilakdemir.softtechfinalproject.gen.entity.BaseAdditionalFields;
import com.anilakdemir.softtechfinalproject.gen.entity.BaseEntity;
import com.anilakdemir.softtechfinalproject.sec.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author anilakdemir
 */
@Service
@RequiredArgsConstructor
public abstract class BaseEntityService<E extends BaseEntity, D extends JpaRepository<E, Long>> {

    private final D dao;

    private AuthenticationService authenticationService;

    @Autowired
    public void setAuthenticationService(@Lazy AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    public E save (E entity) {
        setAdditionalFields(entity);
        entity = dao.save(entity);
        return entity;
    }

    private void setAdditionalFields (E entity) {
        BaseAdditionalFields baseAdditionalFields = entity.getBaseAdditionalFields();

        Long currentCustomerId = getCurrentCustomerId();

        if (baseAdditionalFields == null){
            baseAdditionalFields = new BaseAdditionalFields();
            entity.setBaseAdditionalFields(baseAdditionalFields);
        }

        if (entity.getId() == null){
            baseAdditionalFields.setCreateDate(new Date());
            baseAdditionalFields.setCreatedBy(currentCustomerId);
        }

        baseAdditionalFields.setUpdateDate(new Date());
        baseAdditionalFields.setUpdatedBy(currentCustomerId);
        baseAdditionalFields.setCreatedBy(currentCustomerId);
    }

    public D getDao () {
        return dao;
    }

    public Long getCurrentCustomerId() {
        Long currentCustomerId = authenticationService.getCurrentCustomerId();
        return currentCustomerId;
    }

}
