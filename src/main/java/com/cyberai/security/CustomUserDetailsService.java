package com.cyberai.security;

import com.cyberai.model.AppUser;
import com.cyberai.repository.AppUserRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

 private final AppUserRepository repo;

 public CustomUserDetailsService(AppUserRepository repo) {
  this.repo = repo;
 }

 @Override
 public UserDetails loadUserByUsername(String username)
         throws UsernameNotFoundException {

  AppUser u = repo.findByUsername(username)
          .orElseThrow(
                  () -> new UsernameNotFoundException(username)
          );

  return User.withUsername(u.getUsername())
          .password(u.getPassword())
          .disabled(!u.isEnabled())
          .authorities(
                  List.of(
                          new SimpleGrantedAuthority(
                                  "ROLE_" + u.getRole().name()
                          )
                  )
          )
          .build();
 }
}