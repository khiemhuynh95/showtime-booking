package showtime.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import showtime.booking.model.CinemaSeat;

@Repository
public interface CinemaSeatsRepository extends JpaRepository<CinemaSeat, Long>{

}
