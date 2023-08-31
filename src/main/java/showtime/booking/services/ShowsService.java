package showtime.booking.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import showtime.booking.model.CinemaHall;
import showtime.booking.model.Show;
import showtime.booking.repositories.ShowsRepository;

@Service
public class ShowsService {
	private final ShowsRepository ShowsRepository;

	public ShowsService(ShowsRepository ShowsRepository) {
		this.ShowsRepository = ShowsRepository;
	}

	public List<Show> getAllShows() {
		return ShowsRepository.findAll();
	}

	public Optional<Show> findShow(Long ShowId) {
		return Optional.empty();
	}

	public void addShow(Show s) {
		ShowsRepository.save(s);
	}

	public List<Show> findShowBy(Long movieId, List<CinemaHall> cinemaHalls, String date) {
		// TODO Auto-generated method stub
		// return ShowsRepository.findByMovieId(id);
		List<Show> results = new ArrayList<>();
		List<Show> shows = ShowsRepository.findAll();
		System.out.println("SHOWS: " + shows.size());
		for (Show s : shows) {
			
			if (s.getMovie().getMovieID().equals(movieId) && cinemaHalls.contains(s.getCinemaHall())) {
				// Format for parsing the string date
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

				// Convert the string to a Date object
				try {
					Date dateFromString = dateFormat.parse(date);

					if (s.getDate().compareTo(dateFromString) == 0) {
						results.add(s);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return results;

	}
}
