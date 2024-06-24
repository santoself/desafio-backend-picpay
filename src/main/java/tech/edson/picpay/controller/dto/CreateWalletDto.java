package tech.edson.picpay.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tech.edson.picpay.entity.Wallet;
import tech.edson.picpay.entity.WalletType;

public record CreateWalletDto(@NotBlank String fullName,
                              @NotBlank String cpfCnpj,
                              @NotBlank @Email String email,
                              @NotBlank String password,
                              @NotNull WalletType.Enum walletType) {
    public Wallet toWallet() {
        return new Wallet(fullName, cpfCnpj, email, password, walletType.get());
    }
}
