package showtime.booking.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import showtime.booking.model.User;
import showtime.booking.repositories.UsersRepository;
@Service
public class UsersService {
	private final UsersRepository usersRepository;

	public UsersService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	public List<User> getAllUsers() {
		return usersRepository.findAll();
	}
	
	public void addUser(User u) {
		usersRepository.save(u);
	}
	
	public User findByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	public Optional<User> findUser(Long userId) {
		return Optional.empty();
	}
}
