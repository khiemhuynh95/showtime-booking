package showtime.booking.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import showtime.booking.model.Genre;
import showtime.booking.model.MovieGenre;
import showtime.booking.repositories.GenresRepository;

@Service
public class GenresService {
	private final GenresRepository genresRepository;

	public GenresService(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

	public List<Genre> getAllGenres() {
		return genresRepository.findAll();
	}

	public Genre findGenre(Long id) {
        return genresRepository.findById(id).orElse(null);
    }

	public void addGenre(Genre g) {
		genresRepository.save(g);
	}
}
