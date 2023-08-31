package showtime.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import showtime.booking.model.CinemaHall;

@Repository
public interface CinemaHallsRepository extends JpaRepository<CinemaHall, Long>{

}
