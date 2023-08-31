package showtime.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import showtime.booking.model.Genre;

@Repository
public interface GenresRepository extends JpaRepository<Genre, Long>{

}
