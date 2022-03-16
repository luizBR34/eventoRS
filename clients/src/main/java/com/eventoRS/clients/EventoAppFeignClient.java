package com.eventoRS.clients;

import com.eventoApp.models.Event;
import com.eventoApp.models.Guest;
import com.eventoApp.models.Role;
import com.eventoApp.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


//@FeignClient("GATEWAY-SERVICE")
@FeignClient(name = "evento-cache", url = "${EVENTO_CACHE_URI}")
public interface EventoAppFeignClient {

    @GetMapping("/eventoCache/eventList/{username}")
    public ResponseEntity<List<Event>> eventList(@PathVariable("username") String username);


    @GetMapping("/eventoCache/seekEvent")
    public ResponseEntity<Event> seekEvent(@RequestParam("username") String username, @RequestParam("code") long code);


    @PostMapping("/eventoCache/saveEvent")
    public ResponseEntity<Void> saveEvent(@RequestBody @Valid Event event);


    @GetMapping("/eventoCache/seekUser/{login}")
    public ResponseEntity<User> seekUser(@PathVariable("login") String login);


    @GetMapping("/eventoCache/guestList")
    public ResponseEntity<List<Guest>> guestList(@RequestParam("username") String username,
                                                 @RequestParam("eventCode") long eventCode);

    @PostMapping("/eventoCache/saveGuest")
    public ResponseEntity<Void> saveGuest(@RequestParam("username") String username,
                                          @RequestParam("eventCode") long eventCode,
                                          @RequestBody @Valid Guest guest);


    @DeleteMapping("/eventoCache/deleteEvent")
    public ResponseEntity<Void> deleteEvent(@RequestParam("username") String username,
                                            @RequestParam("code") long code);


    @DeleteMapping("/eventoCache/deleteGuest/{id}")
    public ResponseEntity<Event> deleteGuest(@PathVariable("id") long id);


    @GetMapping("/eventoCache/seekRole/{theRoleName}")
    public ResponseEntity<Role> seekRoleByName(@PathVariable("theRoleName") String theRoleName);

    @PostMapping("/eventoCache/saveSession")
    public ResponseEntity<Void> saveSession(@RequestBody @Valid User user);

    @GetMapping("/eventoCache/getSession")
    public ResponseEntity<User> getSession();
}
