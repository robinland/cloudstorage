package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Base64;

@Controller
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping
    @RequestMapping("/files")
    public String addNote(Authentication authentication, MultipartFile fileUpload, Model model) throws Exception {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        if(fileUpload.isEmpty()){
           return "redirect:/result?error";
        }else{
            File file = new File();
            file.setUserId(user.getUserId());
            file.setFileData(fileUpload.getBytes());
            file.setFileSize(Long.toString(fileUpload.getSize()));
            file.setFileName(fileUpload.getOriginalFilename());
            file.setContentType(fileUpload.getContentType());
            String base64 = Base64.getEncoder().encodeToString(file.getFileData());
            String dataURL = "data:" + file.getContentType() + ";base64," + base64;
            file.setDataURL(dataURL);
            fileService.addFile(file);
        }
        return "redirect:/result?success";
    }

    @GetMapping("/files/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("id") int fileId){
        File file = fileService.getFileById(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ file.getFileName()+"\"")
                .body(new ByteArrayResource(file.getFileData()));
    }


    @GetMapping
    @RequestMapping("/files/delete")
    public String removeNote(@RequestParam("id") int fileId, RedirectAttributes redirectAttributes){
        if(fileId > 0){
            fileService.deleteFile(fileId);
            return "redirect:/result?success";
        }
        redirectAttributes.addAttribute("error", "Exception to delete the file. file ID is not available");
        return "redirect:/result?error";
    }


}
