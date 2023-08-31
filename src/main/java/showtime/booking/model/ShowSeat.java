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

import showtime.booking.enums.SeatStatus;

@Entity
@Table(name = "Show_Seat")
public class ShowSeat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ShowSeatID;

	@Column(nullable = false)
	private Float Price;

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
}