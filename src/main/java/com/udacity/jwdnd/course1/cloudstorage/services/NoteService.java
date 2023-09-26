package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNote(int userId){
        List<Note> lsNote = noteMapper.getAllNoteByUserId(userId);
        if(lsNote == null){
            return  new ArrayList<>();
        }
        return lsNote;
    }

    public void addNote(Note note){
        noteMapper.insert(note);
    }

    public void updateNote(Note note){
        noteMapper.update(note);
    }

    public void deleteNote(Note note){
        noteMapper.delete(note.getNoteId());
    }
}
