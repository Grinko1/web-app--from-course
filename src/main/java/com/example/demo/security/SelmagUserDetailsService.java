package com.example.demo.security;

import com.example.demo.entity.Authority;
import com.example.demo.repository.SelmagUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SelmagUserDetailsService implements UserDetailsService {
    private final SelmagUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).map(user -> User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities()
                        .stream().map(Authority::getAuthority)
                        .map(SimpleGrantedAuthority::new)
                        .toList())

                .build()).orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(username)));
    }
}