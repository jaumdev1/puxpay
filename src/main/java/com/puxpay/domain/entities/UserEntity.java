package com.puxpay.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import com.puxpay.domain.enums.UserType;

@Entity
@Table(name = "users", schema = "transaction")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    private String name;
    private String document;
    private String email;
    private String password;
    private UserType userType;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private WalletEntity wallet;

    public boolean isMerchant() {
        return this.userType == UserType.MERCHANT;
    }

    public UserEntity(String name, String document, String email, String password, UserType userType) {
        this.name = name;
        this.document = document;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }
}
