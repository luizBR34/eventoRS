package com.eventoRS.clients;

import com.eventoApp.models.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

//@FeignClient("GATEWAY-SERVICE")
@FeignClient(name = "evento-ws", url = "${EVENTO_WS_URI:http://localhost:9090}")
public interface EventoAppFeignClientWS {

	@GetMapping("/eventoWS/seekLastEventSaved")
	public ResponseEntity<Event> seekLastEventSaved();
}
