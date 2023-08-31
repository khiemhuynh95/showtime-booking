package showtime.booking.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import showtime.booking.model.Show;

@Repository
public interface ShowsRepository extends JpaRepository<Show, Long>{



}
