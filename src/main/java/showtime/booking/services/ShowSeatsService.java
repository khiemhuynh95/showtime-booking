package showtime.booking.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import showtime.booking.model.Movie;
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
	
	public List<ShowSeat> getSeatsByShowId(Long id) {
		List<ShowSeat> results = new ArrayList<ShowSeat>();
		
		for(ShowSeat seat: getAllShowSeats()) {
			if(seat.getShow().equals(id)) {
				results.add(seat);
			}
		}
		return results;
	}

	public Optional<ShowSeat> findShowSeat(Long showSeatId) {
		return showSeatsRepository.findById(showSeatId);
	}
	
    public void addShowSeat(ShowSeat ss) {
    	showSeatsRepository.save(ss);
    }
}
