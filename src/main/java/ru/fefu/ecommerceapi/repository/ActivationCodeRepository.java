package ru.fefu.ecommerceapi.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import ru.fefu.ecommerceapi.entity.ActivationCode;

public interface ActivationCodeRepository extends CrudRepository<ActivationCode, Long> {

    Optional<ActivationCode> findActivationCodeByCodeAndUserPhone(String code, String phone);

}
