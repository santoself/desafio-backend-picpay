package tech.edson.picpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.edson.picpay.entity.WalletType;

public interface WalletTypeRepository extends JpaRepository<WalletType, Long> {
}
