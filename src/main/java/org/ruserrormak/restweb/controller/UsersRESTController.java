package org.ruserrormak.restweb.controller;

import org.ruserrormak.restweb.model.Users;
import org.ruserrormak.restweb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UsersRESTController {


    private final UsersService userService;

    @Autowired
    public UsersRESTController(UsersService userService) { this.userService = userService; }

    @GetMapping("/info")
    @ResponseBody
    public Users showUserInfo(Authentication authentication) {
        return userService.findByUsername(authentication.getName());
    }
}
