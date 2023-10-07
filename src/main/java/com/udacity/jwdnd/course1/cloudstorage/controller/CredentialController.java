package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CredentialController {

    private UserService userService;

    private CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping
    @RequestMapping("/credentials")
    public String addCredential(Authentication authentication, Credential credential, Model model) throws Exception {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        if(credential.getCredentialId() > 0){
            credentialService.updateCredential(credential);
        }else{
            credential.setUserId(user.getUserId());
            credentialService.addCredential(credential);
        }

        return "redirect:/result?success";
    }

    @GetMapping
    @RequestMapping("/credentials/delete")
    public String removeCredential(@RequestParam("id") int credentialId){
        if(credentialId > 0){
            credentialService.deleteCredential(credentialId);
            return "redirect:/result?success";
        }
        return "redirect:/result?error";
    }


}
