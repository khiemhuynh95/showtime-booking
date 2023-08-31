package showtime.booking.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import showtime.booking.enums.MovieStatus;
import showtime.booking.model.Cinema;
import showtime.booking.model.CinemaHall;
import showtime.booking.model.Movie;
import showtime.booking.model.Show;
import showtime.booking.services.CinemasService;
import showtime.booking.services.MoviesService;
import showtime.booking.services.ShowsService;

@RestController
@RequestMapping("/show")
public class ShowController {
	@Autowired
	private ShowsService sService;

	@Autowired
	private CinemasService cService;

//	@GetMapping("/now_playing")
//	public ResponseEntity<List<Movie>> getPlayingMovie() {
//
//		List<Movie> movies = mService.getMoviesByStatus(MovieStatus.NOW_PLAYING);
//		return ResponseEntity.ok(movies);
//	}
//
//	@GetMapping("/upcoming")
//	public ResponseEntity<List<Movie>> getUpcomingMovie() {
//
//		List<Movie> movies = mService.getMoviesByStatus(MovieStatus.UPCOMING);
//		return ResponseEntity.ok(movies);
//	}

	@GetMapping("/movie-id={movie-id}")
	public List<Show> getShowBy(@PathVariable("movie-id") Long id, @RequestParam("city") String city,
			@RequestParam("date") String date) {
		System.out.println("movie: " + id + ", city: " + city + ", date: " + date);
		//get a list of cinemas with zipcode
		List<Cinema> cinemas = cService.findByCity(city);
		//PASS
		System.out.println("CINEMAS: " + cinemas.size());
		
		List<CinemaHall> cinemaHalls = new ArrayList<>();
		//for each cinema, find all its cinemahalls
		for (Cinema c : cinemas) {
			cinemaHalls.addAll(c.getCinemaHalls());
		}
		//System.out.println("Total Halls: " + cinemaHalls.size());
		return sService.findShowBy(id, cinemaHalls, date);

	}
}
