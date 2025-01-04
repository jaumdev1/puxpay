package com.puxpay.features.users.presentation.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Serdeable
@Introspected
public class CreateUserRequest {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome não pode ter mais de 100 caracteres")
    private String name;

    @NotBlank(message = "O documento (CPF ou CNPJ) é obrigatório")
    @Pattern(
            regexp = "\\d{11}|\\d{14}",
            message = "O documento deve conter 11 dígitos para CPF ou 14 dígitos para CNPJ"
    )
    private String document;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O e-mail deve ser válido")
    @Size(max = 100, message = "O e-mail não pode ter mais de 100 caracteres")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
            message = "A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial"
    )
    private String password;

    private boolean merchant;
}
