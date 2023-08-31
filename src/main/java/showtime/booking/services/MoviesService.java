package showtime.booking.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import showtime.booking.enums.MovieStatus;
import showtime.booking.model.CinemaHall;
import showtime.booking.model.Movie;
import showtime.booking.repositories.MoviesRepository;

@Service
public class MoviesService {
	private final MoviesRepository moviesRepository;

    public MoviesService(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public List<Movie> getAllMovies() {
        return moviesRepository.findAll();
    }

    public Optional<Movie> findMovie(Long MovieId) {
        return moviesRepository.findById(MovieId);
    }
    
    public List<Movie> getMoviesByStatus(MovieStatus ms) {
    	return moviesRepository.findByStatus(ms);
    }
    
    public void addMovie(Movie m) {
    	moviesRepository.save(m);
    }
    
	public Movie getRandomNowPlaying() {
		List<Movie> list = getMoviesByStatus(MovieStatus.NOW_PLAYING);
		Random random = new Random();
		int index = random.nextInt(list.size());
		return list.get(index);
	}

}
