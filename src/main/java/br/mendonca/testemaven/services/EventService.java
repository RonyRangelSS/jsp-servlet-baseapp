package br.mendonca.testemaven.services;

import br.mendonca.testemaven.dao.EventDAO;
import br.mendonca.testemaven.model.entities.Event;
import br.mendonca.testemaven.services.dto.EventDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventService {
	
	public void register(String userId, String eventName, int date, Boolean hasPassed) throws ClassNotFoundException, SQLException {
		EventDAO dao = new EventDAO();
		
		Event event = new Event();
		event.setUserId(userId);
		event.setEventName(eventName);
		event.setDate(date);
		event.setHasPassed(hasPassed);
		
		dao.register(event);
	}
	
	public List<EventDTO> listAllEvent() throws ClassNotFoundException, SQLException {
		ArrayList<EventDTO> resp = new ArrayList<EventDTO>();
		
		EventDAO dao = new EventDAO();
		List<Event> lista = dao.listAllEvent();
		
		for (Event event : lista) {
			resp.add(EventDTO.eventMapper(event));
		}
		
		return resp;
	}
}
