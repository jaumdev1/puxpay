package com.puxpay.features.deposits.infra;

import com.puxpay.domain.entities.DepositEntity;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DepositRepository extends CrudRepository<DepositEntity, UUID> {
    @Query("SELECT d FROM DepositEntity d WHERE d.userId = :userId")
    List<DepositEntity> findByUserId(UUID userId);
}