package tech.edson.picpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.edson.picpay.entity.Wallet;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByCpfCnpjOrEmail(String cpfCnpj, String email);


}
