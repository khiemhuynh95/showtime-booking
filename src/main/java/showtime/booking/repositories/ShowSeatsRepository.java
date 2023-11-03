package showtime.booking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import showtime.booking.enums.MovieStatus;
import showtime.booking.model.Cinema;
import showtime.booking.model.Movie;
import showtime.booking.model.ShowSeat;

@Repository
public interface ShowSeatsRepository extends JpaRepository<ShowSeat, Long> {

}
