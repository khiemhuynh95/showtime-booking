package showtime.booking.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import showtime.booking.model.Show;
import showtime.booking.model.ShowSeat;
import showtime.booking.services.ShowSeatsService;

@RestController
@RequestMapping("/seats")
public class ShowSeatController {
	@Autowired
	private ShowSeatsService ssService;
	
	@GetMapping("/show-id={show-id}")
	public List<ShowSeat> getSeatsByShowId(@PathVariable("show-id") Long id) {
		return ssService.getSeatsByShowId(id);
	}
	
	@GetMapping("/get2")
	public Optional<ShowSeat> get() {
		Long id = (long) 2;
		return ssService.findShowSeat(id);
	}
}
