package ru.fefu.ecommerceapi.repository;

import org.springframework.data.repository.CrudRepository;
import ru.fefu.ecommerceapi.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByPhone(String phone);

}
