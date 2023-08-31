package showtime.booking.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import showtime.booking.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long>{

}
