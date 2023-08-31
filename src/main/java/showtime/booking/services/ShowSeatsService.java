package showtime.booking.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import showtime.booking.model.ShowSeat;
import showtime.booking.repositories.ShowSeatsRepository;

@Service
public class ShowSeatsService {
	private final ShowSeatsRepository showSeatsRepository;

	public ShowSeatsService(ShowSeatsRepository showSeatsRepository) {
		this.showSeatsRepository = showSeatsRepository;
	}

	public List<ShowSeat> getAllShowSeats() {
		return showSeatsRepository.findAll();
	}

	public Optional<ShowSeat> findShowSeat(Long showSeatId) {
		return Optional.empty();
	}
}
