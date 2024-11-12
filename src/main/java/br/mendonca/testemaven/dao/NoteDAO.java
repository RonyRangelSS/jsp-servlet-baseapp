package br.mendonca.testemaven.dao;

import br.mendonca.testemaven.model.entities.Note;
import br.mendonca.testemaven.model.entities.User;
import br.mendonca.testemaven.services.dto.NoteDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NoteDAO {
    public void register(Note note) throws ClassNotFoundException, SQLException {
        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        PreparedStatement ps = conn.prepareStatement("INSERT INTO notes (userId, noteTitle, noteContent, date, isDone) values (?,?,?,?,?)");
        ps.setObject(1, UUID.fromString(note.getUserId()));
        ps.setString(2, note.getNoteTitle());
        ps.setString(3, note.getNoteContent());
        ps.setInt(4, note.getDate());
        ps.setBoolean(5, note.isDone());
        ps.execute();
        ps.close();
    }

    public List<Note> listAllNotesFromUser(String userId) throws ClassNotFoundException, SQLException {
        ArrayList<Note> lista = new ArrayList<Note>();

        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM notes");

        while (rs.next()) {
            Note note = new Note();
            note.setUuid(rs.getString("uuid"));
            note.setUserId(rs.getString("userId"));
            note.setNoteTitle(rs.getString("noteTitle"));
            note.setNoteContent(rs.getString("noteContent"));
            note.setDate(rs.getInt("date"));
            note.setDone(rs.getBoolean("isDone"));

            lista.add(note);
        }

        rs.close();

        return lista;
    }

    public Note getNoteById(String noteId) throws ClassNotFoundException, SQLException {
        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM notes WHERE uuid = ?");
        ps.setObject(1, UUID.fromString(noteId));
        ResultSet rs = ps.executeQuery();

        Note note = new Note();

        while (rs.next()) {
            note.setUuid(rs.getString("uuid"));
            note.setUserId(rs.getString("userId"));
            note.setNoteTitle(rs.getString("noteTitle"));
            note.setNoteContent(rs.getString("noteContent"));
            note.setDate(rs.getInt("date"));
            note.setDone(rs.getBoolean("isDone"));
        }

        rs.close();

        return note;
    }

    public List<Note> listNotesForPagination(String userId, int maxNotesPerPage, int offset) throws ClassNotFoundException, SQLException {
        ArrayList<Note> lista = new ArrayList<Note>();

        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        Statement st = conn.createStatement();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM notes  WHERE userId = ?  LIMIT ? OFFSET ?");
        ps.setObject(1, UUID.fromString(userId));
        ps.setInt(2, maxNotesPerPage);
        ps.setInt(3, offset);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Note note = new Note();
            note.setUuid(rs.getString("uuid"));
            note.setUserId(rs.getString("userId"));
            note.setNoteTitle(rs.getString("noteTitle"));
            note.setNoteContent(rs.getString("noteContent"));
            note.setDate(rs.getInt("date"));
            note.setDone(rs.getBoolean("isDone"));

            lista.add(note);
        }

        rs.close();

        return lista;
    }

    public int countUserNotes(String userId) throws ClassNotFoundException, SQLException {
        int count = 0;

        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        Statement st = conn.createStatement();
        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM notes WHERE userId = ?");
        ps.setObject(1, UUID.fromString(userId));
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            count = rs.getInt(1);
        }

        rs.close();

        return count;
    }






}
