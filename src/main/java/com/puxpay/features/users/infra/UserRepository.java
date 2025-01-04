package com.puxpay.features.users.infra;

import com.puxpay.domain.entities.UserEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    boolean existsByEmail(String email);
    boolean existsByDocument(String document);
}