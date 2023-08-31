package showtime.booking.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;
import showtime.booking.model.CinemaHall;
import showtime.booking.repositories.CinemaHallsRepository;

@Service
public class CinemaHallsService {
	private final CinemaHallsRepository cinemahallsRepository;

	public CinemaHallsService(CinemaHallsRepository cinemahallsRepository) {
		this.cinemahallsRepository = cinemahallsRepository;
	}

	public List<CinemaHall> getAllCenemaHalls() {
		return cinemahallsRepository.findAll();
	}

	public Optional<CinemaHall> findCinemaHall(Long CinemaHallID) {
		return Optional.empty();
	}

	public void addCinemaHall(CinemaHall ch) {
		cinemahallsRepository.save(ch);
	}

	public CinemaHall getRandom() {
		List<CinemaHall> list = cinemahallsRepository.findAll();
		Random random = new Random();
		int index = random.nextInt(list.size());
		return list.get(index);
	}
}
