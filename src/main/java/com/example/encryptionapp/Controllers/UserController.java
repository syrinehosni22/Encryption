package com.example.encryptionapp.Controllers;

import com.example.encryptionapp.Entities.User;
import com.example.encryptionapp.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public User add(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("/signIn")
    public User signIn(@RequestPart("email") String email,
                         @RequestPart("password") String password) {
        return userService.signIn(email, password);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @PostMapping("/email/verification")
    public boolean userEmailExist(@RequestBody String email){
        return userService.userEmailExist(email);
    }
}
