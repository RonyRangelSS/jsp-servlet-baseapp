package br.mendonca.testemaven.dao;

import br.mendonca.testemaven.model.entities.Event;
import br.mendonca.testemaven.model.entities.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventDAO {

	public void register(Event event) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);
		
		PreparedStatement ps = conn.prepareStatement("INSERT INTO events (userId, eventName, date, hasPassed, isVisible) values (?,?,?,?,?)");
		ps.setObject(1, UUID.fromString(event.getUserId()));
		ps.setString(2, event.getEventName());
		ps.setInt(3, event.getDate());
		ps.setBoolean(4, event.getHasPassed());
		ps.setBoolean(5, true);
		ps.execute();
		ps.close();
	}
	
	public List<Event> listAllEvent(String userId) throws ClassNotFoundException, SQLException {
		ArrayList<Event> lista = new ArrayList<Event>();
		
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);

		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM events");
		
		while (rs.next()) {
			Event event = new Event();
			event.setUuid(rs.getString("uuid"));
			event.setUserId(rs.getString("userId"));
			event.setEventName(rs.getString("eventName"));
			event.setDate(rs.getInt("date"));
			event.setHasPassed(rs.getBoolean("hasPassed"));
			event.setIsVisible(rs.getBoolean("isVisible"));
			
			lista.add(event);
		}
		
		rs.close();
		
		return lista;
	}

	public Event getEventById(String eventId) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);

		PreparedStatement ps = conn.prepareStatement("SELECT * FROM events WHERE uuid = ?");
		ps.setObject(1, UUID.fromString(eventId));
		ResultSet rs = ps.executeQuery();

		Event event = new Event();

		while (rs.next()) {
			event.setUuid(rs.getString("uuid"));
			event.setUserId(rs.getString("userId"));
			event.setEventName(rs.getString("eventName"));
			event.setDate(rs.getInt("date"));
			event.setHasPassed(rs.getBoolean("hasPassed"));
			event.setIsVisible(rs.getBoolean("isVisible"));
		}

		rs.close();

		return event;
	}

	public ArrayList<Event> listAllEventPaginated(String userId, int offset, int limit) throws ClassNotFoundException, SQLException {
		ArrayList<Event> lista = new ArrayList<Event>();
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);
		Statement st = conn.createStatement();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM events  WHERE userId = ?  LIMIT ? OFFSET ?");
		ps.setObject(1, UUID.fromString(userId));
		ps.setInt(2, limit);
		ps.setInt(3, offset);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Event event = new Event();
			event.setUuid(rs.getString("uuid"));
			event.setUserId(rs.getString("userId"));
			event.setEventName(rs.getString("eventName"));
			event.setDate(rs.getInt("date"));
			event.setHasPassed(rs.getBoolean("hasPassed"));
			event.setIsVisible(rs.getBoolean("isVisible"));

			if (event.getIsVisible() == true) {
				lista.add(event);
			}
		}
		rs.close();
		return lista;
	}

	public void updateIsVisibleField(String eventId) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);

		Event event = this.getEventById(eventId);

		PreparedStatement ps = conn.prepareStatement("UPDATE events SET isVisible = ? WHERE uuid = ?");
		ps.setBoolean(1, !event.getIsVisible());
		ps.setObject(2, UUID.fromString(eventId));
		ps.executeUpdate();
		ps.close();
	}

}
