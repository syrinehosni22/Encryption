package com.example.encryptionapp.Services.ServicesImpl;

import com.example.encryptionapp.Entities.Claim;
import com.example.encryptionapp.Entities.User;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class AESEncryptionService {
    private SecretKey secretKey;
    private static final String KEY_FILE_PATH = "src/main/resources/secureFile";

    public AESEncryptionService() {
        if (!keyFileExists()) {
            generateAndSaveSecretKey();
        } else {
            secretKey = loadSecretKey();
        }
    }

    private void generateAndSaveSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();
            saveSecretKey(secretKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveSecretKey(SecretKey key) {
        try {
            byte[] keyBytes = key.getEncoded();
            Files.write(Paths.get(KEY_FILE_PATH), keyBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SecretKey loadSecretKey() {
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(KEY_FILE_PATH));
            return new SecretKeySpec(keyBytes, "AES");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean keyFileExists() {
        File keyFile = new File(KEY_FILE_PATH);
        return keyFile.exists();
    }

    public String encrypt(String strToEnc) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEnc.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {

        }
        return null;
    }

    public String decrypt(String strToDec) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDec)));
        } catch (Exception e) {

        }
        return null;
    }

    public Claim encryptClaim(Claim claim) {
        claim.setMessage(encrypt(claim.getMessage()));
        claim.setObject(encrypt(claim.getObject()));
        claim.setEmail(encrypt(claim.getEmail()));
        claim.setFirstName(encrypt(claim.getFirstName()));
        claim.setLastName(encrypt(claim.getLastName()));
        claim.setPhoneNumber(encrypt(claim.getPhoneNumber()));
        claim.setUser(encryptUser(claim.getUser()));
        return claim;
    }

    public User encryptUser(User user) {
        user.setEmail(encrypt(user.getEmail()));
        user.setFirstName(encrypt(user.getFirstName()));
        user.setLastName(encrypt(user.getLastName()));
        return user;
    }

    public Claim decryptClaim(Claim claim) {
        claim.setMessage(decrypt(claim.getMessage()));
        claim.setObject(decrypt(claim.getObject()));
        claim.setEmail(decrypt(claim.getEmail()));
        claim.setFirstName(decrypt(claim.getFirstName()));
        claim.setLastName(decrypt(claim.getLastName()));
        claim.setPhoneNumber(decrypt(claim.getPhoneNumber()));
        claim.setUser(decryptUser(claim.getUser()));
        return claim;
    }

    public User decryptUser(User user) {
        user.setEmail(decrypt(user.getEmail()));
        user.setFirstName(decrypt(user.getFirstName()));
        user.setLastName(decrypt(user.getLastName()));
        return user;
    }
}