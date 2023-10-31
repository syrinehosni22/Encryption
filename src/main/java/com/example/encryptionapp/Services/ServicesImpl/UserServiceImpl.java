package com.example.encryptionapp.Services.ServicesImpl;

import com.example.encryptionapp.Entities.User;
import com.example.encryptionapp.Enum.Role;
import com.example.encryptionapp.Repositories.UserRepository;
import com.example.encryptionapp.Services.UserService;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AESEncryptionService aesEncryptionService;

    @Override
    public User addUser(User user) {
        user.setRole(Role.CLIENT);
        String salt = BCrypt.gensalt();
        user.setPassword(BCrypt.hashpw(user.getPassword(), salt));
        return userRepository.save(aesEncryptionService.encryptUser(user));
    }

    @Override
    public User signIn(String email, String password) {
        User user = userRepository.findByEmail(aesEncryptionService.encrypt(email));
        if (user != null && BCrypt.checkpw(password, user.getPassword()))
            return user;
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return aesEncryptionService.decryptUser(userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(
                "User not found"
        )));
    }

    @Override
    public boolean userEmailExist(String email) {
        if (userRepository.findByEmail(aesEncryptionService.encrypt(email)) != null)
            return true;
        return false;
    }
}
