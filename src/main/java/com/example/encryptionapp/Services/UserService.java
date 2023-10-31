package com.example.encryptionapp.Services;

import com.example.encryptionapp.Entities.User;

public interface UserService {
    User addUser(User user);

    User signIn(String email, String password);

    User getUserById(Long id);
    boolean userEmailExist(String email);
}
