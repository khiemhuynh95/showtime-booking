package showtime.booking.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import showtime.booking.model.Cinema;
import showtime.booking.repositories.CinemasRepository;

@Service
public class CinemasService {
	private final CinemasRepository cinemasRepository;

	public CinemasService(CinemasRepository cinemasRepository) {
		this.cinemasRepository = cinemasRepository;
	}

	public List<Cinema> getAllCinemas() {
		return cinemasRepository.findAll();
	}
	
	public void addCinema(Cinema c) {
		cinemasRepository.save(c);
	}
	
	public Optional<Cinema> findCinema(Long cinemaId) {
		return Optional.empty();
	}

	public List<Cinema> findByZipcode(String zipcode) {
		// TODO Auto-generated method stub
		return cinemasRepository.findAllByZipcode(zipcode);
	}
	
	public List<Cinema> findByCity(String city) {
		// TODO Auto-generated method stub
		return cinemasRepository.findAllByCity(city);
	}
}
