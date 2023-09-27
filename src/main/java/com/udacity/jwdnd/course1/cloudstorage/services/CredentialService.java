package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public List<Credential> getAllCredential(int userId){
        List<Credential> lsCredentials = credentialMapper.getAllCredential(userId);
        if(lsCredentials == null){
            return new ArrayList<>();
        }
        return lsCredentials;
    }

    public void addCredential(Credential credential){
        credentialMapper.insert(credential);
    }

    public void updateCredential(Credential credential){
        credentialMapper.updateCredential(credential);
    }

    public void deleteCredential(int credentialId){
        credentialMapper.deleteCredential(credentialId);
    }



}
