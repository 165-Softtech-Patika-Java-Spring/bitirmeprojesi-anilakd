package com.anilakdemir.softtechfinalproject.usr.service.entityService;

import com.anilakdemir.softtechfinalproject.gen.exceptions.DuplicateException;
import com.anilakdemir.softtechfinalproject.gen.exceptions.EntityNotFoundException;
import com.anilakdemir.softtechfinalproject.gen.service.entityService.BaseEntityService;
import com.anilakdemir.softtechfinalproject.usr.dao.UsrUserDao;
import com.anilakdemir.softtechfinalproject.usr.entity.UsrUser;
import com.anilakdemir.softtechfinalproject.usr.exceptions.UsrUserErrorMessage;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author anilakdemir
 */
@Service
public class UsrUserEntityService extends BaseEntityService<UsrUser, UsrUserDao> {


    public UsrUserEntityService (UsrUserDao dao) {
        super(dao);
    }

    public UsrUser saveWithControlUsername (UsrUser usrUser) {

        boolean isUniqueUsername = !getDao().existsByUsername(usrUser.getUsername());

        if (!isUniqueUsername){
            throw new DuplicateException(UsrUserErrorMessage.USERNAME_ALREADY_EXIST);
        }

        usrUser = save(usrUser);

        return usrUser;
    }

    public UsrUser findById (Long userId) {

        return getDao().findById(userId).orElseThrow(() -> new EntityNotFoundException(UsrUserErrorMessage.USER_NOT_FOUND));
    }


    public void deleteById (Long id) {

        getDao().deleteById(id);
    }

    public boolean existsById (Long id) {
        return getDao().existsById(id);
    }

    public Optional<UsrUser> findByUsername (String username) {

        return getDao().findByUsername(username);
    }

    public UsrUser findByUsernameWithControl (String username) {
        Optional<UsrUser> optionalUsrUser = findByUsername(username);

        if (!optionalUsrUser.isPresent()){
            throw new EntityNotFoundException(UsrUserErrorMessage.USER_NOT_FOUND);
        }else{
            return optionalUsrUser.get();
        }
    }
}
