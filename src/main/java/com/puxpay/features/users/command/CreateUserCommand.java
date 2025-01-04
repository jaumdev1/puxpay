package com.puxpay.features.users.command;

import com.puxpay.domain.enums.UserType;


public record CreateUserCommand(String name,
                                String document,
                                String email,
                                String password,
                                UserType userType) {
}