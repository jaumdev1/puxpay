package com.puxpay.features.users.query;

import com.puxpay.domain.entities.UserEntity;
import com.puxpay.errorhandling.exceptions.NotFoundException;
import com.puxpay.features.users.infra.UserRepository;
import io.micronaut.runtime.http.scope.RequestScope;

@RequestScope
public class GetUserByIdQueryHandler {

    private final UserRepository userRepository;

    public GetUserByIdQueryHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
    }
}
