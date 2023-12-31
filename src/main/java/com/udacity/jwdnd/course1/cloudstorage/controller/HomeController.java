package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/home")
public class HomeController {

    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    private UserService userService;

    private EncryptionService encryptionService;

    public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String homePage(Authentication authentication, Model model) throws Exception {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        model.addAttribute("files", fileService.getAllFile(user.getUserId()));
        model.addAttribute("notes", noteService.getAllNote(user.getUserId()));
        model.addAttribute("credentials", credentialService.getAllCredential(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }
}
