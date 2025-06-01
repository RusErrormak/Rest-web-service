package org.ruserrormak.restweb.service;

import org.ruserrormak.restweb.model.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UsersService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    public Users findByEmail(String email);

    Users findByUsername(String username);

    List<Users> allUsers();

    void saveUser(Users user);

    void deleteUser(Long Id);

    Users getById(Long id);

    void update(Users user);



}
