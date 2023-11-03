package showtime.booking.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import showtime.booking.enums.SeatStatus;
import showtime.booking.model.Booking;
import showtime.booking.model.Show;
import showtime.booking.model.ShowSeat;
import showtime.booking.model.User;
import showtime.booking.services.BookingsService;
import showtime.booking.services.ShowSeatsService;
import showtime.booking.services.ShowsService;
import showtime.booking.services.UsersService;

@RestController
public class BookingController {
	@Autowired
	private UsersService uService;
	
	@Autowired
	private ShowsService sService;
	
	@Autowired
	private BookingsService bService;
	
	@Autowired
	private ShowSeatsService ssService;

	@PostMapping("/book")
	@Transactional
	public ResponseEntity<String> createBooking(@RequestBody Map<String, Object> requestBody) {
		// Access the values from the JSON object
		List<Integer> seatIds = (List<Integer>) requestBody.get("seatIds");
		Long showId = Long.parseLong((String) requestBody.get("showId"));
		Map<String, Object> userMap = (Map<String, Object>) requestBody.get("user");
		String firstName = (String) userMap.get("firstName");
		String lastName = (String) userMap.get("lastName");
		String email = (String) userMap.get("email");
		String phoneNumber = (String) userMap.get("phoneNumber");

		// save the user
		User u = uService.findByEmail(email);
		if (u == null) {
			u = new User(email, firstName, lastName, phoneNumber);
			uService.addUser(u);
		} 
		
		//get show
		Show show = sService.findShowBy(showId).orElse(null);
		
		//save the booking
		Booking b = new Booking(u, show, Calendar.getInstance().getTime());
		bService.addBooking(b);
		
		//update seats
		for (Integer seatId : seatIds) {
			ShowSeat seat = ssService.findShowSeat(seatId.longValue()).orElse(null);
			seat.setStatus(SeatStatus.BOOKED);
			seat.setBooking(b);
		}

		return ResponseEntity.ok("Booking created successfully");
	}
}
