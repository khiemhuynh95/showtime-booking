package showtime.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import showtime.booking.model.Booking;

@Repository
public interface BookingsRepository extends JpaRepository<Booking, Long>{

}
