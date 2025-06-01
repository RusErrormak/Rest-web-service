package org.ruserrormak.restweb.controller;


import org.ruserrormak.restweb.model.Users;
import org.ruserrormak.restweb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRESTController {


    private final UsersService userService;

    @Autowired
    public AdminRESTController(UsersService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<Users> showUsers() {
        return userService.allUsers();
    }

    @DeleteMapping("/delete/{id}")
    public List<Users> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return userService.allUsers();
    }

    @PutMapping("/update/{id}")
    public Users updateUser(@PathVariable Long id, @RequestBody Users user) {
        user.setId(id);
        userService.update(user);
        return user;
    }

    @PostMapping("/newUser")
    public Users createUser(@RequestBody Users user) {
        userService.saveUser(user);
        return user;
    }

}
