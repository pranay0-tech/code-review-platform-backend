package com.pranay.code_review_platform_backend.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class TokenEncryptionService {

    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "1234567890123456";

    public String encrypt(String token) {
        try {
            SecretKeySpec key = new SecretKeySpec(
                    SECRET_KEY.getBytes(),
                    ALGORITHM
            );

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encrypted = cipher.doFinal(token.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt GitHub access token", e);
        }
    }

    public String decrypt(String encryptedToken) {
        try {
            SecretKeySpec key = new SecretKeySpec(
                    SECRET_KEY.getBytes(),
                    ALGORITHM
            );

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decrypted = cipher.doFinal(
                    Base64.getDecoder().decode(encryptedToken)
            );

            return new String(decrypted);

        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt GitHub access token", e);
        }
    }
}
