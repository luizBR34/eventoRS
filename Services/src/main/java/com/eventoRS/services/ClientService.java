package com.eventoRS.services;

import java.util.List;

import com.eventoApp.models.Event;
import com.eventoApp.models.Guest;
import com.eventoApp.models.Role;
import com.eventoApp.models.User;

public interface ClientService {
	
	public List<Event> eventList(String username);
	
	public Event seekEvent(String username, long code);

	public User seekUser(String login);

	public long seekLastEventIndexSaved();
	
	public void saveUser(User user);
	
	public Role seekRoleByName(String theRoleName);
	
	public List<Guest> guestList(long eventCode);
	
	public void saveGuest(long eventCode, Guest guest);
	
	public void saveEvent(Event evento);
	
	public void deleteEvent(String username, long code);
	
	public Event deleteGuest(long id);
	
	public void saveSession(User user);
	
	public User getSession();

	public void deleteSession();
}
