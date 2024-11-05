package br.mendonca.testemaven.services;

import br.mendonca.testemaven.model.entities.Note;
import br.mendonca.testemaven.dao.NoteDAO;

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

}
