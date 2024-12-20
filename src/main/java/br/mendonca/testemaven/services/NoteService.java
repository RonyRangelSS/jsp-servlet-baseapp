package br.mendonca.testemaven.services;

import br.mendonca.testemaven.model.entities.Note;
import br.mendonca.testemaven.dao.NoteDAO;
import br.mendonca.testemaven.services.dto.NoteDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteService {
    public void register(String userId, String noteTitle, String noteContent, int date, boolean isDone) throws ClassNotFoundException, SQLException {
        NoteDAO dao = new NoteDAO();

        Note note = new Note();
        note.setUserId(userId);
        note.setNoteTitle(noteTitle);
        note.setNoteContent(noteContent);
        note.setDate(date);
        note.setDone(isDone);

        dao.register(note);
    }

    public List<NoteDTO> listAllUserNotes(String userId) throws ClassNotFoundException, SQLException {
        ArrayList<NoteDTO> resp = new ArrayList<NoteDTO>();

        NoteDAO dao = new NoteDAO();
        List<Note> lista = dao.listAllNotesFromUser(userId);

        for (Note note : lista) {
            resp.add(NoteDTO.noteMapper(note));
        }

        return resp;
    }

    public NoteDTO getNoteById(String noteId) throws ClassNotFoundException, SQLException {
        NoteDAO dao = new NoteDAO();
        Note note = dao.getNoteById(noteId);

        return NoteDTO.noteMapper(note);
    }

    public List<NoteDTO> listNotesForPagination(String userId, int maxNotesPerPage, int offset) throws ClassNotFoundException, SQLException {
        ArrayList<NoteDTO> resp = new ArrayList<NoteDTO>();

        NoteDAO dao = new NoteDAO();
        List<Note> lista = dao.listNotesForPagination(userId, maxNotesPerPage, offset);

        for (Note note : lista) {
            resp.add(NoteDTO.noteMapper(note));
        }

        return resp;
    }

    public List<NoteDTO> listDeletedNotesForPagination(String userId, int maxNotesPerPage, int offset) throws ClassNotFoundException, SQLException {
        ArrayList<NoteDTO> resp = new ArrayList<NoteDTO>();

        NoteDAO dao = new NoteDAO();
        List<Note> lista = dao.listDeletedNotesForPagination(userId, maxNotesPerPage, offset);

        for (Note note : lista) {
            resp.add(NoteDTO.noteMapper(note));
        }

        return resp;
    }


    public int countUserNotes(String userId) throws ClassNotFoundException, SQLException {
        NoteDAO dao = new NoteDAO();
        int count = dao.countUserNotes(userId);

        return count;
    }

    public int countDeletedNotes(String userId) throws ClassNotFoundException, SQLException {
        NoteDAO dao = new NoteDAO();
        int count = dao.countDeletedNotes(userId);

        return count;
    }

    public void updateVisibleField(String noteId) throws ClassNotFoundException, SQLException {
        NoteDAO dao = new NoteDAO();
        dao.updateVisibleField(noteId);
    }


}
