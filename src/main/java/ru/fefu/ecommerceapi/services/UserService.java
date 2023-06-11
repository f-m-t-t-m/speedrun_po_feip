package ru.fefu.ecommerceapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.fefu.ecommerceapi.dto.auth.UserDto;
import ru.fefu.ecommerceapi.repository.UserRepository;
import ru.fefu.ecommerceapi.services.pagination.PaginationService;

@Service
@RequiredArgsConstructor
public class UserService extends PaginationService<UserDto> implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByPhone(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Can't find user with username: %s", username)));
    }
}
