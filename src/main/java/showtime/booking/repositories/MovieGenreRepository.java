package showtime.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import showtime.booking.model.MovieGenre;

public interface MovieGenreRepository extends JpaRepository<MovieGenre, Long>{

}
