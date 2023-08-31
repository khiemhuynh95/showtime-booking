package showtime.booking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import showtime.booking.model.Cinema;

@Repository
public interface CinemasRepository extends JpaRepository<Cinema, Long> {

	List<Cinema> findAllByZipcode(String zipcode);

	List<Cinema> findAllByCity(String city);

}
