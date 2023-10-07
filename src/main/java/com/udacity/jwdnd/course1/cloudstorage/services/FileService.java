package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getAllFile(int userId){
        List<File> lsFiles =  fileMapper.getAllFile(userId);
        if(lsFiles == null){
            return  new ArrayList<>();
        }
        return lsFiles;
    }

    public File getFileById(int fileId){
        return fileMapper.getFileById(fileId);
    }


    public void addFile(File file){
        fileMapper.insert(file);
    }

    public void updateFile(File file){
        fileMapper.updateFile(file);
    }

    public void deleteFile(int fileId){
        fileMapper.deleteFile(fileId);
    }


}
