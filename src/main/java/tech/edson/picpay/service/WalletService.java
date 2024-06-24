package tech.edson.picpay.service;

import org.springframework.stereotype.Service;
import tech.edson.picpay.controller.dto.CreateWalletDto;
import tech.edson.picpay.entity.Wallet;
import tech.edson.picpay.exception.WalletDataAlreadyExistsException;
import tech.edson.picpay.repository.WalletRepository;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletDto dto) {
        var walletDb = walletRepository.findByCpfCnpjOrEmail(dto.cpfCnpj(), dto.email());
        if(walletDb.isPresent()) {
            throw new WalletDataAlreadyExistsException("CpfCnpj or Email already exists");
        }
        return walletRepository.save(dto.toWallet());
    }
}
