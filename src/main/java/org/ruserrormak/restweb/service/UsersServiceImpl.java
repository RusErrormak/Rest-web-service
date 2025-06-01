package org.ruserrormak.restweb.service;

import org.ruserrormak.restweb.model.Roles;
import org.ruserrormak.restweb.model.Users;
import org.ruserrormak.restweb.repository.RolesRepository;
import org.ruserrormak.restweb.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    private final RolesRepository rolesRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository,
                            RolesRepository rolesRepository,
                            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        return user;
    }

    @Transactional
    @Override
    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public Users findByEmail(String email) { return usersRepository.findByEmail(email); }

    @Transactional
    @Override
    public List<Users> allUsers() {
        return usersRepository.findAll();
    }

    @Transactional
    @Override
    public void saveUser(Users user) {

        Users newUser = new Users();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Set<Roles> userRoles = new HashSet<>();

        Roles userRole = rolesRepository.findByName("ROLE_USER");
        if (userRole != null) {
            userRoles.add(userRole);
        }

        if (user.getRoles() != null) {
            for (Roles role : user.getRoles()) {
                Roles existingRole = rolesRepository.findByName(role.getName());
                if (existingRole != null) {
                    userRoles.add(existingRole);
                }
            }
        }

        newUser.setRoles(userRoles);
        usersRepository.save(newUser);

    }

    @Transactional
    @Override
    public void deleteUser(Long Id) {
        if (usersRepository.findById(Id).isPresent()) {
            usersRepository.deleteById(Id);
        }
    }

    @Transactional
    @Override
    public Users getById(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));    }

    @Transactional
    @Override
    public void update(Users user) {
        Users existingUser =  usersRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user.getId()));

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        Set<Roles> userRoles = new HashSet<>();

        Roles userRole = rolesRepository.findByName("ROLE_USER");
        if (userRole != null) {
            userRoles.add(userRole);
        }

        if (user.getRoles() != null) {
            for (Roles role : user.getRoles()) {
                Roles existingRole = rolesRepository.findByName(role.getName());
                if (existingRole != null) {
                    userRoles.add(existingRole);
                }
            }
        }

        existingUser.setRoles(userRoles);
        usersRepository.save(existingUser);
    }

}
