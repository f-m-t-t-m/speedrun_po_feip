package ru.fefu.ecommerceapi.repository;

import org.springframework.data.repository.CrudRepository;
import ru.fefu.ecommerceapi.entity.ActivationCode;

import java.util.Optional;

public interface ActivationCodeRepository extends CrudRepository<ActivationCode, Long> {

    Optional<ActivationCode> findActivationCodeByCodeAndUserPhone(String code, String phone);

    Optional<ActivationCode> findActivationCodeByUserPhone(String phone);

}
