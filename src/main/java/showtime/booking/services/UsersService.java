package showtime.booking.services;

import java.util.List;
import java.util.Optional;



import showtime.booking.model.User;
import showtime.booking.repositories.UsersRepository;

public class UsersService {
	private final UsersRepository usersRepository;

	public UsersService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	public List<User> getAllUsers() {
		return usersRepository.findAll();
	}

	public Optional<User> findUser(Long userId) {
		return Optional.empty();
	}
}
