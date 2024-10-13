package com.heavendeeds.heavendeeds.utils;

import com.heavendeeds.heavendeeds.entities.HAVEENsecretKey;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import com.heavendeeds.heavendeeds.repository.HAVEENsecretKeyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;

@Service
public class HAVEENcommonUtils {
    @Autowired
    HAVEENsecretKeyRepo haveeNsecretKeyRepo;

    private static final String ALGORITHM = "AES";

   public ResponseEntity<?>updateApiKey(Integer secretId) throws Exception {

       HAVEENsecretKey haveeNsecretKey=haveeNsecretKeyRepo.findBySecretId(secretId);
       String existingApiKey = haveeNsecretKey.getOrgKey();
       String encryptedApiKey = encryptApiKey(existingApiKey, haveeNsecretKey.getSecretKey());
       haveeNsecretKey.setApiKey(encryptedApiKey);
       HAVEENsecretKey haveeNsecretKey1=haveeNsecretKeyRepo.save(haveeNsecretKey);
       haveeNsecretKey1.setOrgKey(null);
       String encodedSecretKey = Base64.getEncoder().encodeToString(haveeNsecretKey1.getSecretKey().getBytes());
       haveeNsecretKey1.setSecretKey(encodedSecretKey);
       return ResponseEntity.status(HttpStatus.OK).body(haveeNsecretKey1);
   }


    public String encryptApiKey(String apiKey, String secretKey) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(padKey(secretKey).getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(apiKey.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String retrieveDecryptedApiKey() throws Exception {
        HAVEENsecretKey secretEntity = haveeNsecretKeyRepo.findById(1).orElseThrow(() -> new Exception("Secret not found"));
        return decryptApiKey(secretEntity.getApiKey(), secretEntity.getSecretKey());
    }
    public String decryptApiKey(String encryptedApiKey, String secretKey) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedApiKey);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    private String padKey(String key) {
        if (key.length() < 16) {
            return String.format("%-16s", key);
        } else if (key.length() > 16) {
            return key.substring(0, 16);
        }
        return key;
    }

}
