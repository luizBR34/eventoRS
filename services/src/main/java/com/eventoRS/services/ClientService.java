package com.eventoRS.services;

import java.util.List;

import com.eventoApp.models.Event;
import com.eventoApp.models.Guest;
import com.eventoApp.models.Role;
import com.eventoApp.models.User;

public interface ClientService {

    List<Event> eventList(String username);

    Event seekEvent(String username, long code);

    User seekUser(String login);

    long seekLastEventIndexSaved();

    void saveUser(User user);

    Role seekRoleByName(String theRoleName);

    List<Guest> guestList(String username, long eventCode);

    void saveGuest(String username, long eventCode, Guest guest);

    void saveEvent(Event event);

    void deleteEvent(String username, long code);

    Event deleteGuest(long id);
}
