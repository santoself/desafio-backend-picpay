package tech.edson.picpay.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.edson.picpay.controller.dto.TransferDto;
import tech.edson.picpay.entity.Transfer;
import tech.edson.picpay.entity.Wallet;
import tech.edson.picpay.exception.InsufficientBalanceException;
import tech.edson.picpay.exception.TransferNotAllowedForWalletTypeException;
import tech.edson.picpay.exception.TransferNotAuthorizedException;
import tech.edson.picpay.exception.WalletNotFoundException;
import tech.edson.picpay.repository.TransferRepository;
import tech.edson.picpay.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Service
public class TransferService {

    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;
    private final TransferRepository transferRepository;
    private final WalletRepository walletRepository;

    public TransferService(AuthorizationService authorizationService, NotificationService notificationService, TransferRepository transferRepository, WalletRepository walletRepository) {
        this.authorizationService = authorizationService;
        this.notificationService = notificationService;
        this.transferRepository = transferRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Transfer transfer(TransferDto transferDto) {


        var sender = walletRepository.findById(transferDto.payer())
                .orElseThrow(() -> new WalletNotFoundException("There is no wallet with id "+ transferDto.payer() +"."));
        var receiver = walletRepository.findById(transferDto.payee())
                .orElseThrow(() -> new WalletNotFoundException("There is no wallet with id "+ transferDto.payee() +"."));

        validateTransfer(transferDto, sender);

        sender.debit(transferDto.value());
        receiver.credit(transferDto.value());

        var transfer = new Transfer(sender, receiver, transferDto.value());

        walletRepository.save(sender);
        walletRepository.save(receiver);
        var transitResult = transferRepository.save(transfer);

        CompletableFuture.runAsync(() -> notificationService.sendNotification(transitResult));
        return transitResult;

         // return new Transfer();


    }

    private void validateTransfer(TransferDto transferDto, Wallet sender) {
        if(!sender.isTransferAllowedForWalletType()) {
            throw new TransferNotAllowedForWalletTypeException();
        }
        if(!sender.isBalancerEqualOrGreaterThan(transferDto.value())) {
            throw new InsufficientBalanceException();
        }
        if(!authorizationService.isAuthorized(transferDto)) {
            throw new TransferNotAuthorizedException();
        }
    }
}
