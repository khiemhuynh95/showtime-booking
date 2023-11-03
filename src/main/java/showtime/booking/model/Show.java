package showtime.booking.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "Show")
public class Show {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long ShowID;
	 
	 @Temporal(TemporalType.DATE)
	 private Date Date;
	 
	 @Temporal(TemporalType.TIME)
	 private Date StartTime;
	 
	 @Temporal(TemporalType.TIME)
	 private Date EndTime;
	 
	 @ManyToOne
	 @JoinColumn(name = "CinemaHallID")
	 private CinemaHall CinemaHall;
	 
	 @ManyToOne
	 @JoinColumn(name = "MovieID")
	 private Movie Movie;
	 
	 @OneToMany(mappedBy = "Show")
	 private Set<Booking> bookings = new HashSet<>();
	 
	 @OneToMany(mappedBy = "Show")
	 private Set<ShowSeat> showSeats = new HashSet<>();

	public Show(Date date, Date startTime) {
		Date = date;
		StartTime = startTime;
	}
	
	public Show() {}

	public void setCinemaHall(CinemaHall cinemaHall) {
		CinemaHall = cinemaHall;
	}

	public void setMovie(Movie movie) {
		Movie = movie;
	}

	public void setEndTime(Date endTime) {
		EndTime = endTime;
	}

	public Date getDate() {
		return Date;
	}

	public CinemaHall getCinemaHall() {
		return CinemaHall;
	}

	public Movie getMovie() {
		return Movie;
	}

	public Long getShowID() {
		return ShowID;
	}

	public Date getStartTime() {
		return StartTime;
	}
	
	
	
	 
	 
}
