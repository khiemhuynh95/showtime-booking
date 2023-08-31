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
import showtime.booking.model.Movie;
import showtime.booking.services.MoviesService;

@RestController
@RequestMapping("/movie")
public class MovieController {
	@Autowired
	private MoviesService mService;

	@GetMapping("/now_playing")
	public ResponseEntity<List<Movie>> getPlayingMovie() {

		List<Movie> movies = mService.getMoviesByStatus(MovieStatus.NOW_PLAYING);
		return ResponseEntity.ok(movies);
	}

	@GetMapping("/upcoming")
	public ResponseEntity<List<Movie>> getUpcomingMovie() {

		List<Movie> movies = mService.getMoviesByStatus(MovieStatus.UPCOMING);
		return ResponseEntity.ok(movies);
	}

	@GetMapping("/{movie-id}")
	public Optional<Movie> getMovieById(@PathVariable("movie-id") Long id) {
		return mService.findMovie(id);
	}
}
