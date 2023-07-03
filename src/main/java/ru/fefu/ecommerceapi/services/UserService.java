package ru.fefu.ecommerceapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.fefu.ecommerceapi.dto.auth.UserDto;
import ru.fefu.ecommerceapi.dto.pagination.PaginationParams;
import ru.fefu.ecommerceapi.mappers.UserMapper;
import ru.fefu.ecommerceapi.repository.UserPagingRepository;
import ru.fefu.ecommerceapi.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserPagingRepository userPagingRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByPhone(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Не найден пользователь с телефоном: %s", username)));
    }

    public Page<UserDto> getPageDto(PaginationParams paginationParams) {
        Pageable pageable = PageRequest.of(paginationParams.getCurrentPage()-1,
                paginationParams.getItemsOnPage());
        return userPagingRepository.findAll(pageable).map(userMapper::entityToDto);
    }

}
