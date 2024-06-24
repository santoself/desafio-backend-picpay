package tech.edson.picpay.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.edson.picpay.controller.dto.CreateWalletDto;
import tech.edson.picpay.entity.Wallet;
import tech.edson.picpay.service.WalletService;

@RestController
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/wallets")
    public ResponseEntity<Wallet> createWallet(@RequestBody @Valid CreateWalletDto walletDto) {
        var wallet = walletService.createWallet(walletDto);

        return ResponseEntity.ok(wallet);
    }
}
