package com.eventoRS.web;

import com.eventoApp.models.Event;
import com.eventoApp.models.Guest;
import com.eventoApp.models.User;
import com.eventoRS.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;

@RestController
@Slf4j
public class ResourceController {

    @Autowired
    private ClientService sr;


    @PostMapping(value="/saveEvent")
    @PreAuthorize("#oauth2.hasScope('read')")
    public void saveEvent(@RequestHeader(name = "username", required = false) String username, @RequestBody @Valid Event event) {

        User user = (nonNull(username) & !username.equals("")) ? sr.seekUser(username) : null;

        if (nonNull(user)) {
            event.setUser(user);
        }

        if (sr.seekLastEventIndexSaved() != -1) {
            long index = sr.seekLastEventIndexSaved();
            event.setCode(++index);
        }

        sr.saveEvent(event);
    }



    @GetMapping(value="/events/{username}", produces="application/json")
    @PreAuthorize("#oauth2.hasScope('read')")
    //@Retry(name="eventlist", fallbackMethod = "handleError")
    public @ResponseBody List<Event> getEventList(@PathVariable("username") String username) {

        log.info("ResourceController:getEventList()");

/*		Event um = new Event(1, "Casamento do Fulano", "S�o PAulo", "12/04/2021", "11:00");
		Event dois = new Event(2, "Cinema", "Rio de Janeiro", "16/08/2018", "18:00");
		List<Event> eventList = Arrays.asList(um, dois);*/


        List<Event> eventList = (!username.equals("Visitor")) ?
                sr.eventList(username) :
                Collections.emptyList();

        return eventList;
    }




    @GetMapping(value="/seekEvent/{code}", produces="application/json")
    @PreAuthorize("#oauth2.hasScope('read')")
    public @ResponseBody Event seekEvent(@RequestHeader(name = "username", required = true) String username, @PathVariable("code") long code) {

        log.info("START - ResourceController:seekEvent()");

        Event soughtEvent = (!username.equals("")) ? sr.seekEvent(username, code) : null;

        log.info("END - ResourceController:seekEvent()");

        return soughtEvent;
    }




    @GetMapping(value="/guestList", produces="application/json")
    @PreAuthorize("#oauth2.hasScope('read')")
    public @ResponseBody List<Guest> guestList(@RequestParam(required = true) String username,
                                               @RequestParam(required = true) long eventCode) {

        log.info("ResourceController:guestList()");
        List<Guest> listaConvidados = sr.guestList(username, eventCode);
        return listaConvidados;
    }


    @PostMapping(value="/saveGuest/", produces="application/json")
    @PreAuthorize("#oauth2.hasScope('read')")
    public @ResponseBody void saveGuest(@RequestParam("username") String username,
                                        @RequestParam(required = true) long eventCode,
                                        @RequestBody @Valid Guest guest) {

        log.info("ResourceController:saveGuest()");
        sr.saveGuest(username, eventCode, guest);
    }



    @DeleteMapping("/deleteEvent/{code}")
    @PreAuthorize("#oauth2.hasScope('read')")
    public @ResponseBody void deleteEvent(@RequestHeader(name = "username", required = true) String username, @PathVariable("code") long code) {

        log.info("START - ResourceController:deleteEvent()");

        sr.deleteEvent(username, code);

        log.info("END - ResourceController:deleteEvent()");
    }



    @DeleteMapping("/deleteGuest/{id}")
    public String deleteGuest(@PathVariable("id")long id) {

        Event event = sr.deleteGuest(id);

        long eventCode = event.getCode();
        String code = "" + eventCode;

        return "redirect:/" + code; // chama o metodo detalhesEvento(@PathVariable("codigo") long codigo) mostrando a lista de eventos
    }



	@RequestMapping(value="/hello",method=RequestMethod.GET)
	@PreAuthorize("#oauth2.hasScope('read')")
    public String helloSecured() {

        return "This Response comes from RESOURCE SERVER!";
    }
}
