package showtime.booking.services;

import showtime.booking.repositories.CinemaSeatsRepository;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import showtime.booking.model.CinemaSeat;
@Service
public class CinemaSeatsService {
	private final CinemaSeatsRepository cinemaseatsRepository;
	
	public CinemaSeatsService(CinemaSeatsRepository cinemaseatsRepository) {
		this.cinemaseatsRepository = cinemaseatsRepository;
	}
	
	public List<CinemaSeat> getAllCenemaSeats () {
		return cinemaseatsRepository.findAll();
	}
	
	public Optional<CinemaSeat> findCinemaSeat(Long CinemaSeatID){
		return Optional.empty();
	}
}
