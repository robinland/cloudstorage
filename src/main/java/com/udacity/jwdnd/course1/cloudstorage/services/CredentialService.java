package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper,EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredential(int userId){
        List<Credential> lsCredentials = credentialMapper.getAllCredential(userId);
        if(lsCredentials == null){
            return new ArrayList<>();
        }
        return lsCredentials;
    }

    public void addCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        credentialMapper.insert(credential);
    }

    public void updateCredential(Credential credential){
        Credential existingCredential = credentialMapper.getCredential(credential.getCredentialId());
        credential.setKey(existingCredential.getKey());
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), existingCredential.getKey()));
        credentialMapper.updateCredential(credential);
    }

    public void deleteCredential(int credentialId){
        credentialMapper.deleteCredential(credentialId);
    }



}
