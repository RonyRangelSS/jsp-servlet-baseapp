package br.mendonca.testemaven.dao;

import br.mendonca.testemaven.model.entities.Note;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
}
