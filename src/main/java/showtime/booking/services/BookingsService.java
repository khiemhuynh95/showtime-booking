package showtime.booking.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import showtime.booking.model.Booking;
import showtime.booking.repositories.BookingsRepository;

@Service
public class BookingsService {
	private final BookingsRepository bookingsRepository;

    public BookingsService(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingsRepository.findAll();
    }

    public Optional<Booking> findBooking(Long bookingId) {
        return Optional.empty();
    }

	public void addBooking(Booking b) {
		bookingsRepository.save(b);
		
	}
}
