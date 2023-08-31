package showtime.booking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import showtime.booking.enums.MovieStatus;
import showtime.booking.model.Movie;

@Repository
public interface MoviesRepository extends JpaRepository<Movie, Long>{

	List<Movie> findByStatus(MovieStatus ms);

}
