package showtime.booking.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import showtime.booking.model.MovieGenre;
import showtime.booking.repositories.MovieGenreRepository;
@Service
public class MovieGenreService {
	private final MovieGenreRepository mgRepository;

    public MovieGenreService(MovieGenreRepository mgRepository) {
        this.mgRepository = mgRepository;
    }

    public List<MovieGenre> getAllMovies() {
        return mgRepository.findAll();
    }

    public MovieGenre findMovieGenre(Long MovieGenreId) {
        return mgRepository.findById(MovieGenreId).orElse(null);
    }
    
    public void createMovieGenre(MovieGenre mg) {
    	mgRepository.save(mg);
    }
}
