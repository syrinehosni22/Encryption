package com.example.encryptionapp.Configuration;

import com.example.encryptionapp.Entities.User;
import com.example.encryptionapp.Enum.Role;
import com.example.encryptionapp.Repositories.UserRepository;
import com.example.encryptionapp.Services.ServicesImpl.AESEncryptionService;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DbInit implements CommandLineRunner {
    private final UserRepository userRepository;
    private final AESEncryptionService aesEncryptionService;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setRole(Role.ADMIN);
            user.setEmail("admin@admin.com");
            String salt = BCrypt.gensalt();
            user.setPassword(BCrypt.hashpw("mmmmmmmm", salt));
            user.setFirstName("admin");
            user.setLastName("super");
            userRepository.save(aesEncryptionService.encryptUser(user));
        }
    }
}
