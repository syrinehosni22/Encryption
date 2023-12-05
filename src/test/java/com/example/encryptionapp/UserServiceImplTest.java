package com.example.encryptionapp;

import com.example.encryptionapp.Entities.User;
import com.example.encryptionapp.Repositories.UserRepository;
import com.example.encryptionapp.Services.ServicesImpl.AESEncryptionService;
import com.example.encryptionapp.Services.ServicesImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AESEncryptionService aesEncryptionService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(aesEncryptionService.encryptUser(any(User.class))).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.addUser(user);

        verify(aesEncryptionService, times(1)).encryptUser(any(User.class));
        verify(userRepository, times(1)).save(user);
        assertEquals(user, result);
    }

    @Test
    public void testSignIn() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));

        when(aesEncryptionService.encrypt(anyString())).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        User result = userService.signIn("test@example.com", "password");

        verify(aesEncryptionService, times(1)).encrypt("test@example.com");
        verify(userRepository, times(1)).findByEmail("test@example.com");
        assertNotNull(result);
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(aesEncryptionService.decryptUser(user)).thenReturn(user);

        User result = userService.getUserById(1L);

        verify(userRepository, times(1)).findById(1L);
        verify(aesEncryptionService, times(1)).decryptUser(user);
        assertEquals(user, result);
    }

    @Test
    public void testUserEmailExist() {
        User user = new User();
        user.setEmail("test@example.com");

        when(aesEncryptionService.encrypt("test@example.com")).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        boolean result = userService.userEmailExist("test@example.com");

        verify(aesEncryptionService, times(1)).encrypt("test@example.com");
        verify(userRepository, times(1)).findByEmail("test@example.com");
        assertTrue(result);
    }
}
