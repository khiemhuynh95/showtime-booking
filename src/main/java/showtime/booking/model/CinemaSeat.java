package showtime.booking.model;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import showtime.booking.enums.SeatStatus;
import showtime.booking.enums.SeatType;

@Entity
@Table(name = "Cinema_Seat")
public class CinemaSeat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CinemaSeatID;
    
    @Column(nullable = false)
    private Integer SeatNumber;
    
    @Column(nullable = false)
    private String SeatRow;
    
    @Column(length = 32, columnDefinition = "varchar(32) default 'REGULAR'")
    @Enumerated(value = EnumType.STRING)
    private SeatType Type;
    
    @ManyToOne
    @JoinColumn(name = "CinemaHallID")
    private CinemaHall CinemaHall;
    
    @OneToMany(mappedBy = "CinemaSeat")
    private Set<ShowSeat> ShowSeats = new HashSet<>();

	public CinemaSeat(Integer seatNumber, String seatRow) {
		super();
		SeatNumber = seatNumber;
		SeatRow = seatRow;
	}

	public String getCinemaHall() {
		return CinemaHall.getName();
	}

	public void setCinemaHall(CinemaHall cinemaHall) {
		CinemaHall = cinemaHall;
	}

	public CinemaSeat() {}

	public Integer getSeatNumber() {
		return SeatNumber;
	}

	public void setSeatNumber(Integer seatNumber) {
		SeatNumber = seatNumber;
	}

	public String getSeatRow() {
		return SeatRow;
	}

	public void setSeatRow(String seatRow) {
		SeatRow = seatRow;
	}

//	public Set<ShowSeat> getShowSeats() {
//		return ShowSeats;
//	}

	public void setShowSeats(Set<ShowSeat> showSeats) {
		ShowSeats = showSeats;
	}
	
    
    
}
