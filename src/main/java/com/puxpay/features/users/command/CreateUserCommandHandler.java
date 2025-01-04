package com.puxpay.features.users.command;

import com.puxpay.domain.entities.UserEntity;
import com.puxpay.errorhandling.exceptions.BadRequestException;
import com.puxpay.features.users.infra.UserRepository;
import com.puxpay.features.wallets.command.CreateWalletCommand;
import com.puxpay.features.wallets.command.CreateWalletCommandHandler;

import io.micronaut.runtime.http.scope.RequestScope;
import io.micronaut.transaction.annotation.Transactional;

import jakarta.inject.Singleton;

@RequestScope
public class CreateUserCommandHandler {

    private final UserRepository userRepository;
    private final CreateWalletCommandHandler createWalletCommandHandler;

    public CreateUserCommandHandler(UserRepository userRepository, CreateWalletCommandHandler createWalletCommandHandler) {
        this.userRepository = userRepository;
        this.createWalletCommandHandler = createWalletCommandHandler;
    }

    @Transactional
    public UserEntity handle(CreateUserCommand command) {

        if (userRepository.existsByEmail(command.email())) {
            throw new BadRequestException("Email já está em uso");
        }
        if (userRepository.existsByDocument(command.document())) {
            throw new BadRequestException("CPF já está em uso");
        }

        UserEntity entity = new UserEntity(
                command.name(),
                command.document(),
                command.email(),
                command.password(),
                command.userType()
        );
        userRepository.save(entity);

       entity.setWallet(createWalletCommandHandler.handle(new CreateWalletCommand(entity.getId())));

        return entity;
    }
}
