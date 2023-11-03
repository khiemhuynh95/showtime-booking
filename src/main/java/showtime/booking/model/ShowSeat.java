package showtime.booking.model;

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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import showtime.booking.enums.SeatStatus;
import showtime.booking.util.RandomData;

@Entity
@Table(name = "Show_Seat")
public class ShowSeat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ShowSeatID;

	@Column(nullable = false)
	private Float Price = RandomData.generateRandomPrice();

	@Column(length = 32, columnDefinition = "varchar(32) default 'AVAILABLE'")
	@Enumerated(value = EnumType.STRING)
	private SeatStatus Status;

	@ManyToOne
	@JoinColumn(name = "CinemaSeatID")
	private CinemaSeat CinemaSeat;

	@ManyToOne
	@JoinColumn(name = "ShowID")
	private Show Show;

	@ManyToOne
	@JoinColumn(name = "BookingID")
	private Booking booking;

	public ShowSeat(SeatStatus status, CinemaSeat cinemaSeat,
			Show show) {
		Status = status;
		CinemaSeat = cinemaSeat;
		Show = show;
	}

	public Long getShow() {
		return Show.getShowID();
	}

	public void setShow(Show show) {
		Show = show;
	}

	public ShowSeat() {
	}

	public Long getShowSeatID() {
		return ShowSeatID;
	}

	public void setShowSeatID(Long showSeatID) {
		ShowSeatID = showSeatID;
	}

	public Float getPrice() {
		return Price;
	}

	public void setPrice(Float price) {
		Price = price;
	}

	public SeatStatus getStatus() {
		return Status;
	}

	public void setStatus(SeatStatus status) {
		Status = status;
	}

	public String getCinemaSeat() {
		return CinemaSeat.getSeatRow() + CinemaSeat.getSeatNumber();
	}

	public void setCinemaSeat(CinemaSeat cinemaSeat) {
		CinemaSeat = cinemaSeat;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
	
	
	
	
}