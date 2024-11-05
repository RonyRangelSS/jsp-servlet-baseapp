package br.mendonca.testemaven.dao;

import br.mendonca.testemaven.model.entities.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventDAO {

	public void register(Event event) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);
		
		PreparedStatement ps = conn.prepareStatement("INSERT INTO events (userId, eventName, date, hasPassed) values (?,?,?,?)");
		ps.setObject(1, UUID.fromString(event.getUserId()));
		ps.setString(2, event.getEventName());
		ps.setInt(3, event.getDate());
		ps.setBoolean(4, event.getHasPassed());
		ps.execute();
		ps.close();
	}
	
	public List<Event> listAllEvent() throws ClassNotFoundException, SQLException {
		ArrayList<Event> lista = new ArrayList<Event>();
		
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM events");
		
		while (rs.next()) {
			Event event = new Event();
			event.setUuid(rs.getString("uuid"));
			event.setEventName(rs.getString("eventName"));
			event.setDate(rs.getInt("date"));
			event.setHasPassed(rs.getBoolean("hasPassed"));
			
			lista.add(event);
		}
		
		rs.close();
		
		return lista;
	}

}
