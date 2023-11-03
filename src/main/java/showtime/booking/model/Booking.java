package showtime.booking.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "Booking")
public class Booking {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long BookingID;
	 
	 @Temporal(TemporalType.TIMESTAMP)
	 private Date Timestamp;
	 
	 @ManyToOne
	 @JoinColumn(name = "UserID")
	 private User User;
	 
	 @ManyToOne
	 @JoinColumn(name = "ShowID")
	 private Show Show;
	 
	 @OneToMany(mappedBy = "booking")
	 private Set<ShowSeat> showSeats = new HashSet<>();

	public Booking(User user, Show show, Date timestamp) {
		super();
		User = user;
		Show = show;
		Timestamp= timestamp;
	}

	public void setShowSeats(Set<ShowSeat> showSeats) {
		this.showSeats = showSeats;
	}
	
	public Booking() {}
	
	 
}
