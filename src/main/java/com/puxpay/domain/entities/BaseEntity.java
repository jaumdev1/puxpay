package com.puxpay.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDateTime createdAt;
}
