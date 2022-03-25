package com.anilakdemir.softtechfinalproject.usr.dao;

import com.anilakdemir.softtechfinalproject.usr.entity.UsrUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author anilakdemir
 */
@Repository
public interface UsrUserDao extends JpaRepository<UsrUser, Long> {

    boolean existsByUsername (String username);

    Optional<UsrUser> findByUsername (String username);
}
