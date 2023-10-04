package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService,UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    @RequestMapping("/notes")
    public String addNote(Authentication authentication, Note note, Model model) throws Exception {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        if(note.getNoteId() > 0){
            noteService.updateNote(note);
        }else{
            note.setUserId(user.getUserId());
            noteService.addNote(note);
        }
        return "redirect:/result?success";
    }

    @GetMapping
    @RequestMapping("/notes/delete")
    public String removeNote(@RequestParam("id") int noteId, RedirectAttributes redirectAttributes){
        if(noteId > 0){
            noteService.deleteNote(noteId);
            return "redirect:/result?success";
        }
        redirectAttributes.addAttribute("error", "Exception to delete the note. Note ID is not available");
        return "redirect:/result?error";
    }


}
