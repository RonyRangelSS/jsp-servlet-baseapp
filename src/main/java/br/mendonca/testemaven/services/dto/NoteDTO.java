package br.mendonca.testemaven.services.dto;

import br.mendonca.testemaven.model.entities.Note;

public class NoteDTO {
    private String uuid;
    private String userId;
    private String noteTitle;
    private String noteContent;
    private boolean isVisible;
    private int date;
    private boolean isDone;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public boolean isDone() {
        return isDone;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public void setVisible(boolean isVisible) {this.isVisible = isVisible; }

    public static NoteDTO noteMapper(Note note) {
        NoteDTO dto = new NoteDTO();
        dto.setUuid(note.getUuid());
        dto.setUserId(note.getUserId());
        dto.setNoteTitle(note.getNoteTitle());
        dto.setNoteContent(note.getNoteContent());
        dto.setDate(note.getDate());
        dto.setDone(note.isDone());
        dto.setVisible(note.isVisible());

        return dto;
    }
}
