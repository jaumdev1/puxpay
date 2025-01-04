package com.puxpay.features.wallets.infra;

import com.puxpay.domain.entities.WalletEntity;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends CrudRepository<WalletEntity, UUID> {

    @Query("SELECT w FROM WalletEntity w WHERE w.user.id = :userId")
    Optional<WalletEntity> findByUserId(UUID userId);


    boolean existsByUser_Id(UUID userId);
}
