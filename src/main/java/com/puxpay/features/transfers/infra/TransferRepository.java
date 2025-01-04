package com.puxpay.features.transfers.infra;

import com.puxpay.domain.entities.TransferEntity;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransferRepository extends CrudRepository<TransferEntity, UUID> {

    @Query("SELECT t FROM TransferEntity t WHERE t.userId = :userId OR t.payeeId = :userId")
    List<TransferEntity> findByUserId(UUID userId);
    Optional<TransferEntity> findByRequestId(UUID requestId);
}
